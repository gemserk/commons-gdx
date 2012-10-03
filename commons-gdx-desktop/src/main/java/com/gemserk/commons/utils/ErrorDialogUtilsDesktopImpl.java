package com.gemserk.commons.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ErrorDialogUtilsDesktopImpl implements ErrorDialogUtils {
	
	private final Component parent;

	public ErrorDialogUtilsDesktopImpl(Component parent) {
		this.parent = parent;
	}
	
	public ErrorDialogUtilsDesktopImpl() {
		this(null);
	}
	
	@Override
	public void showOutOfMemoryError() {
		JOptionPane.showMessageDialog(parent, "You are probably running low on memory, close some applications and try to run the game again.", "ERROR", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

}
