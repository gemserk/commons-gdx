package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.gemserk.resources.dataloaders.DataLoader;

public abstract class DisposableDataLoader<T extends Disposable> extends DataLoader<T> {

	protected FileHandle fileHandle;

	public DisposableDataLoader(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public void dispose(Disposable t) {
		t.dispose();
	}

}