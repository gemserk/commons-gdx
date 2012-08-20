package com.gemserk.commons.svg.inkscape;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DocumentParser {

	private static class DefaultErrorHandler implements ErrorHandler {
		@Override
		public void warning(SAXParseException exception) throws SAXException {

		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			throw exception;
		}

		@Override
		public void error(SAXParseException exception) throws SAXException {

		}
	}

	/**
	 * Returns an XML Document from an InputStream using a DocumentBuilder with the specified validation and namespaceaware flags.
	 * 
	 * @param is
	 *            The InputStream to be processed.
	 * @param validating
	 *            If the document should be validated or not
	 * @param namespaceaware
	 *            If the document should be processed with name space awareness or not.
	 */
	public Document parse(InputStream is, boolean validating, boolean namespaceaware) {
		return parse(null, is, validating, namespaceaware);
	}

	/**
	 * Returns an XML Document from an InputStream using a DocumentBuilder with the specified validation and namespaceaware flags.
	 * 
	 * @param schema
	 *            The Schema to be used to parse the Document, null if no Schema wanted.
	 * @param is
	 *            The InputStream to be processed.
	 * @param validating
	 *            If the document should be validated or not
	 * @param namespaceaware
	 *            If the document should be processed with name space awareness or not.
	 */
	public Document parse(Schema schema, InputStream is, boolean validating, boolean namespaceaware) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			if (schema != null)
				factory.setSchema(schema);
			factory.setValidating(validating);
			factory.setNamespaceAware(namespaceaware);
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new DefaultErrorHandler());
			builder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new ByteArrayInputStream(new byte[0]));
				}
			});
			return builder.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns an XML Document from an InputStream using a DocumentBuilder without validation.
	 * 
	 * @param is
	 *            The InputStream to parse.
	 */
	public Document parse(InputStream is) {
		return this.parse(is, false, true);
	}

}