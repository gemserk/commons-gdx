package com.gemserk.commons.gdx.graphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionUtils {

	/**
	 * Returns true if TextureRegion t1 and TextureRegion t2 are equals by checking their texture coordinates.
	 */
	public static boolean textureRegionEquals(TextureRegion t1, TextureRegion t2) {
		if (t1 == t2)
			return true;

		if (t1 == null)
			return false;

		if (t2 == null)
			return false;

		if (Float.floatToIntBits(t1.getU()) != Float.floatToIntBits(t2.getU()))
			return false;

		if (Float.floatToIntBits(t1.getU2()) != Float.floatToIntBits(t2.getU2()))
			return false;

		if (Float.floatToIntBits(t1.getV()) != Float.floatToIntBits(t2.getV()))
			return false;

		if (Float.floatToIntBits(t1.getV2()) != Float.floatToIntBits(t2.getV2()))
			return false;

		return true;
	}

	/**
	 * Returns true if both AtlasRegion t1 and t2 could be considered equals by checking their values. It doesn't check the index value.
	 */
	public static boolean atlasRegionEquals(AtlasRegion t1, AtlasRegion t2) {
		if (t1 == t2)
			return true;

		if (t1 == null)
			return false;

		if (t2 == null)
			return false;

		if (Float.floatToIntBits(t1.offsetX) != Float.floatToIntBits(t2.offsetX))
			return false;

		if (Float.floatToIntBits(t1.offsetY) != Float.floatToIntBits(t2.offsetY))
			return false;

		if (t1.originalHeight != t2.originalHeight)
			return false;

		if (t1.originalWidth != t2.originalWidth)
			return false;

		if (t1.packedHeight != t2.packedHeight)
			return false;

		if (t1.packedWidth != t2.packedWidth)
			return false;

		if (t1.rotate != t2.rotate)
			return false;

		return true;

	}

}
