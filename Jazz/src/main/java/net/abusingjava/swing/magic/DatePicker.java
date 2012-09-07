package net.abusingjava.swing.magic;

import java.awt.Insets;
import java.util.Locale;

import javax.swing.JButton;

import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlElement;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;

@XmlElement("datepicker")
public class DatePicker extends Component {
	
	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {
		if ($height == null) {
			$height = new Value("27px");
		}
		
		JXDatePicker $c = new JXDatePicker();

		JButton $button = (JButton) $c.getComponent(1);
		$button.setMargin(new Insets(0, -5, 0, -5));
		
		$c.setLocale(Locale.GERMAN);
		JXMonthView $m = $c.getMonthView();
		
		$m.setShowingWeekNumber(true);
		
		$component = $c;
		
		super.create($main, $parent);
	}
	
}