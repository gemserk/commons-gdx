package com.gemserk.commons.gdx.resources;

import org.w3c.dom.Document;

import com.badlogic.gdx.files.FileHandle;
import com.gemserk.commons.svg.inkscape.DocumentParser;

public class XmlDocumentResourceBuilder implements ResourceBuilder<Document> {

	private boolean cached = true;

	FileHandle file;

	public XmlDocumentResourceBuilder file(FileHandle file) {
		this.file = file;
		return this;
	}

	public XmlDocumentResourceBuilder cached() {
		return cached(true);
	}

	public XmlDocumentResourceBuilder cached(boolean cached) {
		this.cached = cached;
		return this;
	}

	@Override
	public Document build() {
		if (file == null)
			throw new IllegalStateException("Can't create an XML Document if no file handle is specified");
		return new DocumentParser().parse(file.read());
	}

	@Override
	public boolean isVolatile() {
		return !cached;
	}

}