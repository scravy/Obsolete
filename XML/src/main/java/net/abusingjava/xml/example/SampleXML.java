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

import java.util.Date;
import java.util.List;

import net.abusingjava.xml.AbusingXML;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElement;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlNamespace;
import net.abusingjava.xml.XmlSelect;
import net.abusingjava.xml.XmlTextContent;

@XmlElement("sample")
public class SampleXML {
	
	@XmlElement
	public static class title extends SampleXML {
		
		@XmlTextContent
		String $text;
		
		@Override
		public String toString() {
			return $text;
		}
	}
	
	@XmlAttribute
	String $name;
	
	@XmlTextContent
	String $value;
	
	@XmlAttribute("test")
	String $attr = "default value";
	
	@XmlAttribute
	@XmlNamespace("http://technodrom.scravy.de/0/author")
	String $author;
	
	@XmlChildElement
	title $title;
	
	//@XmlSelect("//name/text()")
	//String $xpath;
	
	@XmlSelect("count(sample)")
	int $xpath;
	
	@XmlChildElements({SampleXML.class, title.class})
	SampleXML[] $children;
	
	@XmlChildElements
	SampleXML[] $childz;
	
	@XmlChildElements(SampleXML.class)
	List<SampleXML> $c;
	
	@XmlAttribute
	Date $num;
	
	public static void main(final String... $args) {
		new Runnable() {
			@Override
			public void run() {
				SampleXML $xml = AbusingXML.loadXML(getClass().getResourceAsStream("sample.xml"), SampleXML.class);
				System.out.println($xml.$childz[0].$attr);
				System.out.println($xml.$children[2].$attr);
				System.out.println($xml.$c.get(1).$num);
				System.out.println($xml.$c.get(0).$author);
				System.out.println($xml.$children[0]);
				System.out.println($xml.$title);
				System.out.println($xml.$xpath);
			}
		}.run();
	}
	
}
