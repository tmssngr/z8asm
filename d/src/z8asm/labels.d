// Labels.d - translated from Java
module z8asm.labels;

import std.conv : to;
import std.string : startsWith;

import z8asm.location : Location;
import z8asm.syntax_exception : SyntaxException;
import z8asm.warning_out : WarningOut;
import z8asm.utils : Utils;

class Labels {
	private {
		GlobalLabel currentGlobalLabel;
		GlobalLabel[string] globalLabels;
		string[] globalLabelNames;
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
			currentGlobalLabel.localLabelNames ~= text;
		} else {
			currentGlobalLabel = new GlobalLabel(address, location);
			if (text in globalLabels) {
				throw new SyntaxException("A label " ~ text ~ " is already defined at " ~ globalLabels[text].location.toString(), location);
			}
			globalLabels[text] = currentGlobalLabel;
			globalLabelNames ~= text;
		}
	}

	public bool update(string text, int address) {
		Label prevLabel;
		if (isLocalLabel(text)) {
			Utils.assertTrue(currentGlobalLabel !is null);
			prevLabel = currentGlobalLabel.localLabels.get(text, null);
		} else {
			currentGlobalLabel = globalLabels.get(text, null);
			prevLabel = currentGlobalLabel;
		}

		Utils.assertTrue(prevLabel !is null);
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
		foreach (key; globalLabelNames) {
			GlobalLabel globalLabel = globalLabels[key];
			if (!globalLabel.used) {
				warningOut.print(globalLabel.location.toString ~ ": Label " ~ key ~ " is unused");
			}

			foreach (localKey; globalLabel.localLabelNames) {
				Label localLabel = globalLabel.localLabels[localKey];
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
		string[] localLabelNames;

		this(int address, Location location) {
			super(address, location);
			localLabels = new Label[string];
		}
	}
}
