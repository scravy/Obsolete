package net.abusingjava.swing;

import java.awt.Dimension;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import net.abusingjava.swing.magic.MultiList.MultiListTable;

public class TestBindings {

	public static class TestBean {
		
		List<Object> $strings = new LinkedList<Object>();
		private final PropertyChangeSupport $propertyChangeSupport;
		
		TestBean(final String... $selected) {
			$propertyChangeSupport = new PropertyChangeSupport(this);
			
			$strings.addAll(Arrays.asList($selected));
		}
		
		public List<?> getStrings() {
			return $strings;
		}
		
		public void setStrings(final List<Object> $strings) {
			List<Object> $oldValue = this.$strings;
			this.$strings = $strings;
			$propertyChangeSupport.firePropertyChange("string", $oldValue, $strings);
			for (PropertyChangeListener $l : $propertyChangeSupport.getPropertyChangeListeners()) {
				$l.propertyChange(new PropertyChangeEvent(this, "strings", $oldValue, $strings));
			}
		}
		
		public void addPropertyChangeListener(final PropertyChangeListener $listener) {
			$propertyChangeSupport.addPropertyChangeListener($listener);
		}
		
		public void removePropertyChangeListener(final PropertyChangeListener $listener) {
			$propertyChangeSupport.removePropertyChangeListener($listener);
		}
		
		public PropertyChangeListener[] getPropertyChangeListeners() {
			return $propertyChangeSupport.getPropertyChangeListeners();
		}
	}
	
	public static class Frame extends JFrame {
		private static final long serialVersionUID = 2282980379533259127L;
		
		final MagicPanel $panel;
		final MultiListTable $multiList;
		final TestBean $bean = new TestBean("Flowers");
		final TestBean $bean2 = new TestBean("the gloom");
		
		@SuppressWarnings("deprecation")
		Frame() {
			$panel = AbusingSwing.makePanel("TestBindings.xml");
			$panel.setInvocationHandler(this);
			
			setContentPane($panel);
			
			$multiList = $panel.$("#multi").as(MultiListTable.class);
			$multiList.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent $ev) {
					if ($ev.getPropertyName().equals("selectedObjects")) {
						System.out.printf("%s: %s\n", $ev.getPropertyName(), $ev.getNewValue());
					}
				}
			});
			
			$panel.$("#multi").add(new String[] {"Flowers", "maybe Daisies", "the gloom", "hush love hush"});
			
			$panel.bind("testBinding", $bean);
			/*@SuppressWarnings("rawtypes")
			Binding $b = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
					$bean, BeanProperty.create("strings"),
					$multiList, BeanProperty.create("selectedObjects"));
			$b.bind();*/
			
			$bean.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent $ev) {
					System.out.printf("%s changed: %s -> %s\n", $ev.getPropertyName(), $ev.getOldValue(), $ev.getNewValue());
				}
			});
			
			setMinimumSize(new Dimension(150, 500));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		public void action1() {
			System.out.println("bean: " + $bean.getStrings());
		}
		
		@SuppressWarnings("unchecked")
		public void action2() {
			$bean.setStrings((List<Object>) (List<?>) Arrays.asList("Flowers", "the gloom"));
		}
		
		public void action3() {
			$multiList.setSelectedObjects(Arrays.asList("Flowers"));
		}
		
		@SuppressWarnings("deprecation")
		public void action4() {
			$panel.bind("testBinding", $bean2);
		}
	}
	
	public static void main(final String... $args) throws Exception {
		AbusingSwing.setNimbusLookAndFeel();
		new Frame().setVisible(true);
		
		BeanInfo $info = Introspector.getBeanInfo(TestBean.class);
		for (PropertyDescriptor $d : $info.getPropertyDescriptors()) {
			System.out.printf("%s %s %s %s\n",
					$d.getName(), $d.getDisplayName(), $d.getReadMethod(), $d.getWriteMethod());
		}
	}
	
}
