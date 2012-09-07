package net.abusingjava.swing;

import java.awt.Color;

/**
 * Color names as identified by <a
 * href="http://blog.xkcd.com/2010/05/03/color-survey-results/">the XKCD color
 * survey</a>.
 */
public enum Colors {

	PURPLE(0x7e1e9c),

	GREEN(0x15b01a),

	BLUE(0x0343df),

	PINK(0xff81c0),

	BROWN(0x653700),

	RED(0xe50000),

	LIGHT_BLUE(0x95d0fc),

	TEAL(0x029386),

	ORANGE(0xf97306),

	LIGHT_GREEN(0x96f97b),

	MAGENTA(0xc20078),

	YELLOW(0xffff14),

	SKY_BLUE(0x75bbfd),

	GREY(0x929591),

	LIME_GREEN(0x89fe05),

	LIGHT_PURPLE(0xbf7716),

	VIOLET(0x9a0eea),

	DARK_GREEN(0x033500),

	TURQUOISE(0x06c2ac),

	LAVENDER(0xc79fef),

	DARK_BLUE(0x00035b),

	TAN(0xd1b26f),

	CYAN(0x00ffff),

	AQUA(0x13eac9),

	FOREST_GREEN(0x06470c),

	MAUVE(0xae7181),

	DARK_PURPLE(0x35063e),

	BRIGHT_GREEN(0x01ff07),

	MAROON(0x650021),

	OLIVE(0x6e750e),

	SALMON(0xff796c),

	BEIGE(0xe6daa6),

	ROYAL_BLUE(0x0504aa),

	NAVY_BLUE(0x001146),

	LILAC(0xcea2fd),

	BLACK(0x000000),

	HOT_PINK(0xff028d),

	LIGHT_BROWN(0xad8150),

	PALE_GREEN(0xc7fdb5),

	PEACH(0xfb07c),

	OLIVE_GREEN(0x677a04),

	DARK_PINK(0xcb416b),

	PERIWINKLE(0x8e82fe),

	SEA_GREEN(0x53fca1),

	LIME(0xaaff32),

	INDIGO(0x380282),

	MUSTARD(0xceb301),

	LIGHT_PINK(0xffd1df),

	;

	final int $rgb;
	
	Colors(final int $rgb) {
		this.$rgb = $rgb;
	}

	public Color getColor() {
		return new Color($rgb);
	}
}
