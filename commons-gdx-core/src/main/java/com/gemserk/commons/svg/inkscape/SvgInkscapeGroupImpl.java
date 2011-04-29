package com.gemserk.commons.svg.inkscape;

public class SvgInkscapeGroupImpl implements SvgInkscapeGroup {

	String id;

	String groupMode;

	String label;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupMode() {
		return groupMode;
	}

	public void setGroupMode(String groupMode) {
		this.groupMode = groupMode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}