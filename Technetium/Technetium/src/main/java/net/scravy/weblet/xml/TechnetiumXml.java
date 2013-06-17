package net.scravy.weblet.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.weblet.Module;

/**
 * Structure definition and POJO-counterpart of <code>technetium.xml</code>.
 * <p>
 * The format of <code>technetium.xml</code> is rather simple:
 * </p>
 * <h2>Examples</h2>
 * 
 * <pre>
 * &lt;technetium>
 *	&lt;weblet name="your-weblet" defaultHandler="net.scravy.technetium.DefaultHandler">
 *		&lt;handlers>
 *			&lt;handler path="/" handler="your.package.Welcome" />
 *		&lt;/handlers>
 *	&lt;/weblet>
 *	&lt;/technetium>
 * </pre>
 * 
 * <pre>
 * &lt;technetium>
 *	&lt;weblet name="your-weblet" defaultHandler="net.scravy.technetium.DefaultHandler">
 *		&lt;handlers>
 *			&lt;handler path="/" handler="your.package.Welcome" />
 *			&lt;handler path="*.css" handler="your.package.CSS" />
 *			&lt;handler path="*.htm" handler="your.package.HTML" />
 *			&lt;handler path="*.html" handler="your.package.HTML" />
 *		&lt;/handlers>
 *	&lt;/weblet>
 * &lt;/technetium>
 * </pre>
 *
 * <pre>
 * &lt;technetium>
 *	&lt;weblet name="your-weblet" defaultHandler="some.package.SomeDefaultHandler">
 *		&lt;modules>
 *			&lt;module descriptor="your/package/Main.xml" />
 *		&lt;/modules>
 *	&lt;/weblet>
 *	&lt;weblet name="another-weblet" defaultHandler="my.package.CustomDefaultHandler">
 * 		&lt;modules>
 * 			&lt;module descriptor="another/package/Main.xml" />
 *		&lt;/modules>
 *	&lt;/weblet>
 * &lt;/technetium>
 * </pre>
 * 
 * <pre>
 * &lt;technetium>
 * 	&lt;weblet name="your-weblet" defaultHandler="net.scravy.technetium.DefaultHandler">
 * 		&lt;handlers>
 * 			&lt;handler path="/" handler="your.package.Welcome" />
 *  		&lt;handler path="*.css" handler="your.package.CSS" />
 * 			&lt;handler path="*.htm" handler="your.package.HTML" />
 * 			&lt;handler path="*.html" handler="your.package.HTML" />
 * 		&lt;/handlers>
 * 		&lt;modules>
 *  		&lt;module descriptor="your/package/Main.xml" />
 *  		&lt;module descriptor="your/package/About.xml" />
 * 			&lt;module descriptor="your/package/Auth.xml" />
 * 			&lt;module descriptor="your/package/BBoard.xml" />
 * 		&lt;/modules>
 * 	&lt;/weblet>
 * &lt;/technetium>
 * </pre>
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @see WebletXml
 * @see Module
 */
@XmlRootElement(name = "technetium")
public class TechnetiumXml {

	private List<WebletXml> weblets = new ArrayList<WebletXml>();

	@XmlElements(value = @XmlElement(name = "weblet"))
	public List<WebletXml> getWeblets() {
		return weblets;
	}

	public void setWeblets(List<WebletXml> weblets) {
		this.weblets = weblets;
	}
}