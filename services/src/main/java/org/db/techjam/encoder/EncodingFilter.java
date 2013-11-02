package org.db.techjam.encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.IOUtils;

public class EncodingFilter implements Filter {

	private static Map<String, String> locales = new HashMap<String, String>();

	static {
		locales.put("cz", "iso-8859-2");
		locales.put("de", "iso-8859-1");
		locales.put("en", "windows-1252"); // RTC 24089 - use same encoding
											// charset as legacy so as to
											// interpret/save symbols like Û
											// juat the way legacy does.
		locales.put("es", "iso-8859-1");
		locales.put("gb", "GB18030");
		locales.put("hu", "iso-8859-2");
		locales.put("jp", "SJIS");
		locales.put("kr", "EUC-KR");
		locales.put("pl", "iso-8859-2");
		locales.put("ru", "windows-1251");
		locales.put("b5", "Big5");
		locales.put("pt", "iso-8859-1");
		locales.put("br", "iso-8859-1");
		locales.put("it", "iso-8859-1");
		locales.put("fr", "iso-8859-1"); // ? charset
		locales.put("nl", "iso-8859-1");
		locales.put("vn", "windows-1258");
		locales.put("tr", "iso-8859-9");
		locales.put("ua", "windows-1251");
	}

	public static Map<String, String> getLocales() {
		return locales;
	}

	public static void setLocales(Map<String, String> locals) {
		EncodingFilter.locales = locals;
	}

	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {

		if (!(res instanceof HttpServletResponse)
				|| !(req instanceof HttpServletRequest)) {
			throw new ServletException("This filter only supports HTTP");
		}

		final HttpServletRequest httpReq = (HttpServletRequest) req;
		final HttpServletResponse httpRes = (HttpServletResponse) res;
		final String locale = getLocale(httpReq);
		final String charset = locales.get(locale);

		final boolean filterInput = httpReq.getContentType() != null
				&& httpReq.getContentType().contains("application/json");

		final JsonCharsetEncoder enc = new JsonCharsetEncoder(charset);

		ServletRequest requestForChain = httpReq;

		if (filterInput) {
			final InputStream reqIs = req.getInputStream();

			final byte[] inputBytes = IOUtils.toByteArray(reqIs);
			final String inputString = new String(inputBytes, "UTF-8");
			final String encodedString = enc.convertUtf8ToCharset(inputString);
			final byte[] newInputBytes = encodedString.getBytes("UTF-8");

			final BufferedHttpRequestWrapper requestWrapper = new BufferedHttpRequestWrapper(
					httpReq, newInputBytes);
			requestForChain = requestWrapper;
		}

		final BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper(
				httpRes);

		chain.doFilter(requestForChain, responseWrapper);

		final byte[] origData = responseWrapper.getBuffer();
		byte[] outputBytes = origData;

		// final boolean filterOutput = false;
		final boolean filterOutput = httpRes.getContentType() != null
				&& httpRes.getContentType().contains("application/json");

		if (filterOutput) {

			final String str = new String(origData, "UTF-8");
			final String finalString = enc.convertCharsetToUtf8(str);
			outputBytes = finalString.getBytes("UTF-8");
			res.setCharacterEncoding("UTF-8");
		}

		res.setContentLength(outputBytes.length);
		if (outputBytes.length > 0) {
			res.getOutputStream().write(outputBytes);
			res.flushBuffer();
		}
	}

	private String getLocale(final HttpServletRequest req) {
		final Cookie[] cookies = req.getCookies();
		if (cookies == null)
			return "en";
		for (final Cookie c : cookies) {
			if ("DBDILANG".equals(c.getName())) {
				final String val = c.getValue().toLowerCase();
				if (locales.containsKey(val)) {
					return val;
				}
			}
		}
		return "en";
	}

	public void destroy() {
		// empty

	}

	public void init(final FilterConfig arg0) throws ServletException {
		// EMPTY

	}

	public static class BufferedServletOutputStream extends ServletOutputStream {
		// the actual buffer
		private transient ByteArrayOutputStream bos = new ByteArrayOutputStream();

		/**
		 * @return the contents of the buffer.
		 */
		public byte[] getBuffer() {
			return bos.toByteArray();
		}

		/**
		 * This method must be defined for custom servlet output streams.
		 */
		@Override
		public void write(final int data) {
			bos.write(data);
		}

		// BufferedHttpResponseWrapper calls this method
		public void reset() {
			bos.reset();
		}

		// BufferedHttpResponseWrapper calls this method
		public void setBufferSize(final int size) {
			// no way to resize an existing ByteArrayOutputStream
			bos = new ByteArrayOutputStream(size);
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			// TODO Auto-generated method stub

		}
	}

	public static class BufferedServletInputStream extends ServletInputStream {
		// the actual buffer
		private transient final ByteArrayInputStream bos;

		public BufferedServletInputStream(final byte[] bytes) {
			bos = new ByteArrayInputStream(bytes);
		}

		// BufferedHttpResponseWrapper calls this method
		@Override
		public void reset() {
			bos.reset();
		}

		@Override
		public int read() throws IOException {
			return bos.read();
		}

		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {
			// TODO Auto-generated method stub

		}
	}

	public static class BufferedHttpResponseWrapper extends
			HttpServletResponseWrapper {
		private transient final BufferedServletOutputStream bufferedServletOut = new BufferedServletOutputStream();
		private transient PrintWriter printWriter = null;

		public void setOutputStream(ServletOutputStream outptStream) {
			this.outputStream = outptStream;
		}

		private ServletOutputStream outputStream = null;

		public BufferedHttpResponseWrapper(
				final HttpServletResponse origResponse) {
			super(origResponse);
		}

		public byte[] getBuffer() {
			return bufferedServletOut.getBuffer();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (outputStream != null) {
				throw new IllegalStateException(
						"The Servlet API forbids calling getWriter( ) after"
								+ " getOutputStream( ) has been called");
			}

			if (printWriter == null) {
				printWriter = new PrintWriter(bufferedServletOut);
			}
			return printWriter;
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			if (printWriter != null) {
				throw new IllegalStateException(
						"The Servlet API forbids calling getOutputStream( ) after"
								+ " getWriter( ) has been called");
			}

			if (outputStream == null) {
				outputStream = bufferedServletOut;
			}
			return outputStream;
		}

		// override methods that deal with the response buffer

		@Override
		public void flushBuffer() throws IOException {
			if (outputStream != null) {
				outputStream.flush();
			} else if (printWriter != null) {
				printWriter.flush();
			}
		}

		@Override
		public int getBufferSize() {
			return bufferedServletOut.getBuffer().length;
		}

		@Override
		public void reset() {
			bufferedServletOut.reset();
		}

		@Override
		public void resetBuffer() {
			bufferedServletOut.reset();
		}

		@Override
		public void setBufferSize(final int size) {
			bufferedServletOut.setBufferSize(size);
		}
	}

	public static class BufferedHttpRequestWrapper extends
			HttpServletRequestWrapper {

		private transient final BufferedServletInputStream bufferedServletIn;

		public BufferedHttpRequestWrapper(final HttpServletRequest arg0,
				final byte[] bytes) {
			super(arg0);
			bufferedServletIn = new BufferedServletInputStream(bytes);
		}

		@Override
		public ServletInputStream getInputStream() {
			return bufferedServletIn;
		}

	}

}
