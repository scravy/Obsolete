package net.abusingjava.swing.magic;

import javax.swing.JSplitPane;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("hsplit")
public class HSplit extends Component {

	@XmlChildElements({HBox.class, VBox.class, FixedBox.class})
	Container[] $container;
	
	@XmlAttribute
	boolean $continuous = true;
	
	@XmlAttribute("divider-location")
	int $divideAt = 0;
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		$container[0].create($main, $parent);
		$container[1].create($main, $parent);
		
		final JSplitPane $c = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				$continuous,
				new MagicPanel($main, $container[0]),
				new MagicPanel($main, $container[1]));
		
		$c.setDividerLocation($divideAt);
		
		$component = $c;
		
		super.create($main, $parent);
	}
	
}