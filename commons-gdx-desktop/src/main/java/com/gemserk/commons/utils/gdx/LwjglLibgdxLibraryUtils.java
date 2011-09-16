package com.gemserk.commons.utils.gdx;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.lwjgl.LWJGLUtil;

/**
 * It is some kind of utility for LWJGL Applets to try to load libGDX from org.lwjgl.librarypath
 */
public class LwjglLibgdxLibraryUtils {

	private static final String amd64Bits = "amd64";
	private static final String x86_64Bits = "x86_64";

	public static boolean is64Bits() {
		String osArch = System.getProperty("os.arch");
		return amd64Bits.equals(osArch) || x86_64Bits.equals(osArch);
	}

	/**
	 * Tries to load the library from the org.lwjgl.librarypath
	 */
	private static void doLoadLibrary(final String lib_name) {
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			public Object run() {
				String library_path = System.getProperty("org.lwjgl.librarypath");
				if (library_path != null) 
					System.load(library_path + File.separator + lib_name);
				return null;
			}
		});
	}

	public static void loadLibgdxLibrary() {
		switch (LWJGLUtil.getPlatform()) {
		case LWJGLUtil.PLATFORM_LINUX:
			loadLibgdxLinuxLibrary();
			return;
		case LWJGLUtil.PLATFORM_WINDOWS:
			loadLibgdxWindowsLibrary();
			return;
		case LWJGLUtil.PLATFORM_MACOSX:
			loadLibgdxMacLibrary();
			return;
		default:
			throw new IllegalStateException("Unsupported platform");
		}
	}

	public static void loadLibgdxMacLibrary() {
		try {
			doLoadLibrary("libgdx.dylib");
		} catch (UnsatisfiedLinkError e) {
			System.loadLibrary("gdx");
		}
	}

	public static void loadLibgdxWindowsLibrary() {
		try {
			if (is64Bits())
				doLoadLibrary("gdx.dll");
			else
				doLoadLibrary("gdx-64.dll");
		} catch (UnsatisfiedLinkError e) {
			if (is64Bits())
				System.loadLibrary("gdx-64");
			else
				System.loadLibrary("gdx");
		}
	}

	public static void loadLibgdxLinuxLibrary() {
		try {
			if (is64Bits())
				doLoadLibrary("libgdx-64.so");
			else
				doLoadLibrary("libgdx.so");
		} catch (UnsatisfiedLinkError e) {
			if (is64Bits())
				System.loadLibrary("gdx-64");
			else
				System.loadLibrary("gdx");
		}
	}

}
