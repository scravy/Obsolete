package net.abusingjava.swing.magic;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("label")
public class Label extends TextComponent {

	@XmlAttribute("text-align")
	String $textAlign;

	@XmlAttribute("vertical-align")
	String $verticalAlignment;

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($height == null) {
			$height = new Value("27px");
		}

		int $align = SwingConstants.LEFT;
		if ("right".equals($textAlign)) {
			$align = SwingConstants.RIGHT;
		} else if ("center".equals($textAlign)) {
			$align = SwingConstants.CENTER;
		}

		JLabel $c = new JLabel($text.trim(), $align);

		if ("top".equals($verticalAlignment)) {
			$c.setVerticalAlignment(SwingConstants.TOP);
		} else if ("bottom".equals($verticalAlignment)) {
			$c.setVerticalAlignment(SwingConstants.BOTTOM);
		}

		if (($background != null) && ($opaque == null)) {
			$opaque = true;
		}

		$component = $c;

		$realComponent = $c;

		super.create($main, $parent);
	}

}