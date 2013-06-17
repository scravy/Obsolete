package net.scravy.weblet;

import java.util.HashMap;
import java.util.Map;

public class SessionMockup {

	public static Session genSession() {
		return genSession(null);
	}
	
	public static Session genSession(final User user) {
		final Map<String, Object> values = new HashMap<String, Object>();
		
		return new Session() {
			
			User currentUser = user;

			@Override
			public User getUser() {
				return currentUser;
			}

			@Override
			public void setUser(User p) {
				currentUser = p;
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T get(String key, T def) {
				if (values.containsKey(key)) {
					return (T) key;
				}
				return def;
			}

			@Override
			public <T> void set(String key, T value) {
				values.put(key, value);
			}
			
		};
	}
	
}
