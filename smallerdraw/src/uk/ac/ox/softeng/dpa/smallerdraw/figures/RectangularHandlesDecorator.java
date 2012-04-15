package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.command.RedrawCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.tools.RectangularTool;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;
import uk.ac.ox.softeng.dpa.smallerdraw.util.RectangleUtils;

/**
 * A decorator for rectangular figures that provides handles for moving and reshaping.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see AbstractRectangularFigure
 * @see RectangularTool
 */
public class RectangularHandlesDecorator extends AbstractRectangularFigure {
	private final AbstractRectangularFigure delegate;

	public RectangularHandlesDecorator(AbstractRectangularFigure delegate) {
		this.delegate = delegate;
		initializeHandles();
	}

	public RectangularHandlesDecorator(RectangularHandlesDecorator that) {
		this.delegate = that.delegate.clone();
		initializeHandles();
	}

	private void initializeHandles() {
		this.handles = new Handle[] {
			new Handle(new CLocator()),
			new Handle(new NLocator()),
			new Handle(new SLocator()),
			new Handle(new ELocator()),
			new Handle(new WLocator()),
			new Handle(new NELocator()),
			new Handle(new NWLocator()),
			new Handle(new SELocator()),
			new Handle(new SWLocator())
		};
	}

	@Override
	public RectangularHandlesDecorator clone() {
		return new RectangularHandlesDecorator(this);
	}

	@Override
	public Rectangle getBounds() {
		return delegate.getBounds();
	}

	@Override
	public boolean isSelected() {
		return delegate.isSelected();
	}

	@Override
	public void setSelected(boolean selected) {
		delegate.setSelected(selected);
	}

	@Override
	public boolean isFilled() {
		return delegate.isFilled();
	}

	@Override
	public void setFilled(boolean filled) {
		delegate.setFilled(filled);
	}

	@Override
	public void move(Dimension offset) {
		delegate.move(offset);
		notifyHandles();
	}

	@Override
	public boolean contains(Point p) {
		return delegate.contains(p);
	}

	@Override
	public void setRedrawCommand(RedrawCommand redrawCommand) {
		super.setRedrawCommand(redrawCommand);
		delegate.setRedrawCommand(redrawCommand);
	}

	@Override
	public void draw(Graphics2D g) {
		delegate.draw(g);
		drawHandles(g);
	}

	@Override
	public void dispose() {
		super.dispose();
		delegate.dispose();
	}

	@Override
	protected void modify(ModifyCommand command) {
		delegate.modify(command);
		notifyHandles();
	}

	@Override
	protected Shape createShape() {
		return null;
	}

	@Override
	public void setRectangle(Rectangle r) {
		delegate.setRectangle(r);
		notifyHandles();
	}

	@Override
	public Handle handleAt(Point p) {
		for (Handle handle : handles) {
			if (handle.contains(p)) {
				return handle;
			}
		}
		return delegate.handleAt(p);
	}

	private void notifyHandles() {
		for (Handle handle : handles) {
			handle.locationChanged();
		}
	}

	protected final class CLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getCenter(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Point c = getLocation();
			move(new Dimension(p.x - c.x, p.y - c.y));
		}
	}

	protected final class NLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getN(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Rectangle bounds = getBounds();
			Point se = RectangleUtils.getSE(bounds);
			if (se.y - p.y >= MINHEIGHT) {
				Point nw = RectangleUtils.getNW(bounds);
				nw.translate(0, p.y - nw.y);
				setRectangle(Geometry.rectangleFromPoints(nw, se));
			}
		}
	}

	protected final class SLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getS(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Rectangle bounds = getBounds();
			Point nw = RectangleUtils.getNW(bounds);
			if (p.y - nw.y >= MINHEIGHT) {
				Point se = RectangleUtils.getSE(bounds);
				se.translate(0, p.y - se.y);
				setRectangle(Geometry.rectangleFromPoints(nw, se));
			}
		}
	}

	protected final class ELocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getE(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Rectangle bounds = getBounds();
			Point nw = RectangleUtils.getNW(bounds);
			if (p.x - nw.x >= MINWIDTH) {
				Point se = RectangleUtils.getSE(bounds);
				se.translate(p.x - se.x, 0);
				setRectangle(Geometry.rectangleFromPoints(nw, se));
			}
		}
	}

	protected final class WLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getW(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Rectangle bounds = getBounds();
			Point se = RectangleUtils.getSE(bounds);
			if (se.x - p.x >= MINWIDTH) {
				Point nw = RectangleUtils.getNW(bounds);
				nw.translate(p.x - nw.x, 0);
				setRectangle(Geometry.rectangleFromPoints(nw, se));
			}
		}
	}

	protected final class NELocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getNE(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Point sw = RectangleUtils.getSW(getBounds());
			if (p.x - sw.x >= MINWIDTH && sw.y - p.y >= MINHEIGHT) {
				setRectangle(Geometry.rectangleFromPoints(p, sw));
			}
		}
	}

	protected final class NWLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getNW(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Point se = RectangleUtils.getSE(getBounds());
			if (se.x - p.x >= MINWIDTH && se.y - p.y >= MINHEIGHT) {
				setRectangle(Geometry.rectangleFromPoints(p, se));
			}
		}
	}

	protected final class SELocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getSE(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Point nw = RectangleUtils.getNW(getBounds());
			if (p.x - nw.x >= MINWIDTH && p.y - nw.y >= MINHEIGHT) {
				setRectangle(Geometry.rectangleFromPoints(p, nw));
			}
		}
	}

	protected final class SWLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getSW(getBounds());
		}

		@Override
		public void setLocation(Point p) {
			Point ne = RectangleUtils.getNE(getBounds());
			if (ne.x - p.x >= MINWIDTH && p.y - ne.y >= MINHEIGHT) {
				setRectangle(Geometry.rectangleFromPoints(p, ne));
			}
		}
	}
}
