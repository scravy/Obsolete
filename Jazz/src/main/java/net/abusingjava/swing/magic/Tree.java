package net.abusingjava.swing.magic;

import org.jdesktop.swingx.JXTree;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.xml.XmlElement;

@XmlElement("tree")
public class Tree extends Component {

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		JXTree $c = new JXTree();
		
		$component = $c;
		
		super.create($main, $parent);
	}
	
	
	
}
