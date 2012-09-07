package net.abusingjava.swing.magic;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import net.abusingjava.AbusingArrays;
import net.abusingjava.functions.AbusingFunctions;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.JavaType;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlTextContent;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

@XmlElement("combobox")
public class ComboBox extends Component {

	@XmlAttribute("auto-complete")
	Boolean $autoComplete;
	
	@XmlAttribute("from")
	JavaType $from;

	@XmlChildElements
	Val[] $values;
	
	@XmlAttribute
	String $selected;

	@XmlElement("val")
	public static class Val implements Comparable<Val> {
		
		public Val() {}
		
		public Val(final String $text) {
			this.$text = $text;
		}
		
		@XmlTextContent
		String $text;
		
		@XmlAttribute
		boolean $selected = false;
		
		@Override
		public String toString() {
			return $text;
		}
		
		@Override
		public int compareTo(final Val $obj) {
			return $text.compareTo($obj.$text);
		}
		
		@Override
		public boolean equals(final Object $object) {
			return $text.equals($object.toString());
		}
		
		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($autoComplete == null) {
			$autoComplete = false;
		}
		if ($height == null) {
			$height = new Value("27px");
		}
		
		if ($from != null) {
			Object[] $v = new Object[] {};
			if ($from.getJavaType().isEnum()) {
				$v = (Object[]) AbusingFunctions.callback($from.getJavaType(), "values").call();
			} else if (($from != null) && Iterable.class.isAssignableFrom($from.getJavaType())) {
				try {
					$v = AbusingArrays.toArray((Iterable<?>) $from.getJavaType().newInstance());
				} catch (Exception $exc) {
				}
			}
			Val[] $values = new Val[$v.length];
			for (int $i = 0; $i < $v.length; $i++) {
				$values[$i] = new Val($v[$i].toString());
			}
			this.$values = AbusingArrays.concat(this.$values, $values);
		}
		DefaultComboBoxModel $model;
		if ($values != null) {
			$model = new DefaultComboBoxModel($values);
		} else {
			$model = new DefaultComboBoxModel();
		}
		if ($selected != null) {
			for (int $i = 0; $i < $model.getSize(); $i++) {
				if ($model.getElementAt($i).toString().equals($selected)) {
					$model.setSelectedItem($model.getElementAt($i));
					break;
				}
			}
		}

		JComboBox $c = new JComboBox($model);
		
		if ($autoComplete) {
			AutoCompleteDecorator.decorate($c);
		}
		
		$component = $c;
		
		super.create($main, $parent);
	}
	
	
}