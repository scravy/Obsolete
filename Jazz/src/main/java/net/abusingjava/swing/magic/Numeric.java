package net.abusingjava.swing.magic;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlTextContent;

@XmlElement("numeric")
public class Numeric extends Component {
	
	@XmlAttribute
	Double $min;
	
	@XmlAttribute
	Double $max;
	
	@XmlAttribute
	Double $step;
	
	@XmlTextContent
	Double $value;

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($min == null) {
			$min = 0.0;
		}
		if ($max == null) {
			$max = 100.0;
		}
		if ($step == null) {
			$step = 1.0;
		}
		if ($value == null) {
			$value = $min;
		}
		if ($height == null) {
			$height = new Value("27px");
		}

		final SpinnerNumberModel $m = new SpinnerNumberModel($value, $min, $max, $step);
		final JSpinner $c = new JSpinner($m);
		
		$c.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(final MouseWheelEvent $ev) {
				try {
					if ($ev.getWheelRotation() > 0) {
						$m.setValue($m.getPreviousValue());
					} else if ($ev.getWheelRotation() < 0) {
						$m.setValue($m.getNextValue());
					}
				} catch (Exception $exc) {
					
				}
			}
		});
		
		$component = $c;
		
		super.create($main, $parent);
	}
	
}
