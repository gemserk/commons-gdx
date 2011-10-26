package com.gemserk.commons.gdx.gui.animation4j.converters;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.commons.gdx.gui.Control;

public class GuiConverters {
	
	public static final TypeConverter<Control> controlPositionConverter = new ControlPositionConverter();

}
