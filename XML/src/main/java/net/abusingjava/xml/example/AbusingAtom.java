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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import net.abusingjava.Author;
import net.abusingjava.Version;
import net.abusingjava.xml.AbusingXML;
import net.abusingjava.xml.XmlChildElement;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlNamespace;
import net.abusingjava.xml.XmlTextContent;

@Author("Julian Fleischer")
@Version("2011-06-20")
public class AbusingAtom {

	@XmlElement("feed")
	@XmlNamespace("http://www.w3.org/2005/Atom")
	public static class Atom {
		
		@XmlChildElement
		public Title title;
		
		@XmlChildElement
		public Updated updated;
		
	}
	
	@XmlElement("title")
	public static class Title {
		
		@XmlTextContent
		private String $text;
		
		public String text() {
			return $text;
		}
	}
	
	@XmlElement("updated")
	public static class Updated {

		@XmlTextContent
		private Date $value;
		
		public Date value() {
			return $value;
		}
	}
	
	public static void main(final String... $args) throws MalformedURLException, IOException {
		Atom $feed = AbusingXML.loadXML(new URL("http://www.xkcd.com/atom.xml").openStream(), Atom.class);
		System.out.println($feed.updated.value());
		System.out.println($feed.title.text());
	}
}
