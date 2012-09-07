package net.abusingjava.swing.magic;

import java.awt.Insets;

import javax.swing.JButton;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Unit;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("button")
public class Button extends TextComponent {

	@XmlAttribute("insets")
	Value $insets = null;

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($height == null) {
			$height = new Value("27px");
		}
		
		JButton $button = new JButton($text.trim());
		$component = $button;
		
		if (($insets != null) && ($insets.getUnit() == Unit.PIXEL)) {
			Insets $in = $button.getMargin();
			$in.set($in.top, $insets.getValue(), $in.bottom, $insets.getValue());
			$button.setMargin($in);
		}
		
		super.create($main, $parent);
	}
	
}