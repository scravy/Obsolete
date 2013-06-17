package net.scravy.weblet;


public class RequestMockup {

	public static Request genRequest(String url) {
		return genRequest(RequestMethod.GET, url, null);
	}
	
	public static Request genRequest(final RequestMethod method, String url) {
		return genRequest(method, url, null);
	}
	
	public static Request genRequest(final RequestMethod method,
			final String url, final Session s) {

		String[] urlParts = url.split("\\?");
		String pathInfo = urlParts[0];

		if (pathInfo != null) {
			int i = 0;
			while (i < pathInfo.length() && pathInfo.charAt(i) == '/') {
				i++;
			}
			pathInfo = pathInfo.substring(i);
		}

		final String queryString = urlParts.length == 2 ? urlParts[1] : "";
		final String[] path = pathInfo == null || pathInfo.isEmpty() ? new String[0]
				: pathInfo.split("/+");

		final Session session = s == null ? SessionMockup.genSession() : s;

		return new AbstractRequest(queryString) {

			@Override
			public Session getSession() {
				return session;
			}

			@Override
			public String[] getPath() {
				return path;
			}

			@Override
			public String getRootPath() {
				return "/rootPath";
			}

			@Override
			public String getHeader(String header) {
				if ("Host".equals(header)) {
					return "example.com";
				} else if ("Connection".equals(header)) {
					return "keep-alive";
				} else if ("User-Agent".equals(header)) {
					return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.56 Safari/536.5";
				} else if ("Accept".equals(header)) { 
					return "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
				} else if ("Accept-Encoding".equals(header)) {
					return "gzip,deflate";
				} else if ("Accept-Language".equals(header)) {
					return "en-US,en;q=0.8";
				} else if ("Accept-Charset".equals(header)) {
					return "ISO-8859-1,utf-8;q=0.7,*;q=0.3";
				}
				return "";
			}

			@Override
			public RequestMethod getMethod() {
				return method;
			}
		};
	}
}
