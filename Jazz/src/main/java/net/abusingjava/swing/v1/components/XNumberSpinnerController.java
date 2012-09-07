package net.abusingjava.swing.v1.components;

import net.abusingjava.swing.v1.XController;


public class XNumberSpinnerController implements XController<XNumberSpinner> {

	@SuppressWarnings("unused")
	private final XNumberSpinnerDefinition $definition;
	private final XNumberSpinner $component;
	
	public XNumberSpinnerController(final XNumberSpinnerDefinition $xNumberSpinnerDefinition) {
		$definition = $xNumberSpinnerDefinition;
		$component = new XNumberSpinner();
	}
	
	@Override
	public XNumberSpinner getComponent() {
		return $component;
	}

	@Override
	public void reset() {
		
	}

}
