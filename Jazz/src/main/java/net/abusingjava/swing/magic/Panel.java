package net.abusingjava.swing.magic;

import net.abusingjava.swing.magic.Style.Rule;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;

@XmlElement("panel")
public class Panel {

	@XmlChildElements({HBox.class, VBox.class, FixedBox.class})
	Container[] $containers = new Container[] {};
	
	@XmlChildElements
	BindingDefinition[] $bindings = new BindingDefinition[] {};
	
	@XmlChildElements
	Style[] $style = new Style[] {};
	
	@XmlChildElements
	Menu[] $menus = new Menu[] {};
	
	public Container getContainer() {
		return $containers[0];
	}
	
	public Menu[] getMenus() {
		return $menus;
	}
	
	public BindingDefinition getBinding(final String $name) {
		for (BindingDefinition $b : $bindings) {
			if ($b.getName().equals($name)) {
				return $b;
			}
		}
		return null;
	}
	
	public Rule[] getStyleRules() {
		if ($style.length > 0) {
			return $style[0].$rules;
		}
		return new Rule[0];
	}
}
