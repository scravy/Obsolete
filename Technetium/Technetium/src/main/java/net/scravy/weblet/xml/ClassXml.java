package net.scravy.weblet.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A wrapper for Class-objects which allows to serialize and unserialize them as
 * XML easily.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@XmlRootElement(name = "class")
public class ClassXml {

	public static class AppliedAnnotationXml {

		private String simpleName;
		private String name;
		private String canonicalName;

		private Map<String, String> values = new TreeMap<String, String>();

		AppliedAnnotationXml() {

		}

		public AppliedAnnotationXml(Class<?> clazz, Annotation a) {
			Class<?> annotationType = a.annotationType();

			this.name = annotationType.getName();
			this.simpleName = annotationType.getSimpleName();
			this.canonicalName = annotationType.getCanonicalName();

			for (Method method : annotationType.getDeclaredMethods()) {
				try {
					values.put(method.getName(),
							String.valueOf(method.invoke(a)));
				} catch (Exception exc) {
					System.out.println(exc.getMessage());
				}
			}
		}

		public Map<String, String> getValues() {
			return values;
		}

		public void setValues(Map<String, String> values) {
			this.values = values;
		}

		@XmlAttribute
		public String getSimpleName() {
			return simpleName;
		}

		public void setSimpleName(String simpleName) {
			this.simpleName = simpleName;
		}

		@XmlAttribute
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@XmlAttribute
		public String getCanonicalName() {
			return canonicalName;
		}

		public void setCanonicalName(String canonicalName) {
			this.canonicalName = canonicalName;
		}

	}

	private String simpleName;
	private String canonicalName;
	private String name;

	private boolean isStatic = false;
	private boolean isStrict = false;

	private boolean isPublic = false;
	private boolean isProtected = false;
	private boolean isPrivate = false;
	private boolean isPackagePrivate = false;

	private List<AppliedAnnotationXml> annotations = new ArrayList<AppliedAnnotationXml>();

	ClassXml() {
		// JAXB needs the empty constructor
	}

	public ClassXml(Class<?> clazz) {

		this.name = clazz.getName();
		this.simpleName = clazz.getSimpleName();
		this.canonicalName = clazz.getCanonicalName();

		int modifiers = clazz.getModifiers();
		if ((modifiers & Modifier.PUBLIC) != 0) {
			isPublic = true;
		} else if ((modifiers & Modifier.PROTECTED) != 0) {
			isProtected = true;
		} else if ((modifiers & Modifier.PRIVATE) != 0) {
			isPrivate = true;
		} else {
			isPackagePrivate = true;
		}

		isStrict = (modifiers & Modifier.STRICT) != 0;
		isStatic = (modifiers & Modifier.STATIC) != 0;

		for (Annotation a : clazz.getAnnotations()) {
			annotations.add(new AppliedAnnotationXml(clazz, a));
		}
	}

	@XmlAttribute
	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	@XmlAttribute
	public String getCanonicalName() {
		return canonicalName;
	}

	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	@XmlAttribute
	public boolean isStrict() {
		return isStrict;
	}

	public void setStrict(boolean isStrict) {
		this.isStrict = isStrict;
	}

	@XmlAttribute
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	@XmlAttribute
	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	@XmlAttribute
	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	@XmlAttribute
	public boolean isPackagePrivate() {
		return isPackagePrivate;
	}

	public void setPackagePrivate(boolean isPackagePrivate) {
		this.isPackagePrivate = isPackagePrivate;
	}

	@XmlElements(value = @XmlElement(name = "annotation"))
	public List<AppliedAnnotationXml> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AppliedAnnotationXml> annotations) {
		this.annotations = annotations;
	}
}
