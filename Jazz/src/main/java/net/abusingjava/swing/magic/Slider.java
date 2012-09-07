package net.abusingjava.swing.magic;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("slider")
public class Slider extends Component {


	@XmlAttribute
	Integer $min;
	
	@XmlAttribute
	Integer $max;
	
	@XmlAttribute
	Integer $value;
	
	@XmlAttribute
	Integer $extent;
	
	@XmlAttribute("minor-tick-spacing")
	Integer $minorTickSpacing;
	
	@XmlAttribute("major-tick-spacing")
	Integer $majorTickSpacing;
	
	@XmlAttribute("paint-labels")
	Boolean $paintLabels;
	
	@XmlAttribute("paint-ticks")
	Boolean $paintTicks;
	
	@XmlAttribute("paint-track")
	Boolean $paintTrack;
	
	@XmlAttribute("snap-to-tick")
	Boolean $snapToTick;
	

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($min == null) {
			$min = 0;
		}
		if ($max == null) {
			$max = 100;
		}
		if ($value == null) {
			$value = ($min + $max) / 2;
		}

		final JSlider $c = new JSlider($min, $max, $value);
		
		switch ($parent.getOrientation()) {
		case HORIZONTAL:
			if ($width == null) {
				$width = new Value("17px");
			}
			$c.setOrientation(SwingConstants.VERTICAL);
			break;
		case VERTICAL:
			if ($height == null) {
				$height = new Value("17px");
			}
			$c.setOrientation(SwingConstants.HORIZONTAL);
			break;
		case FIXED:
			break;
		}
		
		$component = $c;
		super.create($main, $parent);
		
	}
	
	
	
}