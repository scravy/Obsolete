package net.abusingjava.swing.v1;

import java.io.InputStream;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.swing.v1.components.XPanelDefinition;
import net.abusingjava.xml.AbusingXML;

@Author("Julian Fleischer")
@Since("2011-10-15")
public class MagicPanel {

	@SuppressWarnings("unused")
	final private XPanelDefinition $definition;
	
	MagicPanel(final InputStream $stream) {
		$definition = AbusingXML.loadXML($stream, XPanelDefinition.class);
	}
}
