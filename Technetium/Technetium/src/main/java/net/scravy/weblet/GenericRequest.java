package net.scravy.weblet;

import javax.servlet.http.HttpServletRequest;

public class GenericRequest extends AbstractRequest {

	private final Session session;
	private final HttpServletRequest request;
	private final RequestMethod requestMethod;
	private final String[] path;

	public GenericRequest(RequestMethod method, HttpServletRequest request) {
		super(request.getQueryString(), request.getParameterMap());

		this.session = new GenericSession(request.getSession(true));
		this.request = request;
		this.requestMethod = method;

		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			path = new String[0];
		} else {
			int i = 0;
			while (i < pathInfo.length() && pathInfo.charAt(i) == '/') {
				i++;
			}
			pathInfo = pathInfo.substring(i);
			if (pathInfo.isEmpty()) {
				path = new String[0];
			} else {
				path = pathInfo.split("/+");
			}
		}
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public String[] getPath() {
		return path;
	}

	@Override
	public String getHeader(String header) {
		String value = request.getHeader(header);
		return value == null ? "" : value;
	}

	@Override
	public RequestMethod getMethod() {
		return requestMethod;
	}

	@Override
	public String getRootPath() {
		return request.getContextPath() + request.getServletPath();
	}
}
