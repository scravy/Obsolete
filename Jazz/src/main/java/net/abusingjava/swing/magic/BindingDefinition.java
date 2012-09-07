package net.abusingjava.swing.magic;

import java.util.ArrayList;
import java.util.Iterator;

import net.abusingjava.AbusingArrays;
import net.abusingjava.swing.magic.BindingDefinition.Property;
import net.abusingjava.xml.XmlAttribute;
import net.abusingjava.xml.XmlChildElements;
import net.abusingjava.xml.XmlElement;
import net.abusingjava.xml.XmlTextContent;

import org.jdesktop.beansbinding.Binding;

@XmlElement("binding")
public class BindingDefinition implements Iterable<Property> {
	
	@XmlAttribute
	String $name;
	
	@XmlAttribute("table")
	String $tableName;
	
	@XmlChildElements
	Property $properties[];
	
	@XmlElement("property")
	public static class Property {
		@XmlAttribute
		String $name;
		
		@XmlTextContent
		String $target;
		
		public String getName() {
			return $name;
		}
		
		public String getTarget() {
			return $target;
		}
	}
	
	@SuppressWarnings("rawtypes")
	final java.util.List<Binding> $bindings = new ArrayList<Binding>();
	
	@SuppressWarnings("rawtypes")
	public void addBinding(final Binding $binding) {
		$bindings.add($binding);
	}
	
	@SuppressWarnings("rawtypes")
	public Binding[] getBindings() {
		return $bindings.toArray(new Binding[$bindings.size()]);
	}
	
	@SuppressWarnings("rawtypes")
	public void removeBinding(final Binding $binding) {
		$bindings.remove($binding);
	}
	
	public String getName() {
		if ($name.charAt(0) == '#') {
			return $name.substring(1);
		}
		return $name;
	}
	
	public boolean isTableBinding() {
		return ($tableName != null) && !$tableName.isEmpty();
	}
	
	public String getTableName() {
		return $tableName;
	}

	@Override
	public Iterator<Property> iterator() {
		return AbusingArrays.array($properties).iterator();
	}
}