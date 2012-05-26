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
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlNamespace;
import net.abusingjava.xml.XmlNamespaceDefinition;
import net.abusingjava.xml.XmlSelect;



@Author("Julian Fleischer")
@Version("2011-06-20")
public class AbusingXHTML2 {

	@XmlElement("html")
	@XmlNamespace("http://www.w3.org/1999/xhtml")
	public static class HTMLDocument {
		
		@XmlSelect(
			namespaceContext = @XmlNamespaceDefinition(prefix = "xh", uri = "http://www.w3.org/1999/xhtml"),
			value = "xh:head/xh:title/text()"
		)
		public String $title;
		
		public String getTitle() {
			return $title;
		}
		
		
	}
	

	public static void main(final String... $args) {
		HTMLDocument $document = AbusingXML.loadXML(AbusingXHTML.class.getResourceAsStream("xhtml.xml"), HTMLDocument.class);
		
		System.out.println($document.getTitle());
	}
	
}
