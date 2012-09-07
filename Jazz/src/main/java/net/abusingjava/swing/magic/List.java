package net.abusingjava.swing.magic;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.abusingjava.AbusingArrays;
import net.abusingjava.functions.AbusingFunctions;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magic.ComboBox.Val;
import net.abusingjava.swing.magix.types.JavaType;
import net.abusingjava.swing.magix.types.MethodType;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("list")
public class List extends Component {

	@XmlAttribute
	MethodType $onselect;

	@XmlChildElements
	Val[] $values;

	@XmlAttribute("from")
	JavaType $from;

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		 
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
		final DefaultListModel $m = new DefaultListModel();
		if ($values != null) {
			for (Object $value : $values) {
				$m.addElement($value);
			}
		}
		final JList $c = new JList($m);
		
		$c.setVisibleRowCount(-1);
		$c.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		if ($onselect != null) {
			$c.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(final ListSelectionEvent $ev) {
					if (!$ev.getValueIsAdjusting()) {
						Integer $index = null;
						if ($c.isSelectedIndex($ev.getFirstIndex())) {
							$index = $ev.getFirstIndex();
						}
						if ($c.isSelectedIndex($ev.getLastIndex())) {
							$index = $ev.getLastIndex();
						}
						if ($index != null) {
							Object $object = $m.elementAt($index);
							$onselect.call($main.getInvocationHandler(),
								new net.abusingjava.swing.ListSelectionEvent($index, $object, $c));
						}
					}
				}
			});
		}
		
		$realComponent = $c;
		$component = new JScrollPane($c);
		
		super.create($main, $parent);
	}
	
	
	
}