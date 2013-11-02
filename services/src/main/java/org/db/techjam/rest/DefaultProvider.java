package org.db.techjam.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.wink.common.RuntimeContext;
import org.apache.wink.common.internal.runtime.RuntimeContextTLS;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

@Provider
@Consumes({ MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_FORM_URLENCODED , MediaType.MULTIPART_FORM_DATA})
@Produces(MediaType.APPLICATION_JSON)
public class DefaultProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	private JacksonJaxbJsonProvider jacksonProvider;

	public JacksonJaxbJsonProvider getJacksonProvider() {
		return jacksonProvider;
	}

	public void setJacksonProvider(JacksonJaxbJsonProvider jacksonProvider) {
		this.jacksonProvider = jacksonProvider;
	}

	public DefaultProvider() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		final AnnotationIntrospector pair = new AnnotationIntrospector.Pair(
						new JaxbAnnotationIntrospector(), new JacksonAnnotationIntrospector());
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.getSerializationConfig().setAnnotationIntrospector(pair);
		jacksonProvider = new JacksonJaxbJsonProvider();
		jacksonProvider.setMapper(mapper);
	}

	public long getSize(final Object object, final Class<?> type, final Type genericType,
					final Annotation[] annotations, final MediaType mediaType) {
		return this.jacksonProvider.getSize(object, type, genericType, annotations, mediaType);
	}

	public boolean isWriteable(final Class<?> type, final Type genericType,
					final Annotation[] annotations, final MediaType mediaType) {
		return this.jacksonProvider.isWriteable(type, genericType, annotations, mediaType);
	}

	public void writeTo(final Object object, final Class<?> type, final Type genericType,
					final Annotation[] annotations, final MediaType mediaType,
					final MultivaluedMap<String, Object> httpHeaders,
					final OutputStream entityStream) throws IOException {
		this.jacksonProvider.writeTo(object, type, genericType, annotations, mediaType,
						httpHeaders, entityStream);
	}

	public boolean isReadable(final Class<?> type, final Type genericType,
					final Annotation[] annotations, final MediaType mediaType) {
		return true;
	}

	public Object readFrom(final Class<Object> type, final Type genericType,
					final Annotation[] annotations, final MediaType mediaType,
					final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream)
					throws IOException {
		final RuntimeContext runtimeContext = RuntimeContextTLS.getRuntimeContext();
		final Map<String, String[]> map = runtimeContext.getAttribute(HttpServletRequest.class)
						.getParameterMap();
		try {
			final Object obj = type.newInstance();
			BeanUtils.populate(obj, map);
			return obj;
		} catch (final Exception ex) {
			throw new IOException(ex);
		}
	}

}
