package net.abusingjava.swing.v1.components;

import net.abusingjava.xml.XmlAttribute;

public class XPanelDefinition extends XBoxDefinition {

	@XmlAttribute
	String $title = "";
	public String getTitle() {
		return $title;
	}
	
}
