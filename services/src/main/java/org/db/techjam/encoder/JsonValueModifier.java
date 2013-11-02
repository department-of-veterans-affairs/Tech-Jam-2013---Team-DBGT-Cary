package org.db.techjam.encoder;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.TextNode;
import org.db.techjam.encoder.JsonCharsetEncoder.StringTransformer;

public class JsonValueModifier {

	private transient final StringTransformer fMapper;
	private static final Log logger = LogFactory
			.getLog(JsonValueModifier.class);

	public JsonValueModifier(final StringTransformer valueModifier) {
		fMapper = valueModifier;
	}

	public String transform(final String src) {

		final ObjectMapper mapper = new ObjectMapper();
		try {
			final JsonNode rootNode = mapper.readValue(src, JsonNode.class);
			update(rootNode);
			return mapper.writeValueAsString(rootNode);

		} catch (final JsonParseException e) {
			logger.error("", e);
		} catch (final JsonMappingException e) {
			logger.error("", e);
		} catch (final IOException e) {
			logger.error("", e);
		}

		return src;
	}

	private void update(final JsonNode node) {
		if (node.isObject()) {
			final ObjectNode oNode = (ObjectNode) node;
			final Iterator<String> iter = oNode.getFieldNames();
			while (iter.hasNext()) {
				final String fieldName = iter.next();
				JsonNode child = oNode.get(fieldName);
				if (child.isTextual()) {
					child = getUpdatedTextNode(child);
					oNode.put(fieldName, child);
				} else {
					update(child);
				}
			}
		} else if (node.isArray()) {
			final ArrayNode aNode = (ArrayNode) node;
			for (int i = 0; i < aNode.size(); i++) {
				JsonNode child = aNode.get(i);
				if (child.isTextual()) {
					child = getUpdatedTextNode(child);
					aNode.set(i, child);
				} else {
					update(child);
				}
			}
		}
	}

	private TextNode getUpdatedTextNode(final JsonNode node) {
		final String newValue = fMapper.transform(node.getTextValue());
		return new TextNode(newValue);
	}

}
