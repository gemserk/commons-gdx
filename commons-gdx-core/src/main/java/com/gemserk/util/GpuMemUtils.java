package com.gemserk.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class GpuMemUtils {

	public static int getTextureGpuSize(){
		try {
			Field managedTexturesField = Texture.class.getDeclaredField("managedTextures");
			managedTexturesField.setAccessible(true);
			Map<Application, List<Texture>> managedTexturesPerApp= (Map<Application, List<Texture>>) managedTexturesField.get(null);
			List<Texture> managedTextures = managedTexturesPerApp.get(Gdx.app);
			
			System.out.println(managedTextures.size());
			
			int totalTextureSize = 0;
			for (Texture texture : managedTextures) {
				int width = texture.getWidth();
				int height = texture.getHeight();
				Format format = texture.getTextureData().getFormat();
				boolean useMipMaps = texture.getTextureData().useMipMaps();
				
				int bytesPerPixel = getBytesPerPixel(format);
				
				int textureSize = (int)( width * height * bytesPerPixel * (useMipMaps ? 1.33333f : 1));
				
				totalTextureSize +=textureSize;
			}
			return totalTextureSize;
			
		} catch (Exception e) {
			throw new RuntimeException("Error while getting textures gpu memory use",e);
		} 
	}

	static public int getBytesPerPixel(Format format) {
		switch (format) {
		case Alpha:
		case Intensity:
		case LuminanceAlpha:
			return 1;
		case RGB565:
		case RGBA4444:
			return 2;
		case RGB888:
			return 3;
		case RGBA8888:
			return 4;
		default:
			return 4;
		}
	}
}