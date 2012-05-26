/* Part of the AbusingJava-Library.
 * 
 * Source:  http://github.com/scravy/AbusingJava
 * Home:    http://www.abusingjava.net/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.abusingjava.xml.example;

import net.abusingjava.Author;
import net.abusingjava.Version;
import net.abusingjava.xml.AbusingXML;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElement;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlTextContent;

@Author("Julian Fleischer")
@Version("2011-06-20")
public class AbusingXHTML {

	@XmlElement("html")
	public static class Html {
		@XmlChildElement
		public Head head;
		
		@XmlChildElement
		public Body body;
	}
	
	@XmlElement("head")
	public static class Head {
		
		@XmlChildElement
		public Title title;
		
		@XmlChildElements
		Style[] style;
	}
	
	public static class Text {

		@XmlTextContent
		private String value;
		
		public String text() {
			return value;
		}
	}
	
	@XmlElement("title")
	public static class Title extends Text {
		
	}
	
	@XmlElement("style")
	public static class Style extends Text {
		
		@XmlAttribute
		public String $type = "text/css";
		
	}
	
	@XmlElement("body")
	public static class Body {
		
		@XmlChildElements
		public Paragraph[] p = {};
		
	}
	
	@XmlElement("p")
	public static class Paragraph extends Text {
		
	}
	
	public static void main(final String... $args) {
		Html $html = AbusingXML.loadXML(AbusingXHTML.class.getResourceAsStream("xhtml.xml"), Html.class);
		
		System.out.println($html.head.title.text());
		System.out.println($html.head.style[0].$type);
		System.out.println($html.body.p[1].text());
	}
	
}
