package net.abusingjava.swing.magic;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("textarea")
public class TextArea extends TextComponent {

	@XmlAttribute("line-wrap")
	Boolean $lineWrap;

	@XmlAttribute("wrap-style-word")
	Boolean $wrapStyleWord;

	@XmlAttribute("tab-size")
	Integer $tabSize;

	@XmlAttribute("editable")
	Boolean $editable;

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($lineWrap == null) {
			$lineWrap = true;
		}
		if ($wrapStyleWord == null) {
			$wrapStyleWord = true;
		}
		if ($editable == null) {
			$editable = true;
		}
		JTextArea $c = new JTextArea($text);

		$c.setEditable($editable);
		$c.setLineWrap($lineWrap);
		$c.setWrapStyleWord($wrapStyleWord);
		if ($tabSize != null) {
			$c.setTabSize($tabSize);
		}

		$realComponent = $c;
		$component = new JScrollPane($c);

		super.create($main, $parent);
	}

}