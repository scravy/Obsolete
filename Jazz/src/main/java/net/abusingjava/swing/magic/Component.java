package net.abusingjava.swing.magic;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import net.abusingjava.AbusingReflection;
import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;
import net.abusingjava.swing.MagicPanel;
import net.abusingjava.swing.magix.types.ClassNames;
import net.abusingjava.swing.magix.types.Color;
import net.abusingjava.swing.magix.types.FontStyle;
import net.abusingjava.swing.magix.types.FontWeight;
import net.abusingjava.swing.magix.types.MethodType;
import net.abusingjava.swing.magix.types.Unit;
import net.abusingjava.swing.magix.types.Value;
import net.abusingjava.xml.XmlAttribute;
import net.java.balloontip.BalloonTip;

@Author("Julian Fleischer")
@Version("2011-12-20")
@Since(value = "2011-08-21", version = "1.0")
abstract public class Component {

	@XmlAttribute
	Value $width;

	@XmlAttribute
	Value $height;

	@XmlAttribute
	Value $minWidth = new Value("0px");

	@XmlAttribute
	Value $minHeight = new Value("0px");

	@XmlAttribute("left")
	Value $posX = new Value("0px");

	@XmlAttribute("top")
	Value $posY = new Value("0px");

	@XmlAttribute
	Color $foreground;

	@XmlAttribute
	Color $background;

	@XmlAttribute
	Boolean $enabled; // true

	@XmlAttribute
	Boolean $visible; // true

	@XmlAttribute
	MethodType $onaction;

	@XmlAttribute
	MethodType $onclick;

	@XmlAttribute
	MethodType $onblur;

	@XmlAttribute
	MethodType $onfocus;

	@XmlAttribute
	MethodType $onmousedown;

	@XmlAttribute
	MethodType $onmouseup;

	@XmlAttribute
	MethodType $onmouseover;

	@XmlAttribute
	MethodType $onmouseout;

	@XmlAttribute
	MethodType $onmousemove;

	@XmlAttribute
	MethodType $onmousewheel;

	@XmlAttribute
	MethodType $onkeydown;

	@XmlAttribute
	MethodType $onkeypress;

	@XmlAttribute
	MethodType $onkeyup;

	@XmlAttribute("tool-tip")
	String $toolTip = "";

	@XmlAttribute("font-weight")
	FontWeight $fontWeight;

	@XmlAttribute("font-style")
	FontStyle $fontStyle;

	@XmlAttribute("font-family")
	String $fontFamily;

	@XmlAttribute("font-size")
	Value $fontSize;

	@XmlAttribute
	Boolean $opaque;

	@XmlAttribute
	String $cursor;

	@XmlAttribute
	ClassNames $class;

	@XmlAttribute
	String $name;

	@XmlAttribute("popup-menu")
	String $popupMenu;

	protected JComponent $component;

	protected JComponent $realComponent = null;

	protected BalloonTip $balloonTip = null;

	protected MagicPanel $mainPanel;

	boolean $update = false;

	public BalloonTip getBalloonTip() {
		return $balloonTip;
	}

	public void setBalloonTip(final BalloonTip $tip) {
		$balloonTip = $tip;
	}

	public JComponent getJComponent() {
		return $component;
	}

	public JComponent getRealComponent() {
		if ($realComponent == null) {
			return $component;
		}
		return $realComponent;
	}

	public String getName() {
		return $name;
	}

	public ClassNames getClasses() {
		return $class;
	}

	public boolean hasClass(final String $className) {
		if (($class == null) || ($className == null)) {
			return false;
		}
		return $class.contains($className);
	}

	public Value getWidth() {
		return $width;
	}

	public Value getHeight() {
		return $height;
	}

	public Value getPosX() {
		return $posX;
	}

	public Value getPosY() {
		return $posY;
	}

	public Runnable eventListener(final MethodType $method, final Object... $args) {
		final String $specialMethodArg = $method.getSpecialMethodArg();
		if ($method.isSpecialMethod()) {
			switch ($method.getSpecialMethodType()) {
			case GOTO:
				return new Runnable() {
					@Override
					public void run() {
						$mainPanel.$($specialMethodArg).goTo();
					}
				};
			case HIDE:
				return new Runnable() {
					@Override
					public void run() {
						$mainPanel.$($specialMethodArg).hide();
					}
				};
			case SHOW:
				return new Runnable() {
					@Override
					public void run() {
						$mainPanel.$($specialMethodArg).show();
					}
				};
			case ENABLE:
				return new Runnable() {
					@Override
					public void run() {
						$mainPanel.$($specialMethodArg).enable();
					}
				};
			case DISABLE:
				return new Runnable() {
					@Override
					public void run() {
						$mainPanel.$($specialMethodArg).disable();
					}
				};
			}
		}
		return new Runnable() {
			@Override
			public void run() {
				$method.call($mainPanel.getInvocationHandler(), $args);
			}
		};
	}

	public void create(final MagicPanel $main,
			@SuppressWarnings("unused") final MagicPanel $parent) {
		$mainPanel = $main;

		if ($enabled == null) {
			$enabled = true;
		}
		if ($visible == null) {
			$visible = true;
		}
		if ($realComponent == null) {
			$realComponent = $component;
		}

		if (($cursor != null) && $cursor.equalsIgnoreCase("hand")) {
			$component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		$main.registerComponent($name, this);

		$component.setEnabled($enabled);
		$component.setVisible($visible);
		if (!$toolTip.isEmpty()) {
			$component.setToolTipText($toolTip);
		}
		if ($foreground != null) {
			$component.setForeground($foreground.getColor());
		}
		if ($background != null) {
			$component.setBackground($background.getColor());
		}
		if ($width == null) {
			$width = new Value();
		}
		if ($height == null) {
			$height = new Value();
		}
		if ($opaque != null) {
			$component.setOpaque($opaque);
		}
		if (($fontSize != null) && ($fontSize.getUnit() == Unit.POINT)) {
			$realComponent.setFont($realComponent.getFont().deriveFont((float) $fontSize.getValue()));
		}
		if (($fontWeight != null) && $fontWeight.isBold()) {
			$realComponent.setFont($realComponent.getFont().deriveFont(Font.BOLD));
		}
		if (($fontStyle != null) && $fontStyle.isItalic()) {
			$realComponent.setFont($realComponent.getFont().deriveFont(Font.ITALIC));
		}
		if ($fontFamily != null) {
			$realComponent.setFont(Font.decode($fontFamily));
		}

		if ($popupMenu != null) {
			final JPopupMenu $menu = $main.getPopupMenu($popupMenu);
			if ($menu != null) {
				$realComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(final MouseEvent $ev) {
						if ($ev.isPopupTrigger()) {
							doPop($ev);
						}
					}

					@Override
					public void mouseReleased(final MouseEvent $ev) {
						if ($ev.isPopupTrigger()) {
							doPop($ev);
						}
					}

					private void doPop(final MouseEvent $ev) {
						$menu.show($realComponent, $ev.getX(), $ev.getY());
					}
				});
			}
		}

		if ($onaction != null) {
			ActionListener $listener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent $ev) {
					if (!$update) {
						new Thread(eventListener($onaction, $ev)).start();
					}
				}
			};
			if (AbusingReflection.hasMethod($realComponent, "addActionListener")) {
				try {
					$realComponent.getClass().getMethod("addActionListener", ActionListener.class)
							.invoke($realComponent, $listener);
				} catch (Exception $exc) {
					System.err.println($exc);
				}
			}
		}

		if ($onclick != null) {
			MouseAdapter $listener = new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent $ev) {
					new Thread(eventListener($onclick, $ev)).start();
				}
			};
			$realComponent.addMouseListener($listener);
		}

		if ($onmouseover != null) {
			MouseAdapter $listener = new MouseAdapter() {
				@Override
				public void mouseEntered(final MouseEvent $ev) {
					new Thread(eventListener($onmouseover, $ev)).start();
				}
			};
			$realComponent.addMouseListener($listener);
		}

		if ($onmouseout != null) {
			MouseAdapter $listener = new MouseAdapter() {
				@Override
				public void mouseExited(final MouseEvent $ev) {
					new Thread(eventListener($onmouseout, $ev)).start();
				}
			};
			$realComponent.addMouseListener($listener);
		}

		if ($onmousemove != null) {
			MouseAdapter $listener = new MouseAdapter() {
				@Override
				public void mouseMoved(final MouseEvent $ev) {
					new Thread(eventListener($onmousemove, $ev)).start();
				}
			};
			$realComponent.addMouseListener($listener);
		}

		if ($onmousedown != null) {
			MouseAdapter $listener = new MouseAdapter() {
				@Override
				public void mousePressed(final MouseEvent $ev) {
					new Thread(eventListener($onmousedown, $ev)).start();
				}
			};
			$realComponent.addMouseListener($listener);
		}

		if ($onmouseup != null) {
			MouseAdapter $listener = new MouseAdapter() {
				@Override
				public void mouseReleased(final MouseEvent $ev) {
					new Thread(eventListener($onmouseup, $ev)).start();
				}
			};
			$realComponent.addMouseListener($listener);
		}

		if ($onmousewheel != null) {
			MouseWheelListener $listener = new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(final MouseWheelEvent $ev) {
					new Thread(eventListener($onmousewheel, $ev)).start();
				}
			};
			$realComponent.addMouseWheelListener($listener);
		}

		if ($onkeydown != null) {
			KeyAdapter $listener = new KeyAdapter() {
				@Override
				public void keyPressed(final KeyEvent $ev) {
					new Thread(eventListener($onkeydown, $ev)).start();
				}
			};
			$realComponent.addKeyListener($listener);
		}

		if ($onkeyup != null) {
			KeyAdapter $listener = new KeyAdapter() {
				@Override
				public void keyReleased(final KeyEvent $ev) {
					new Thread(eventListener($onkeyup, $ev)).start();
				}
			};
			$realComponent.addKeyListener($listener);
		}

		if ($onkeypress != null) {
			KeyAdapter $listener = new KeyAdapter() {
				@Override
				public void keyTyped(final KeyEvent $ev) {
					new Thread(eventListener($onkeypress, $ev)).start();
				}
			};
			$realComponent.addKeyListener($listener);
		}

		if ($onfocus != null) {
			FocusAdapter $listener = new FocusAdapter() {
				@Override
				public void focusGained(final FocusEvent $ev) {
					new Thread(eventListener($onfocus, $ev)).start();
				}
			};
			$realComponent.addFocusListener($listener);
		}

		if ($onblur != null) {
			FocusAdapter $listener = new FocusAdapter() {
				@Override
				public void focusLost(final FocusEvent $ev) {
					new Thread(eventListener($onblur, $ev)).start();
				}
			};
			$realComponent.addFocusListener($listener);
		}
	}

	public void setUpdate(final boolean $updateMode) {
		$update = $updateMode;
	}
}