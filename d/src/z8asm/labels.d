// Labels.d - translated from Java
module z8asm.labels;

import std.conv : to;
import std.string : startsWith;

import z8asm.location : Location;
import z8asm.syntax_exception : SyntaxException;
import z8asm.warning_out : WarningOut;

class Labels {
	private {
		GlobalLabel currentGlobalLabel;
		GlobalLabel[string] globalLabels;
	}

	public this() {
		globalLabels = new GlobalLabel[string];
	}

	public void add(string text, int address, Location location) {
		if (isLocalLabel(text)) {
			if (currentGlobalLabel is null) {
				throw new SyntaxException("A local label only can be defined after a global label", location);
			}

			if (text in currentGlobalLabel.localLabels) {
				throw new SyntaxException("A local label " ~ text ~ " is already defined at " ~ currentGlobalLabel.localLabels[text].location.toString(), location);
			}
			currentGlobalLabel.localLabels[text] = new Label(address, location);
		} else {
			currentGlobalLabel = new GlobalLabel(address, location);
			if (text in globalLabels) {
				throw new SyntaxException("A label " ~ text ~ " is already defined at " ~ globalLabels[text].location.toString(), location);
			}
			globalLabels[text] = currentGlobalLabel;
		}
	}

	public bool update(string text, int address) {
		Label prevLabel;
		if (isLocalLabel(text)) {
			if (currentGlobalLabel is null) return false;
			prevLabel = currentGlobalLabel.localLabels.get(text, null);
		} else {
			prevLabel = globalLabels.get(text, null);
		}

		if (prevLabel is null) return false;
		if (prevLabel.address == address) return false;
		prevLabel.address = address;
		return true;
	}

	public void finishedInitialization() {
		currentGlobalLabel = null;
	}

	public void processing(string text) {
		if (isLocalLabel(text)) {
			return;
		}

		currentGlobalLabel = globalLabels.get(text, null);
		if (currentGlobalLabel is null) {
			throw new Exception("Label " ~ text ~ " not found");
		}
	}

	public int resolve(string text, Location location) {
		Label label;
		if (isLocalLabel(text)) {
			label = currentGlobalLabel !is null ? currentGlobalLabel.localLabels.get(text, null) : null;
		} else {
			label = globalLabels.get(text, null);
		}
		if (label is null) {
			throw new SyntaxException("No target '" ~ text ~ "' found", location);
		}

		label.used = true;
		return label.address;
	}

	public void reportUnused(WarningOut warningOut) {
		foreach (key, GlobalLabel globalLabel; globalLabels) {
			if (!globalLabel.used) {
				warningOut.print(globalLabel.location.toString ~ ": Label " ~ key ~ " is unused");
			}

			foreach (localKey, localLabel; globalLabel.localLabels) {
				if (!localLabel.used) {
					warningOut.print(localLabel.location.toString ~ ": Label " ~ key ~ "/" ~ localKey ~ " is unused");
				}
			}
		}
	}

	private bool isLocalLabel(string text) {
		return text.startsWith(".");
	}

	private static class Label {
		public Location location;
		public int address;
		public bool used;

		this(int address, Location location) {
			this.address = address;
			this.location = location;
		}
	}

	private static class GlobalLabel : Label {
		Label[string] localLabels;

		this(int address, Location location) {
			super(address, location);
			localLabels = new Label[string];
		}
	}
}
