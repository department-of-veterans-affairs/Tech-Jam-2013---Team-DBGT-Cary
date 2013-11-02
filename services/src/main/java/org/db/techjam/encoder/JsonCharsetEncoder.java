package org.db.techjam.encoder;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class JsonCharsetEncoder {

	static final Log logger = LogFactory.getLog(JsonCharsetEncoder.class);
	private String charset;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charst) {
		this.charset = charst;
	}

	public JsonCharsetEncoder(final String charst) {
		this.charset = charst;
	}

	public String convertCharsetToUtf8(final String source) {
		final StringTransformer transformer = new ToUtf8Transformer(charset);
		return (convert(source, transformer));
	}

	public String convertUtf8ToCharset(final String source) {
		final StringTransformer transformer = new ToLocaleTransformer(charset);
		return convert(source, transformer);
	}

	private String convert(final String source,
			final StringTransformer transformer) {

		final JsonValueModifier jsonModifier = new JsonValueModifier(
				transformer);
		return jsonModifier.transform(source);
	}

	public interface StringTransformer {

		String transform(String source);
	}

	private static class ToUtf8Transformer implements StringTransformer {

		private transient final String fCharset;

		public ToUtf8Transformer(final String charst) {
			fCharset = charst;
		}

		public String transform(final String source) {
			try {
				return new String(source.getBytes("ISO-8859-1"), fCharset);
			} catch (final UnsupportedEncodingException e) {
				logger.error("Exception in transform", e);
				return source;
			}
		}
	}

	private static class ToLocaleTransformer implements StringTransformer {

		private transient final String fCharset;

		public ToLocaleTransformer(final String charst) {
			fCharset = charst;
		}

		public String transform(final String source) {
			try {
				return new String(source.getBytes(fCharset), "ISO-8859-1");
			} catch (final Exception e) {
				logger.error("Exception in transform", e);
				return source;
			}
		}
	}

}
