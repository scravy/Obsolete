package net.abusingjava.swing.magic;

import net.abusingjava.functions.AbusingFunctions;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.xml.XmlTextContent;


abstract public class TextComponent extends Component {
	
	@XmlTextContent
	String $text = "";
	
	public void setText(final String $text) {
		AbusingFunctions.callback($realComponent, "setText").call($text);
	}
	
	public String getText() {
		return AbusingFunctions.callback($realComponent, "getText").call().toString();
	}
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {

		$text = $text.trim();
		
		super.create($main, $parent);
	}
	
}