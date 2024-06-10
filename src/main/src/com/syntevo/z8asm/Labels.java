package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public final class Labels {

	private final Map<String, GlobalLabel> globalLabels = new LinkedHashMap<>();

	private GlobalLabel currentGlobalLabel;

	public Labels() {
	}

	public void add(@NotNull String text, int address, @NotNull Location location) {
		if (isLocalLabel(text)) {
			if (currentGlobalLabel == null) {
				throw new SyntaxException("A local label only can be defined after a global label", location);
			}

			final Label prevLabel = currentGlobalLabel.localLabels.put(text, new Label(address, location));
			if (prevLabel != null) {
				throw new SyntaxException("A local label " + text + " is already defined at " + prevLabel.location, location);
			}
		}
		else {
			currentGlobalLabel = new GlobalLabel(address, location);
			final GlobalLabel prevLabel = globalLabels.put(text, currentGlobalLabel);
			if (prevLabel != null) {
				throw new SyntaxException("A label " + text + " is already defined at " + prevLabel.location, location);
			}
		}
	}

	public void finishedInitialization() {
		currentGlobalLabel = null;
	}

	public void processing(@NotNull String text) {
		if (isLocalLabel(text)) {
			return;
		}

		currentGlobalLabel = globalLabels.get(text);
		if (currentGlobalLabel == null) {
			throw new IllegalStateException();
		}
	}

	public int resolve(@NotNull String text, @NotNull Location location) {
		final Label label;
		if (isLocalLabel(text)) {
			label = currentGlobalLabel != null
					? currentGlobalLabel.localLabels.get(text)
					: null;
		}
		else {
			label = globalLabels.get(text);
		}
		if (label == null) {
			throw new SyntaxException("No target '" + text + "' found", location);
		}

		label.used = true;
		return label.address;
	}

	public void reportUnused(@NotNull WarningOut out) {
		for (Map.Entry<String, GlobalLabel> entry : globalLabels.entrySet()) {
			final GlobalLabel globalLabel = entry.getValue();
			if (!globalLabel.used) {
				out.print(globalLabel.location + ": Label " + entry.getKey() + " is unused");
			}

			for (Map.Entry<String, Label> localEntry : globalLabel.localLabels.entrySet()) {
				final Label localLabel = localEntry.getValue();
				if (!localLabel.used) {
					out.print(localLabel.location + ": Label " + entry.getKey() + "/" + localEntry.getKey() + " is unused");
				}
			}
		}
	}

	private boolean isLocalLabel(String text) {
		return text.startsWith(".");
	}

	private static class Label {
		public final int address;
		public final Location location;

		public boolean used;

		public Label(int address, Location location) {
			this.address = address;
			this.location = location;
		}
	}

	private static final class GlobalLabel extends Label {
		public final Map<String, Label> localLabels = new LinkedHashMap<>();

		public GlobalLabel(int address, @NotNull Location location) {
			super(address, location);
		}
	}
}
