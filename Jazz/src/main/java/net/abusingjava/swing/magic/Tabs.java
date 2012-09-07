package net.abusingjava.swing.magic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

import net.abusingjava.AbusingArrays;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.TabCloseEvent;
import net.abusingjava.swing.magic.Tabs.Tab;
import net.abusingjava.swing.magix.types.MethodType;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("tabs")
public class Tabs extends Component implements Iterable<Tab> {

	@XmlElement("tab")
	public static class Tab extends Panel {

		@XmlAttribute
		String $title = "";

		@XmlAttribute
		Boolean $closeable = false;

		@XmlAttribute
		MethodType $onclose;

		@XmlAttribute
		MethodType $onselect;

		public String getTitle() {
			return $title;
		}

		public boolean getCloseable() {
			return $closeable == null ? false : $closeable;
		}
	}

	@XmlChildElements
	Tab[] $tabs = new Tab[]{};

	@Override
	public void create(final MagicPanel $main, final MagicPanel $parent) {

		final JTabbedPane $c = new JTabbedPane();

		for (final Tab $t : $tabs) {
			Container $con = $t.getContainer();
			$con.create($main, $parent);

			final JScrollPane $scrollPane = new JScrollPane($con.getRealComponent());
			$c.add($scrollPane, $t.getTitle());

			if ($t.getCloseable()) {
				@SuppressWarnings("serial")
				final JButton $closeButton = new JButton("x") {
					{
						setPreferredSize(new Dimension(15, 15));
						setUI(new BasicButtonUI());
						setContentAreaFilled(false);
						setFocusable(false);
						setBorder(BorderFactory.createLineBorder(Color.BLACK));
						setBorderPainted(false);
						addMouseListener(new MouseAdapter() {
							@Override
							public void mouseEntered(final MouseEvent e) {
								java.awt.Component component = e.getComponent();
								AbstractButton button = (AbstractButton) component;
								button.setBorderPainted(true);
							}

							@Override
							public void mouseExited(final MouseEvent e) {
								java.awt.Component component = e.getComponent();
								AbstractButton button = (AbstractButton) component;
								button.setBorderPainted(false);
							}
						});
						setRolloverEnabled(true);
					}
				};
				@SuppressWarnings("serial")
				JPanel $titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
					{
						setOpaque(false);
						add(new JLabel($t.getTitle()) {
							{
								setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
							}
						});
						add($closeButton);
						setSize(new Dimension(getWidth(), 20));
						setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

						$closeButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(final ActionEvent $ev) {
								if ($t.$onclose != null) {
									final TabCloseEvent $e = new TabCloseEvent($c);
									new Thread(new Runnable() {
										@Override
										public void run() {
											$t.$onclose.call($main.getInvocationHandler(), $e);
										}
									}).start();
									if (!$e.isCanceled()) {
										$c.remove($scrollPane);
									}
								} else {
									$c.remove($scrollPane);
								}
							}
						});
					}
				};
				$c.setTabComponentAt(
						$c.indexOfComponent($scrollPane),
						$titlePanel);
			}
		}

		$c.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent $ev) {
				final int $i = $c.getSelectedIndex();
				if ($tabs[$i].$onselect != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							$tabs[$i].$onselect.call($main.getInvocationHandler());
						}
					}).start();
				}
			}
		});

		$component = $c;

		super.create($main, $parent);
	}

	@Override
	public Iterator<Tab> iterator() {
		return AbusingArrays.array($tabs).iterator();
	}

}