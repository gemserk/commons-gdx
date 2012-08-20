package com.gemserk.commons.gdx.resources;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.badlogic.gdx.files.FileHandle;

public class XmlSchemaResourceBuilder implements ResourceBuilder<Schema> {

	private FileHandle fileHandle;

	@Override
	public boolean isVolatile() {
		return false;
	}

	public XmlSchemaResourceBuilder fileHandle(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
		return this;
	}

	@Override
	public Schema build() {
		if (fileHandle == null)
			throw new IllegalArgumentException("FileHandle should not be null");
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			return schemaFactory.newSchema(new StreamSource(fileHandle.read()));
		} catch (SAXException e) {
			throw new RuntimeException("Failed to create Schema from fileHandle " + fileHandle, e);
		}
	}

}