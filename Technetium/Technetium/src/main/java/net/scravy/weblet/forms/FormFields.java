package net.scravy.weblet.forms;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare additional FormFields on a Java class – this annotation is not used
 * currently, use an XML-definition instead.
 * 
 * @author Julian Fleischer
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface FormFields {

	String[] order() default {};

	FormField[] fields();

	FromClass[] from() default {};
}