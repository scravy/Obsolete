package net.abusingjava.swing.magix.types;

public class FontStyle {
	final boolean $italic;
	
	public FontStyle(final String $style) {
		$italic = "italic".equalsIgnoreCase($style);
	}

	public boolean isItalic() {
		return $italic;
	}
}