package net.abusingjava.swing.magic;

import javax.swing.JPasswordField;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("password")
public class Password extends TextComponent {
	
	public static class EchoChar {
		public final Character $char;
		
		public EchoChar(final String $value) {
			if ($value.length() > 0) {
				$char = $value.charAt(0);
			} else {
				$char = '*';
			}
		}
	}
	
	@XmlAttribute("echo-char")
	EchoChar $echoChar;
	
	@Override
	public String getText() {
		return super.getText();
	}
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($height == null) {
			$height = new Value("27px");
		}
		final JPasswordField $c = new JPasswordField($text);
		
		$component = $c;

		super.create($main, $parent);
	}
	
}