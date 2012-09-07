package net.abusingjava.swing.magic;

import java.awt.CardLayout;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.abusingjava.AbusingArrays;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magic.Cards.Card;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("cards")
public class Cards extends Component implements Iterable<Card> {

	@XmlElement("card")
	public static class Card extends Panel {
		
		@XmlAttribute
		String $name = "";
		
	}
	
	public static class CardComponent extends Component {

		private static final JComponent $c = new JLabel();
		
		final JPanel $parent;
		final String $name;
		
		CardComponent(final JPanel $parent, final String $name) {
			this.$parent = $parent;
			this.$name = $name;
			
			$component = $c;
		}
		
		public void goTo() {
			((CardLayout)$parent.getLayout()).show($parent, $name);
		}
	}
	
	@XmlChildElements
	Card[] $cards = new Card[] {};
	
	@XmlAttribute
	Boolean $border;
	
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($border == null) {
			$border = false;
		}
		JPanel $c = new JPanel(new CardLayout());
		
		int $i = 0;
		for (Card $card : $cards) {
			Container $con = $card.getContainer();
			$con.create($main, $parent);

			JScrollPane $p = new JScrollPane($con.getJComponent());
			if (!$border) {
				$p.setBorder(null);
			}
			
			String $name = $card.$name.isEmpty()
					? this.toString() + "-card" + $i++
					: $card.$name;
			$c.add($p, $name);

			$main.registerComponent($name, new CardComponent($c, $name));
		}

		$component = $c;

		super.create($main, $parent);
	}


	@Override
	public Iterator<Card> iterator() {
		return AbusingArrays.array($cards).iterator();
	}
}
