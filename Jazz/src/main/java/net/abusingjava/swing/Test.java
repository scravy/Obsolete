package net.abusingjava.swing;

import javax.swing.JOptionPane;

public class Test {

	public static class Handler {
		
		public void close(final TabCloseEvent $ev) {
			if (JOptionPane.showConfirmDialog(null, "Close?") > 0) {
				$ev.cancel();
			}
		}
		
	}
	
	public static void main(final String... $args) {
		AbusingSwing.setNimbusLookAndFeel();
		MagicPanel $panel = AbusingSwing.showWindow("Table.xml").getMagicPanel();
		$panel.setInvocationHandler(new Handler());
		
		for (Staat $s : Staat.values()) {
			$panel.$("#table").addRow(new Object[] {$s.name(), $s.toString(), $s.getLangform()});
		}
		for (Sprache $s : Sprache.values()) {
			$panel.$("#sluggy").add($s.toString());
		}
	}
	
}
