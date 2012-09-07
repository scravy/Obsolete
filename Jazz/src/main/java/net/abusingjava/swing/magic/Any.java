package net.abusingjava.swing.magic;

import javax.swing.JComponent;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.JavaType;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("any")
public class Any extends Component {
	
	@XmlAttribute("class")
	JavaType $class;
	
	
	public JavaType getJavaType() {
		return $class;
	}

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		try {
			$component = (JComponent) $class.getJavaType().newInstance();
		} catch (Exception $exc) {
			$exc.printStackTrace(System.err);
		}
		
		super.create($main, $parent);
	}
}