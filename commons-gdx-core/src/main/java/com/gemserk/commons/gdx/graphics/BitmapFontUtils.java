package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;

/**
 * Util methods to work with BitmapFont class.
 * 
 */
public class BitmapFontUtils {

	public static void spacing(BitmapFont bitmapFont, CharSequence charSequence, int spacing) {

		BitmapFontData data = bitmapFont.getData();

		for (int c = 0; c < charSequence.length(); c++) {
			char charAt = charSequence.charAt(c);
			Glyph g = data.getGlyph(charAt);
			g.xoffset += (spacing - g.xadvance) / 2;
			g.xadvance = spacing;
		}

	}

}
