package com.gemserk.util;

import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenshotSaver {

	private static final int[] RGBA_OFFSETS = { 0, 1, 2, 3 };
	private static final int[] RGB_OFFSETS = { 0, 1, 2 };

	public static void saveScreenshot(String baseName) throws IOException {
		File createTempFile = File.createTempFile(baseName, ".png");
		saveScreenshot(createTempFile);
	}

	public static void saveScreenshot(File file) throws IOException {
		saveScreenshot(file, false);
	}

	public static void saveScreenshot(File file, boolean hasAlpha) throws IOException {
		if (Gdx.app.getType() == ApplicationType.Android)
			return;

		byte[] screenshotPixels = ScreenUtils.getFrameBufferPixels(true);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		saveScreenshot(file, screenshotPixels, width, height, hasAlpha);
	}

	public static void saveScreenshot(File file, byte[] pixels, int width, int height, boolean hasAlpha) throws IOException {
		DataBufferByte dataBuffer = new DataBufferByte(pixels, pixels.length);

		PixelInterleavedSampleModel sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_BYTE, width, height, 4, 4 * width, getOffsets(hasAlpha));

		WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));

		BufferedImage img = new BufferedImage(getColorModel(hasAlpha), raster, false, null);

		ImageIO.write(img, "png", file);
	}

	private static ColorModel getColorModel(boolean alpha) {
		if (alpha)
			return new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
		return new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
	}

	private static int[] getOffsets(boolean alpha) {
		if (alpha)
			return RGBA_OFFSETS;
		return RGB_OFFSETS;
	}

}
