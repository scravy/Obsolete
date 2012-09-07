package net.abusingjava.swing.magic;

import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("menu-bar")
public class MenuBar {

	@XmlChildElements
	Menu[] $menus = new Menu[0];
	
	public Menu[] getMenus() {
		return $menus;
	}
}
