package net.abusingjava.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;
import net.abusingjava.swing.magix.types.Unit;

@Author("Julian Fleischer")
@Version("2011-09-06")
@Since(value = "2011-08-21", version = "1.0")
public class MagicLayoutManager implements LayoutManager {

	private final MagicPanel $parent;
	private final MagicPanel $panel;
	private final net.abusingjava.swing.magic.Container $container;
	
	MagicLayoutManager(final MagicPanel $parent, final MagicPanel $panel, final net.abusingjava.swing.magic.Container $container) {
		this.$parent = $parent;
		this.$panel = $panel;
		this.$container = $container;
	}

	@Override
	public void addLayoutComponent(final String $s, final Component $c) {
		
	}

	@Override
	public void removeLayoutComponent(final Component $c) {
		
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void layoutContainer(final Container $container) {
		if ($panel == $container) {
			net.abusingjava.swing.magic.Container $def = $panel.getDefinition();
			
			double $width = $container.getWidth();
			double $height = $container.getHeight();
			
			int $starsWidth = 0;
			int $starsHeight = 0;

			double $posX = 0;
			double $posY = 0;

			double $remainingWidth = $width;
			double $remainingHeight = $height;
			
			if ($def.getPaddingLeft().getUnit() == Unit.PIXEL) {
				$posX = $def.getPaddingLeft().getValue();
				$remainingWidth -= $posX;
			}
			if ($def.getPaddingTop().getUnit() == Unit.PIXEL) {
				$posY = $def.getPaddingTop().getValue();
				$remainingHeight -= $posY;
			}
			if ($def.getPaddingRight().getUnit() == Unit.PIXEL) {
				$remainingWidth -= $def.getPaddingRight().getValue();
			}
			if ($def.getPaddingBottom().getUnit() == Unit.PIXEL) {
				$remainingHeight -= $def.getPaddingBottom().getValue();
			}
			
			double $innerWidth = $remainingWidth;
			double $innerHeight = $remainingHeight;
			
			int $newWidth = 0;
			int $newHeight = 0;
			
			int $newX = 0;
			int $newY = 0;
			
			for (net.abusingjava.swing.magic.Component $c : this.$container) {
				switch ($panel.getOrientation()) {
				case VERTICAL:
					switch ($c.getHeight().getUnit()) {
					case PIXEL:
						$remainingHeight -= $c.getHeight().getValue();
						break;
					case STAR:
						$starsHeight += $c.getHeight().getValue();
						break;
					case PERCENT:
						$remainingHeight -= Math.round(($c.getHeight().getValue() / 100.0) * $innerHeight);
						break;
					case AUTO:
						$starsHeight++;
						break;
					case INTRINSIC:
						$remainingHeight -= $c.getJComponent().getMinimumSize().height;
						break;
					}
					switch ($c.getWidth().getUnit()) {
					case STAR:
						$starsWidth = Math.max($starsWidth, $c.getWidth().getValue());
						break;
					case AUTO:
					case PIXEL:
					case PERCENT:
					case INTRINSIC:
						// nothing
						break;
					}
					break;
					
				case HORIZONTAL:
					switch ($c.getWidth().getUnit()) {
					case PIXEL:
						$remainingWidth -= $c.getWidth().getValue();
						break;
					case STAR:
						$starsWidth += $c.getWidth().getValue();
						break;
					case PERCENT:
						$remainingWidth -= Math.round(($c.getWidth().getValue() / 100.0) * $innerWidth);
						break;
					case AUTO:
						$starsWidth++;
						break;
					case INTRINSIC:
						$remainingWidth -= $c.getJComponent().getMinimumSize().width;
						break;
					}
					switch ($c.getHeight().getUnit()) {
					case STAR:
						$starsHeight = Math.max($starsHeight, $c.getHeight().getValue());
						break;
					case AUTO:
					case PIXEL:
					case PERCENT:
					case INTRINSIC:
						// nothing
						break;
					}
					break;
					
				case FIXED:
					
					break;
				}
			}
			
			for (net.abusingjava.swing.magic.Component $c : this.$container) {
				switch ($panel.getOrientation()) {
				case VERTICAL:
					$newWidth = (int) $width;
					switch ($c.getHeight().getUnit()) {
					case PIXEL:
						$newHeight = $c.getHeight().getValue();
						break;
					case STAR:
						$newHeight = (int) Math.round(($c.getHeight().getValue() / (double) $starsHeight) * $remainingHeight);
						break;
					case PERCENT:
						$newHeight = (int) Math.round(($c.getHeight().getValue() / 100.0) * $innerHeight);
						break;
					case AUTO:
						$newHeight = (int) Math.round((1 / (double) $starsHeight) * $remainingHeight);
						break;
					case INTRINSIC:
						$newHeight = $c.getJComponent().getMinimumSize().height;
						break;
					}
					switch ($c.getWidth().getUnit()) {
					case PIXEL:
						$newWidth = $c.getWidth().getValue();
						break;
					case STAR:
						$newWidth = (int) Math.round(($c.getWidth().getValue() / (double) $starsWidth) * $innerWidth);
						break;
					case PERCENT:
						$newWidth = (int) Math.round(($c.getWidth().getValue() / 100.0) * $innerWidth);
						break;
					case AUTO:
					case INTRINSIC:
						$newWidth = (int) Math.round($innerWidth);
						break;
					}
					$newX = (int) Math.round($posX);
					$newY = (int) Math.round($posY);
					$posY += $newHeight;
					break;
					
				case HORIZONTAL:
					$newHeight = (int) $height;
					switch ($c.getWidth().getUnit()) {
					case PIXEL:
						$newWidth = $c.getWidth().getValue();
						break;
					case STAR:
						$newWidth = (int) Math.round(($c.getWidth().getValue() / (double) $starsWidth) * $remainingWidth);
						break;
					case PERCENT:
						$newWidth = (int) Math.round(($c.getWidth().getValue() / 100.0) * $innerWidth);
						break;
					case AUTO:
						$newWidth = (int) Math.round((1 / (double) $starsWidth) * $remainingWidth);
						break;
					case INTRINSIC:
						$newWidth = $c.getJComponent().getMinimumSize().width;
						break;
					}
					switch ($c.getHeight().getUnit()) {
					case PIXEL:
						$newHeight = $c.getHeight().getValue();
						break;
					case STAR:
						$newHeight = (int) Math.round(($c.getHeight().getValue() / (double) $starsHeight) * $innerHeight);
						break;
					case PERCENT:
						$newHeight = (int) Math.round(($c.getHeight().getValue() / 100.0) * $innerHeight);
						break;
					case AUTO:
					case INTRINSIC:
						$newHeight = (int) Math.round($innerHeight);
						break;
					}
					$newX = (int) Math.round($posX);
					$newY = (int) Math.round($posY);
					$posX += $newWidth;
					break;
					
				case FIXED:
					switch ($c.getWidth().getUnit()) {
					case PERCENT:
						$newWidth = (int) Math.round(($c.getWidth().getValue() / 100.0) * $width);
						break;
					case PIXEL:
						$newWidth = $c.getWidth().getValue();
						break;
					case STAR:
					case INTRINSIC:
					case AUTO:
						// TODO: Better debugging
						// System.err.println("STAR, COMPUTE & AUTO are not allowed for positions in a fixed box");
						break;
					}
					switch ($c.getHeight().getUnit()) {
					case PERCENT:
						$newHeight = (int) Math.round(($c.getHeight().getValue() / 100.0) * $height);
						break;
					case PIXEL:
						$newHeight = $c.getHeight().getValue();
						break;
					case STAR:
					case INTRINSIC:
					case AUTO:
						// TODO: Better debugging
						// System.err.println("STAR, COMPUTE & AUTO are not allowed for dimensions in a fixed box");
						break;
					}
					
					switch ($c.getPosX().getUnit()) {
					case PERCENT:
						$newX = (int) Math.round((($c.getPosX().getValue() / 100.0) * $width) - (($c.getPosX().getValue() / 100.0) * $newWidth));
						break;
					case PIXEL:
						$newX = $c.getPosX().getValue();
						break;
					case STAR:
					case INTRINSIC:
					case AUTO:
						// TODO: Better debugging
						// System.err.println("STAR, COMPUTE & AUTO are not allowed for positions in a fixed box");
						break;
					}
					switch ($c.getPosY().getUnit()) {
					case PERCENT:
						$newY = (int) Math.round((($c.getPosY().getValue() / 100.0) * $height) - (($c.getPosY().getValue() / 100.0) * $newHeight));
						break;
					case PIXEL:
						$newY = $c.getPosY().getValue();
						break;
					case STAR:
					case INTRINSIC:
					case AUTO:
						// TODO: Better debugging
						// System.err.println("STAR, COMPUTE & AUTO are not allowed for dimensions in a fixed box");
						break;
					}
					break;
				}
				$c.getJComponent().setSize($newWidth, $newHeight);
				$c.getJComponent().setLocation($newX, $newY);
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public Dimension minimumLayoutSize(final Container $container) {
		int $width = 0;
		int $height = 0;
		if ($panel == $container) {
			
			for (net.abusingjava.swing.magic.Component $c : this.$container) {
				switch ($panel.getOrientation()) {
				case HORIZONTAL:
					switch ($c.getWidth().getUnit()) {
					case PIXEL:
						$width += $c.getWidth().getValue();
						break;
					case INTRINSIC:
						if ($c.getJComponent().getLayout() instanceof MagicLayoutManager) {
							$height += $c.getJComponent().getLayout().minimumLayoutSize($c.getJComponent()).width;
						}
						break;
					case PERCENT:
					case STAR:
					case AUTO:
						// System.err.println("PERCENT, STAR, and AUTO not yet supported within INTRINSIC");
						break;
					}
					
					switch ($c.getHeight().getUnit()) {
					case PIXEL:
						$height = Math.max($height, $c.getHeight().getValue());
						break;
					case INTRINSIC:
						if ($c.getJComponent().getLayout() instanceof MagicLayoutManager) {
							$height = Math.max($height, $c.getJComponent().getLayout().minimumLayoutSize($c.getJComponent()).height);
						}
						break;
					case PERCENT:
					case STAR:
					case AUTO:
						// System.err.println("PERCENT, STAR, and AUTO not yet supported within INTRINSIC");
						break;
					}
					break;
					
				case VERTICAL:
					switch ($c.getHeight().getUnit()) {
					case PIXEL:
						$height += $c.getHeight().getValue();
						break;
					case INTRINSIC:
						if ($c.getJComponent().getLayout() instanceof MagicLayoutManager) {
							$height += $c.getJComponent().getLayout().minimumLayoutSize($c.getJComponent()).height;
						}
						break;
					case PERCENT:
					case STAR:
					case AUTO:
						// System.err.println("PERCENT, STAR, and AUTO not yet supported within INTRINSIC");
						break;
					}
					
					switch ($c.getWidth().getUnit()) {
					case PIXEL:
						$width = Math.max($width, $c.getWidth().getValue());
						break;
					case INTRINSIC:
						if ($c.getJComponent().getLayout() instanceof MagicLayoutManager) {
							$width = Math.max($width, $c.getJComponent().getLayout().minimumLayoutSize($c.getJComponent()).width);
						}
						break;
					case PERCENT:
					case STAR:
					case AUTO:
						// System.err.println("PERCENT, STAR, and AUTO not yet supported within INTRINSIC");
						break;
					}
					break;
				case FIXED:
					int $x = 0;
					int $y = 0;
					if ($c.getWidth().getUnit() == Unit.PIXEL) {
						$x += $c.getWidth().getValue();
					}
					if ($c.getHeight().getUnit() == Unit.PIXEL) {
						$y += $c.getHeight().getValue();
					}
					if ($c.getPosX().getUnit() == Unit.PIXEL) {
						$x += $c.getPosX().getValue();
					}
					if ($c.getPosY().getUnit() == Unit.PIXEL) {
						$y += $c.getPosY().getValue();
					}
					$width = Math.max($width, $x);
					$height = Math.max($height, $y);
					break;
				}
			}
		}
		return new Dimension($width, $height);
	}

	@Override
	public Dimension preferredLayoutSize(final Container $c) {
		return minimumLayoutSize($c);
	}

	public MagicPanel getParent() {
		// TODO: Is this needed?
		return $parent;
	}
}
