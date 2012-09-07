package net.abusingjava.swing.magix.types;

public class JavaType {
	
	final Class<?> $class;
	
	public JavaType(final String $javaType) throws ClassNotFoundException {
		Class<?> $class = null;
		if ("string".equalsIgnoreCase($javaType)) {
			$class = java.lang.String.class;
		} else if ("byte".equalsIgnoreCase($javaType)) {
			$class = Byte.class;
		} else if ("short".equalsIgnoreCase($javaType)) {
			$class = Short.class;
		} else if ("long".equalsIgnoreCase($javaType)) {
			$class = Long.class;
		} else if ("int".equalsIgnoreCase($javaType) || "integer".equalsIgnoreCase($javaType)) {
			$class = Integer.class;
		} else if ("boolean".equalsIgnoreCase($javaType) || "bool".equalsIgnoreCase($javaType)) {
			$class = Boolean.class;
		} else if ("float".equalsIgnoreCase($javaType)) {
			$class = Float.class;
		} else if ("double".equalsIgnoreCase($javaType)) {
			$class = Double.class;
		} else if ("date".equalsIgnoreCase($javaType)) {
			$class = java.util.Date.class;
		} else if ("object".equalsIgnoreCase($javaType)) {
			$class = java.lang.Object.class;
		}
		
		if ($class == null)
			$class = Class.forName($javaType);
		
		this.$class = $class;
	}
	
	public JavaType(final Class<?> $theClass) {
		$class = $theClass;
	}
	
	public Class<?> getJavaType() {
		return $class;
	}
}