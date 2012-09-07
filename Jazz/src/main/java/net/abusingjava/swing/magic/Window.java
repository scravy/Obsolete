package net.abusingjava.swing.magic;

import java.awt.Dimension;

import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElement;
import net.abusingjava.xml.XmlElement;

@XmlElement("window")
public class Window {

	@XmlAttribute("min-width")
	Integer $minWidth;
	
	@XmlAttribute("max-width")
	Integer $maxWidth;
	
	@XmlAttribute("min-height")
	Integer $minHeight;
	
	@XmlAttribute("max-height")
	Integer $maxHeight;
	
	@XmlAttribute("width")
	Integer $width;
	
	@XmlAttribute("height")
	Integer $height;

	@XmlAttribute("title")
	String $title = "";
	
	@XmlAttribute
	Boolean $resizable;
	
	@XmlChildElement
	Panel $contentPane;

	@XmlChildElement
	MenuBar $menuBar;
	
	
	public boolean hasMinSize() {
		return ($minWidth != null) && ($minHeight != null);
	}
	
	public Dimension getMinSize() {
		return new Dimension($minWidth, $minHeight);
	}
	
	public boolean hasSize() {
		return ($width != null) && ($height != null);
	}
	
	public Dimension getSize() {
		return new Dimension($width, $height);
	}
	
	public boolean getResizable() {
		return $resizable == null ? true : $resizable;
	}
	
	public String getTitle() {
		return $title;
	}
	
	public Panel getPanel() {
		if ($contentPane == null) {
			$contentPane = new Panel();
			$contentPane.$containers = new Container[] {
				new FixedBox()
			};
		}
		return $contentPane;
	}
	
	public boolean hasMenuBar() {
		return $menuBar != null;
	}
	
	public MenuBar getMenuBar() {
		return $menuBar;
	}
}
