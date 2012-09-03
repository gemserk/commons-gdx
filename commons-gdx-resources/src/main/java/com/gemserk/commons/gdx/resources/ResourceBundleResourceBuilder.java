package com.gemserk.commons.gdx.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import com.badlogic.gdx.files.FileHandle;

public class ResourceBundleResourceBuilder implements ResourceBuilder<ResourceBundle> {

	static class PropertyResourceBundleWithParent extends PropertyResourceBundle {
		PropertyResourceBundleWithParent(InputStream stream, ResourceBundle parent) throws IOException {
			super(stream);
			setParent(parent);
		}
	}

	ResourceBundle rootResourceBundle;
	Map<Locale, ResourceBundle> bundlesCache = null;

	FileHandle rootFileHandle;
	Map<Locale, FileHandle> fileHandles = new HashMap<Locale, FileHandle>();
	boolean cached = false;

	public ResourceBundleResourceBuilder root(FileHandle fileHandle) {
		this.rootFileHandle = fileHandle;
		return this;
	}

	public ResourceBundleResourceBuilder bundle(Locale locale, FileHandle fileHandle) {
		fileHandles.put(locale, fileHandle);
		return this;
	}
	
	public ResourceBundleResourceBuilder cached() {
		cached = true;
		return this;
	}

	@Override
	public boolean isVolatile() {
		return !cached;
	}

	@Override
	public ResourceBundle build() {
		if (bundlesCache == null) {
			if (rootFileHandle == null)
				throw new RuntimeException("file handle for root ResourceBundle should be specified");

			bundlesCache = new HashMap<Locale, ResourceBundle>();

			try {
				rootResourceBundle = new PropertyResourceBundle(rootFileHandle.read());
			} catch (IOException e) {
				throw new RuntimeException("failed to create root ResourceBundle from " + rootFileHandle.name(), e);
			}

			Set<Locale> locales = fileHandles.keySet();
			for (Locale locale : locales) {
				FileHandle fileHandle = fileHandles.get(locale);
				
				if (equalsFileHandle(fileHandle, rootFileHandle)) {
					bundlesCache.put(locale, rootResourceBundle);
					continue;
				}
				
				try {
					ResourceBundle resourceBundle = new PropertyResourceBundleWithParent(fileHandle.read(), rootResourceBundle);
					bundlesCache.put(locale, resourceBundle);
				} catch (IOException e) {
					throw new RuntimeException("failed to create ResourceBundle from " + fileHandle.name(), e);
				}
			}

		}
		
		Locale currentLocale = Locale.getDefault();
		ResourceBundle resourceBundle = bundlesCache.get(currentLocale);
		if (resourceBundle == null)
			return rootResourceBundle;
		return resourceBundle;
	}

	public boolean equalsFileHandle(FileHandle fh1, FileHandle fh2) {
		if (fh1.type() != fh2.type())
			return false;
		return fh1.toString().equals(fh2.toString());
	}

}
