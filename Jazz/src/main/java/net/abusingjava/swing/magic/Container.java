package net.abusingjava.swing.magic;

import java.util.Iterator;

import javax.swing.JScrollPane;

import net.abusingjava.AbusingArrays;
import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;

@Author("Julian Fleischer")
@Version("2011-08-21")
@Since(value = "2011-08-21", version = "1.0")
abstract public class Container extends Component implements Iterable<Component> {

	@XmlChildElements({HBox.class, VBox.class, Any.class, FixedBox.class, Button.class,
		Cards.class, CheckBox.class, ComboBox.class, DatePicker.class, List.class,
		Numeric.class, Panel.class, Panes.class, Password.class, ProgressBar.class,
		Slider.class, Table.class, Tabs.class, TextArea.class, TextField.class,
		ToggleButton.class, Tree.class, Label.class, HSplit.class, VSplit.class,
		MultiList.class})
	Component[] $components = new Component[] {};

	@XmlAttribute
	Value $padding = new Value("0px");
	
	@XmlAttribute("padding-x")
	Value $paddingX;
	
	@XmlAttribute("padding-y")
	Value $paddingY;
	
	@XmlAttribute("padding-left")
	Value $paddingLeft;
	
	@XmlAttribute("padding-right")
	Value $paddingRight;
	
	@XmlAttribute("padding-top")
	Value $paddingTop;
	
	@XmlAttribute("padding-bottom")
	Value $paddingBottom;

	@XmlAttribute("scrollable")
	Boolean $scrollable;
	
	
	@Override
	public Iterator<Component> iterator() {
		return AbusingArrays.array($components).iterator();
	}
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($scrollable == null) {
			$scrollable = false;
		}
		if ($paddingX == null) {
			$paddingX = $padding;
		}
		if ($paddingY == null) {
			$paddingY = $padding;
		}
		if ($paddingTop == null) {
			$paddingTop = $paddingY;
		}
		if ($paddingBottom == null) {
			$paddingBottom = $paddingY;
		}
		if ($paddingLeft == null) {
			$paddingLeft = $paddingX;
		}
		if ($paddingRight == null) {
			$paddingRight = $paddingX;
		}

		if ($parent != null) {
			MagicPanel $c = new MagicPanel($main, this);
			$realComponent = $c;
			$component = $scrollable ? new JScrollPane($c) : $c;
			
			super.create($main, $parent);
		} else {
			$component = $main;
		}
	}
	
	public Value getPaddingLeft() {
		return $paddingLeft;
	}
	
	public Value getPaddingRight() {
		return $paddingRight;
	}
	
	public Value getPaddingTop() {
		return $paddingTop;
	}
	
	public Value getPaddingBottom() {
		return $paddingBottom;
	}
}