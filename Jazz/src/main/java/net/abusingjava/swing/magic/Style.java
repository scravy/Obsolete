package net.abusingjava.swing.magic;

import net.abusingjava.NotGonnaHappenException;
import net.abusingjava.swing.magic.Password.EchoChar;
import net.abusingjava.swing.magix.types.Color;
import net.abusingjava.swing.magix.types.FilterMode;
import net.abusingjava.swing.magix.types.FontStyle;
import net.abusingjava.swing.magix.types.FontWeight;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("style")
public class Style {

	@XmlElement("rule")
	public static class Rule {


		@XmlAttribute
		String $match = "";
		
		@XmlAttribute
		Value $insets;

		@XmlAttribute
		Boolean $animated;

		@XmlAttribute("auto-complete")
		Boolean $autoComplete;

		@XmlAttribute
		Color $background;

		@XmlAttribute
		Boolean $border;

		@XmlAttribute
		Boolean $closeable;

		@XmlAttribute("column-control-visible")
		Boolean $columnControlVisible;

		@XmlAttribute("column-margin")
		Integer $columnMargin;

		@XmlAttribute("default-text")
		String $defaultText;

		@XmlAttribute("echo-char")
		EchoChar $echoChar;

		@XmlAttribute
		Boolean $editable;

		@XmlAttribute
		Boolean $enabled;

		@XmlAttribute
		Boolean $expanded;

		@XmlAttribute("filter-each-word")
		Boolean $filterEachWord;

		@XmlAttribute("filter-mode")
		FilterMode $filterMode;

		@XmlAttribute("font-size")
		Value $fontSize;

		@XmlAttribute("font-style")
		FontStyle $fontStyle;

		@XmlAttribute("font-weight")
		FontWeight $fontWeight;

		@XmlAttribute
		Color $foreground;

		@XmlAttribute("grid-color")
		Color $gridColor;

		@XmlAttribute
		Value $height;

		@XmlAttribute("horizontal-scroll-enabled")
		Boolean $horizontalScrollEnabled;

		@XmlAttribute
		Boolean $indeterminate;

		@XmlAttribute("line-wrap")
		Boolean $lineWrap;

		@XmlAttribute("max")
		Integer $max;

		@XmlAttribute("min")
		Integer $min;

		@XmlAttribute("min-height")
		Value $minHeight;

		@XmlAttribute("min-width")
		Value $minWidth;

		@XmlAttribute
		Value $padding;

		@XmlAttribute("padding-bottom")
		Value $paddingBottom;

		@XmlAttribute("padding-left")
		Value $paddingLeft;

		@XmlAttribute("padding-right")
		Value $paddingRight;

		@XmlAttribute("padding-top")
		Value $paddingTop;

		@XmlAttribute("padding-x")
		Value $paddingX;

		@XmlAttribute("padding-y")
		Value $paddingY;

		@XmlAttribute("left")
		Value $posX;

		@XmlAttribute("top")
		Value $posY;

		@XmlAttribute("row-height")
		Value $rowHeight;

		@XmlAttribute("scrollable")
		Boolean $scrollable;

		@XmlAttribute
		Boolean $selected;

		@XmlAttribute("select-on-focus")
		Boolean $selectOnFocus;

		@XmlAttribute
		Boolean $sortable;

		@XmlAttribute("sorts-on-update")
		Boolean $sortsOnUpdate;

		@XmlAttribute
		Double $step;

		@XmlAttribute
		String $string;

		@XmlAttribute("string-painted")
		Boolean $stringPainted;

		@XmlAttribute("tab-size")
		Integer $tabSize;

		@XmlAttribute("terminate-edit-on-focus-lost")
		Boolean $terminateEditOnFocusLost;

		@XmlAttribute("text-align")
		String $textAlign;

		@XmlAttribute("value")
		Integer $value;

		@XmlAttribute("vertical-align")
		String $verticalAlignment;

		@XmlAttribute
		Boolean $visible;

		@XmlAttribute
		Value $width;

		@XmlAttribute("wrap-style-word")
		Boolean $wrapStyleWord;

		public boolean matches(final Object $o) {
			try {
				String[] $selectors = $match.split(", +");
				for (String $selector : $selectors) {
					if ($o.getClass().isAnnotationPresent(XmlElement.class)) {
						if ($o.getClass().getAnnotation(XmlElement.class).value().equals($selector)) {
							return true;
						}
					}
					if ($o instanceof Component) {
						if (($selector.charAt(0) == '#')) {
							if ($selector.substring(1).equals(((Component)$o).getName())) {
								return true;
							}
						} else if (($selector.charAt(0) == '.')) {
							if (((Component)$o).hasClass($selector.substring(1))) {
								return true;
							}
						}
					}
				}
			} catch (Exception $exc) {
				$exc.printStackTrace(System.err);
			}
			return false;
		}
		
		public Object get(final String $field) {
			
			try {
				return Rule.class.getDeclaredField($field).get(this);
			} catch (NoSuchFieldException $exc) {
				return null;
			} catch (IllegalAccessException $exc) {
				throw new NotGonnaHappenException($exc);
			}
		}
	}

	@XmlChildElements()
	Rule $rules[] = new Rule[] {};

}