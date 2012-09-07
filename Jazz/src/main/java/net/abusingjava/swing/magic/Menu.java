package net.abusingjava.swing.magic;

import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("menu")
public class Menu extends MenuItem {
	
	@XmlChildElements({Menu.class, MenuItem.class})
	MenuItem[] $items;
	
	@XmlAttribute
	String $name;
	
	public MenuItem[] getMenuItems() {
		return $items;
	}
	
	public String getName() {
		return $name;
	}
}
