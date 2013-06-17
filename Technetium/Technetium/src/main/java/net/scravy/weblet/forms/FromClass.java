package net.scravy.weblet.forms;

/**
 * Import FormFields from a class – this annotation is not used currently, use
 * an XML-definition instead.
 * <p>
 * This annotation causes all bean properties from the ({@link #value()
 * referenced Class}) to be imported as {@link FormField}. If certain properties
 * should not be imported, {@link #exclude() exclude them}. You can also limit
 * the imported properties to a certain set via {@link #include()}. If
 * {@link #include()} is empty, all properties except the excluded ones are
 * included. If {@link FromClass#include()} is not empty, only the specified
 * properties are included. Excluded properties are never included.
 * </p>
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @see FormFields
 */
public @interface FromClass {

	/**
	 * The Class whose properties are to be included.
	 * 
	 * @since 1.0
	 */
	Class<?> value();

	/**
	 * Limits the included properties to the specified properties.
	 * 
	 * @since 1.0
	 */
	String[] include() default {};

	/**
	 * Excludes the specified properties from inclusion.
	 * 
	 * @since 1.0
	 */
	String[] exclude() default {};

}