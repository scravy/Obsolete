package net.abusingjava.swing.magic;

import java.util.Iterator;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import net.abusingjava.AbusingArrays;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magic.Panes.Pane;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("panes")
public class Panes extends Component implements Iterable<Pane> {

	@XmlElement("pane")
	public static class Pane extends Panel {

		@XmlAttribute
		String $title = "";

		@XmlAttribute
		Boolean $expanded;

		@XmlAttribute
		Boolean $animated;

		public String getTitle() {
			return $title;
		}

		public boolean getExpanded() {
			return $expanded == null ? false : $expanded;
		}

		public boolean getAnimated() {
			return $animated == null ? true : $animated;
		}
	}

	@XmlAttribute
	Boolean $border;
	
	@XmlChildElements
	Pane[] $panes = new Pane[] {};

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($border == null) {
			$border = false;
		}

		final JXTaskPaneContainer $c = new JXTaskPaneContainer();

		for (Pane $p : $panes) {
			JXTaskPane $jxp = new JXTaskPane();
			Container $con = $p.getContainer();
			$con.create($main, $parent);
			
			$jxp.add($con.getRealComponent());
			$jxp.setTitle($p.getTitle());
			$jxp.setAnimated($p.getAnimated());
			$jxp.setCollapsed(!$p.getExpanded());
			
			$c.add($jxp);
		}

		$realComponent = $c;
		$component = new JScrollPane($c);

		if (!$border) {
			$component.setBorder(null);
		}
		
		super.create($main, $parent);
	}

	@Override
	public Iterator<Pane> iterator() {
		return AbusingArrays.array($panes).iterator();
	}

}