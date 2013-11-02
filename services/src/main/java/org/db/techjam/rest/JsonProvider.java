package org.db.techjam.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JsonProvider implements MessageBodyReader<Object>,
		MessageBodyWriter<Object> {

	private JacksonJaxbJsonProvider jacksonProvider;

	public JacksonJaxbJsonProvider getJacksonProvider() {
		return jacksonProvider;
	}

	public void setJacksonProvider(JacksonJaxbJsonProvider jacksonProvider) {
		this.jacksonProvider = jacksonProvider;
	}

	public JsonProvider() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(
				Inclusion.NON_NULL);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		final AnnotationIntrospector pair = new AnnotationIntrospector.Pair(
				new JaxbAnnotationIntrospector(),
				new JacksonAnnotationIntrospector());
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.getSerializationConfig().setAnnotationIntrospector(pair);
		jacksonProvider = new JacksonJaxbJsonProvider();
		jacksonProvider.setMapper(mapper);
	}

	public long getSize(final Object t, final Class<?> type,
			final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		return this.jacksonProvider.getSize(t, type, genericType, annotations,
				mediaType);
	}

	public boolean isWriteable(final Class<?> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType) {
		return this.jacksonProvider.isWriteable(type, genericType, annotations,
				mediaType);
	}

	public void writeTo(final Object t, final Class<?> type,
			final Type genericType, final Annotation[] annotations,
			final MediaType mediaType,
			final MultivaluedMap<String, Object> httpHeaders,
			final OutputStream entityStream) throws IOException {
		this.jacksonProvider.writeTo(t, type, genericType, annotations,
				mediaType, httpHeaders, entityStream);
	}

	public boolean isReadable(final Class<?> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType) {
		return this.jacksonProvider.isReadable(type, genericType, annotations,
				mediaType);
	}

	public Object readFrom(final Class<Object> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType,
			final MultivaluedMap<String, String> httpHeaders,
			final InputStream entityStream) throws IOException {
		return this.jacksonProvider.readFrom(type, genericType, annotations,
				mediaType, httpHeaders, entityStream);
	}

}
