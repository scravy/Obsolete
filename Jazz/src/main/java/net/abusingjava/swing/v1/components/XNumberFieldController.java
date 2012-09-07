package net.abusingjava.swing.v1.components;

import net.abusingjava.swing.v1.XController;


public class XNumberFieldController implements XController<XNumberField> {

	private final XNumberField $component;
	@SuppressWarnings("unused")
	private final XNumberFieldDefinition $definition;
	
	public XNumberFieldController(final XNumberFieldDefinition $xNumberFieldDefinition) {
		$definition = $xNumberFieldDefinition;
		$component = new XNumberField();
	}

	@Override
	public XNumberField getComponent() {
		return $component;
	}

	@Override
	public void reset() {
		
	}
	
}
