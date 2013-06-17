package net.scravy.weblet;

import javax.servlet.http.HttpSession;



class GenericSession implements Session {

	private final HttpSession session;
	
	GenericSession(HttpSession session) {
		this.session = session;
	}
	
	@Override
	public User getUser() {
		return get("__user__", (User) null);
	}
	
	@Override
	public void setUser(User p) {
		set("__user__", p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, T def) {
		Object value = session.getAttribute(key);
		
		if (value == null) {
			return def;
		}
		
		try {
			return (T) value;
		} catch (ClassCastException exc) {
			return def;
		}
	}

	@Override
	public <T> void set(String key, T value) {
		session.setAttribute(key, value);
	}

}
