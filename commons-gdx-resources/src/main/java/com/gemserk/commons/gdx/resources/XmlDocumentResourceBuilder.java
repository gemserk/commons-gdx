package com.gemserk.commons.gdx.resources;

import javax.xml.validation.Schema;

import org.w3c.dom.Document;

import com.badlogic.gdx.files.FileHandle;
import com.gemserk.commons.svg.inkscape.DocumentParser;
import com.gemserk.resources.ResourceManager;

public class XmlDocumentResourceBuilder implements ResourceBuilder<Document> {

	private final ResourceManager<String> resourceManager;

	private boolean cached = true;

	FileHandle file;
	String schemaResourceId;

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

	public XmlDocumentResourceBuilder schemaResourceId(String schemaResourceId) {
		this.schemaResourceId = schemaResourceId;
		return this;
	}

	public XmlDocumentResourceBuilder(ResourceManager<String> resourceManager) {
		this.resourceManager = resourceManager;
	}

	@Override
	public Document build() {
		if (file == null)
			throw new IllegalStateException("Can't create an XML Document if no file handle is specified");
		if (schemaResourceId != null) {
			Schema schema = resourceManager.getResourceValue(schemaResourceId);
			return new DocumentParser().parse(schema, file.read(), false, true);
		}
		return new DocumentParser().parse(file.read());
	}

	@Override
	public boolean isVolatile() {
		return !cached;
	}

}