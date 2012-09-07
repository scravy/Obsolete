package net.abusingjava.swing.magic;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlElement;

@XmlElement("progressbar")
public class ProgressBar extends Component {

	@XmlAttribute
	Integer $min;
	
	@XmlAttribute
	Integer $max;
	
	@XmlAttribute
	Integer $value;
	
	@XmlAttribute
	Boolean $indeterminate;
	
	@XmlAttribute("string-painted")
	Boolean $stringPainted;
	
	@XmlAttribute
	String $string = "%s / %s";
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($min == null) {
			$min = 0;
		}
		if ($max == null) {
			$max = 100;
		}
		if ($value == null) {
			$value = $min;
		}
		if ($indeterminate == null) {
			$indeterminate = false;
		}
		if ($stringPainted == null) {
			$stringPainted = false;
		}
		
		final JProgressBar $c = new JProgressBar($min, $max);
		
		$c.setIndeterminate($indeterminate);
		$c.setStringPainted($stringPainted);
		
		$component = $c;
		
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
		
		$c.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent $ev) {
				$c.setString(String.format($string, $c.getValue(), $c.getMaximum(), $c.getMinimum()));
			}
		});
		
		$c.setValue($value);
		$c.setValue($value + 1);
		
		super.create($main, $parent);
	}
	
}