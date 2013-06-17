package net.scravy.weblet.transform;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.scravy.weblet.Weblet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TransformerPoolFactory {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Weblet parent;

	private TransformerPoolFactory(Weblet parent) {
		this.parent = parent;
	}

	static TransformerPoolFactory newInstance(Weblet parent) {
		return new TransformerPoolFactory(parent);
	}

	TransformerPool newTransformerPool() {
		return newTransformerPool(CachingTransformerPool.class.getName());
	}

	TransformerPool newTransformerPool(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			if (TransformerPool.class.isAssignableFrom(clazz)) {
				for (Constructor<?> cr : clazz.getDeclaredConstructors()) {
					Class<?>[] params = cr.getParameterTypes();
					if (params.length == 1 &&
							Weblet.class.isAssignableFrom(params[0])) {
						return (TransformerPool) cr.newInstance(parent);
					}
				}
				return (TransformerPool) clazz.newInstance();
			}
		} catch (InstantiationException exc) {
			logger.warn(
					String.format(
							"TransformerPool “%s” could not be instantiated."
									+ " (this is configured by technetium.transformers.pool)",
							className), exc);
		} catch (IllegalAccessException exc) {
			logger.warn(
					String.format(
							"TransformerPool “%s” could not be instantiated –"
									+ " the Constructor is not accessible"
									+ " (this is configured by technetium.transformers.pool).",
							className), exc);
		} catch (IllegalArgumentException exc) {
			// should not happen
			logger.warn(
					String.format(
							"TransformerPool “%s” could not be instantiated –"
									+ " due to IllegalArgumentException: “%s”"
									+ " (this should not happen at all).",
							className, exc.getMessage()), exc);
		} catch (InvocationTargetException exc) {
			logger.warn(
					String.format(
							"An Exception occured while instantiating TransformerPool “%s” –"
									+ " due to %s: “%s”"
									+ " (this should not happen at all).",
							className, exc.getClass().getSimpleName(),
							exc.getMessage()), exc);
		} catch (ClassNotFoundException exc) {
			logger.warn(
					String.format("Could not find TransformerPool-class “%s”.",
							className), exc);
		}
		return null;
	}

}
