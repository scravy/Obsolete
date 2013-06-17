/**
 * Auto-magic web forms for {@link net.scravy.weblet.Weblet}s.
 * <p>
 * A form is part of a {@link net.scravy.weblet.Module} and can specified in XML.
 * The Weblet automatically handles form presentation, submission,
 * {@link net.scravy.weblet.forms.Converter conversion} of String values to
 * typed values, as well as {@link net.scravy.weblet.forms.Validator validating}
 * those values.
 * </p>
 * <p>
 * The final handling of a successfully validated form is left to a
 * {@link net.scravy.weblet.forms.FormHandler}, specified in the Forms
 * definition ({@link net.scravy.weblet.xml.FormXml}).
 * </p>
 * 
 * @since 1.0
 * @author Julian Fleischer
 */
package net.scravy.weblet.forms;

