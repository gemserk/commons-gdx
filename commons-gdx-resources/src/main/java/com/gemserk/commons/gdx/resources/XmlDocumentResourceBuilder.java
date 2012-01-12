package com.gemserk.commons.gdx.resources;

import org.w3c.dom.Document;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.gemserk.commons.svg.inkscape.DocumentParser;

public class XmlDocumentResourceBuilder implements ResourceBuilder<Document> {

	private final String file;

	private boolean cached = true;

	private FileType fileType = FileType.Internal;

	public XmlDocumentResourceBuilder fileType(FileType fileType) {
		this.fileType = fileType;
		return this;
	}

	public XmlDocumentResourceBuilder cached() {
		return cached(true);
	}
	
	public XmlDocumentResourceBuilder cached(boolean cached) {
		this.cached = cached;
		return this;
	}


	public XmlDocumentResourceBuilder(String file) {
		this.file = file;
	}

	@Override
	public Document build() {
		return new DocumentParser().parse(Gdx.files.getFileHandle(file, fileType).read());
	}

	@Override
	public boolean isVolatile() {
		return !cached;
	}

}