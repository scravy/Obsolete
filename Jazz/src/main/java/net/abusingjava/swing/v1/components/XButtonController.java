package net.abusingjava.swing.v1.components;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;
import net.abusingjava.swing.v1.XController;

/**
 * A Controller for an {@link XButton}, that is capable of creating one from an
 * {@link XButtonDefinition}.
 */
@Author("Julian Fleischer")
@Version("2011-10-20")
@Since("2011-10-20")
public class XButtonController implements XController<XButton> {

	@SuppressWarnings("unused")
	private final XButtonDefinition $definition;
	private final XButton $component;

	public XButtonController(final XButtonDefinition $xButtonDefinition) {
		$definition = $xButtonDefinition;
		$component = new XButton();
	}
	
	@Override
	public XButton getComponent() {
		return $component;
	}

	@Override
	public void reset() {
		
	}

}
