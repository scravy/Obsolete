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
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlSelect;

@Author("Julian Fleischer")
@Version("2011-06-20")
public class AbusingRSS {

	@XmlElement("rss")
	public static class Rss {
		@XmlSelect("channel/title/text()")
		public String text;
		
		@XmlSelect("channel/description/text()")
		public String description;
		
		@XmlSelect("channel/link/text()")
		public URL link;
		
		@XmlSelect("channel/item")
		public Item[] items;
	}
	
	@XmlElement("item")
	public static class Item {
		@XmlSelect("title/text()")
		public String title;
		
		@XmlSelect("link/text()")
		public URL link;
		
		@XmlSelect("description/text()")
		public String description;
		
		@XmlSelect("pubDate/text()")
		public Date pubDate;
	}
	
	public static void main(final String... $args) throws MalformedURLException, IOException {
		Rss $feed = AbusingXML.loadXML(new URL("http://www.xkcd.com/rss.xml").openStream(), Rss.class);
		System.out.println($feed.items[2].title);
		System.out.println($feed.items[2].link);
		System.out.println($feed.items[2].pubDate);
	}
}
