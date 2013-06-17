package net.scravy.weblet;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.persistence.EntityManagerFactory;
import javax.xml.bind.Marshaller;

import net.scravy.persistence.EntityManager;
import net.scravy.persistence.EntityManagerWrapper;
import net.scravy.technetium.util.UnmodifiableProperties;

public class ServletMockup {

	public static Weblet genServlet(final EntityManagerFactory emf) {

		final ThreadLocal<EntityManager> entityManager = new ThreadLocal<EntityManager>() {

			@Override
			public EntityManager get() {
				EntityManager em = super.get();
				if (!em.isOpen()) {
					remove();
					set(em = initialValue());
				}
				return em;
			}

			@Override
			protected EntityManager initialValue() {
				return EntityManagerWrapper.wrap(emf.createEntityManager());
			}

		};

		return new Weblet() {

			@Override
			public <T> T unwrap(Class<T> servletClass) {
				return null;
			}

			@Override
			public EntityManager db() {
				return entityManager.get();
			}

			@Override
			public Marshaller createMarshaller() {
				return null;
			}

			@Override
			public SortedMap<String[], Handler> getHandlers() {
				return new TreeMap<String[], Handler>();
			}

			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Module> getLoadedModules() {
				return Collections.EMPTY_MAP;
			}

			@Override
			public TimeZone getDefaultTimeZone() {
				return TimeZone.getDefault();
			}

			@Override
			public TimeZone getTimeZoneForRequest(Request request) {
				Session s = request.getSession();
				if (s != null) {
					User u = s.getUser();
					if (u != null) {
						TimeZone z = u.getUserTimeZone();
						if (z != null) {
							return z;
						}
					}
				}
				return getDefaultTimeZone();
			}

			@Override
			public UnmodifiableProperties getProperties() {
				return null;
			}
		};
	}
}
