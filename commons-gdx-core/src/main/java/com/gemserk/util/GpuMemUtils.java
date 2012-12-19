package com.gemserk.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Keys;

public class GpuMemUtils {

	public static class GpuMemInfo {
		public int totalTextures;
		public int gpuMemSize;
	}

	public static GpuMemInfo getTextureGpuSize() {
		try {
			List<Texture> managedTextures = getTextures();

			int totalTextureSize = 0;
			for (Texture texture : managedTextures) {
				int width = texture.getWidth();
				int height = texture.getHeight();
				Format format = texture.getTextureData().getFormat();
				boolean useMipMaps = texture.getTextureData().useMipMaps();

				int bytesPerPixel = getBytesPerPixel(format);

				int textureSize = (int) (width * height * bytesPerPixel * (useMipMaps ? 1.33333f : 1));

				totalTextureSize += textureSize;
			}
			GpuMemInfo gpuMemInfo = new GpuMemInfo();
			gpuMemInfo.totalTextures = managedTextures.size();
			gpuMemInfo.gpuMemSize = totalTextureSize;
			return gpuMemInfo;

		} catch (Exception e) {
			throw new RuntimeException("Error while getting textures gpu memory use", e);
		}
	}

	public static String checkDuplicateTextureErrors() {
		try {
			List<Texture> managedTextures = getTextures();

			List<String> textureReferenceDuplicates = getTextureReferenceDuplicates(managedTextures);

			List<String> texturePathDuplicates = getTexturePathDuplicates(managedTextures);
			
			StringBuilder result = new StringBuilder();
			result.append("[duplicateReferences(" + textureReferenceDuplicates.size() + "):{");
			for (String path : textureReferenceDuplicates) {
				result.append(path);
				result.append(",");
			}
			result.append("}, duplicatePaths("+texturePathDuplicates.size()+"):{");
			for (String path : texturePathDuplicates) {
				result.append(path);
				result.append(",");
			}
			result.append("}]");
			
			return result.toString();

		} catch (Exception e) {
			return "ERROR_WHILE_GETTING_GPU_TEXTURES_ERROR";
		}
	}

	private static List<String> getTexturePathDuplicates(List<Texture> managedTextures) {
		ObjectMap<String, Object> pathErrors = new ObjectMap<String, Object>();
		ObjectMap<String, Object> paths = new ObjectMap<String, Object>();
		for (Texture texture : managedTextures) {
			String path = getFilePath(texture);
			if (path != null) {
				if (paths.containsKey(path))
					pathErrors.put(path, null);

				paths.put(path, null);
			}
		}

		List<String> duplicatePaths = new ArrayList<String>(pathErrors.size);
		Keys<String> keys = pathErrors.keys();
		while (keys.hasNext()) {
			String path = (String) keys.next();
			duplicatePaths.add(path);
		}
		return duplicatePaths;
	}

	private static String getFilePath(Texture texture) {
		String path = null;
		TextureData textureData = texture.getTextureData();
		if (textureData instanceof FileTextureData) {
			FileTextureData fileTextureData = (FileTextureData) textureData;
			path = fileTextureData.getFileHandle().path();

		}
		return path;
	}

	private static List<String> getTextureReferenceDuplicates(List<Texture> managedTextures) {
		ObjectMap<Texture, Object> referenceErrors = new ObjectMap<Texture, Object>();
		ObjectMap<Texture, Object> references = new ObjectMap<Texture, Object>();
		for (Texture texture : managedTextures) {
			if (references.containsKey(texture))
				referenceErrors.put(texture, null);

			references.put(texture, null);
		}
		
		List<String> duplicatePaths = new ArrayList<String>(referenceErrors.size);
		Keys<Texture> keys = referenceErrors.keys();
		while (keys.hasNext()) {
			Texture texture = (Texture) keys.next();
			String path = getFilePath(texture);
			if(path==null)
				path = "NoPath -" + texture.getTextureData().getClass() + " - " + texture.getWidth() + "x" + texture.getHeight();
				
			duplicatePaths.add(path);
		}
		return duplicatePaths;
	}

	private static List<Texture> getTextures() throws NoSuchFieldException, IllegalAccessException {
		Field managedTexturesField = Texture.class.getDeclaredField("managedTextures");
		managedTexturesField.setAccessible(true);
		Map<Application, List<Texture>> managedTexturesPerApp = (Map<Application, List<Texture>>) managedTexturesField.get(null);
		List<Texture> managedTextures = managedTexturesPerApp.get(Gdx.app);
		return managedTextures;
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