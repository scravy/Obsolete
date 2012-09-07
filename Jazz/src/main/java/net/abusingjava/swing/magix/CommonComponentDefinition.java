package net.abusingjava.swing.magix;

import java.awt.Font;

import net.abusingjava.swing.magix.types.*;
import net.abusingjava.xml.XmlAttribute;

public class CommonComponentDefinition {

	@XmlAttribute("color")
	Color $color;
	
	public Color getColor() {
		return $color;
	}
	
	@XmlAttribute("background-color")
	Color $backgroundColor;
	
	public Color getBackgroundColor() {
		return $backgroundColor;
	}
	
	@XmlAttribute("font")
	Font $font;
	
	@XmlAttribute("font-style")
	FontStyle $fontStyle;
	
	public FontStyle getFontStyle() {
		return $fontStyle;
	}
	
	@XmlAttribute("font-weight")
	FontWeight $fontWeight;
	
	public FontWeight getFontWeight() {
		return $fontWeight;
	}
	
	@XmlAttribute("font-size")
	Value $fontSize;
	
	public Value getFontSize() {
		return $fontSize;
	}
	
	// Events
	@XmlAttribute("onmouseover")
	MethodType $onMouseOver;
	
	@XmlAttribute("onmouseout")
	MethodType $onMouseOut;
	
	@XmlAttribute("onmousemove")
	MethodType $onMouseMove;
	
	@XmlAttribute("onclick")
	MethodType $onClick;
	
	@XmlAttribute("ondblclick")
	MethodType $onDblClick;

	@XmlAttribute("onmousewheel")
	MethodType $onMouseWheel;
	
	@XmlAttribute("onmousedown")
	MethodType $onMouseDown;
	
	@XmlAttribute("onmouseup")
	MethodType $onMouseUp;
	
	@XmlAttribute("onkeypress")
	MethodType $onKeyPress;
	
	@XmlAttribute("onkeyup")
	MethodType $onKeyUp;
	
	@XmlAttribute("onkeydown")
	MethodType $onKeyDown;
	
	@XmlAttribute("onfocus")
	MethodType $onFocus;
	
	@XmlAttribute("onblur")
	MethodType $onBlur;
	
	// “Special” events
	
	@XmlAttribute("onchange")
	MethodType $onChange;

	@XmlAttribute("onselect")
	MethodType $onSelect;
	
}
