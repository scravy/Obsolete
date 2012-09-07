package net.abusingjava.swing.magix.types;

public class FontWeight {
	final boolean $bold;
	
	public FontWeight(final String $weight) {
		$bold = "bold".equalsIgnoreCase($weight);
	}

	public boolean isBold() {
		return $bold;
	}
	
	@Override
	public String toString() {
		return $bold ? "bold" : "normal";
	}
}