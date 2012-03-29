package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;
import uk.ac.ox.softeng.dpa.smallerdraw.util.RectangleUtils;

/**
 * An abstract figure for rectangular shapes.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class AbstractRectangularFigure extends AbstractFigure {

	public static final int MINWIDTH = 20, MINHEIGHT = 20;
	
	private Rectangle bounds;
	
	public AbstractRectangularFigure() {
		this.bounds = new Rectangle();
		initializeHandles();
	}
	
	public AbstractRectangularFigure(AbstractRectangularFigure that) {
		super(that);
		this.bounds = new Rectangle(that.bounds);
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
	
	public abstract AbstractRectangularFigure clone();
	
	@Override
	public Rectangle getBounds() {
		return this.bounds.getBounds();
	}
	
	/**
	 * Set the bounds of this figure.
	 * 
	 * @param r the bounds
	 */
	public void setRectangle(Rectangle r) {
		this.modify(new SetBounds(r));
	}
	
	@Override
	public void move(Dimension offset) {
		final Point nw = RectangleUtils.getNW(bounds);
		final Point se = RectangleUtils.getSE(bounds);
		nw.translate(offset.width, offset.height);
		se.translate(offset.width, offset.height);
		this.modify(new SetBounds(nw, se));
	}

	protected final class SetBounds implements ModifyCommand {
		private final Rectangle r;

		protected SetBounds(Rectangle r) {
			this.r = new Rectangle(r);
		}

		protected SetBounds(Point p1, Point p2) {
			r = Geometry.rectangleFromPoints(p1, p2);
		}

		@Override
		public void execute() {
			bounds.setBounds(r);
		}
	}
	
	protected final class CLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getCenter(bounds);
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
			return RectangleUtils.getN(bounds);
		}

		@Override
		public void setLocation(Point p) {
			Point se = RectangleUtils.getSE(bounds);
			if (se.y - p.y >= MINHEIGHT) {
				Point nw = RectangleUtils.getNW(bounds);
				nw.translate(0, p.y - nw.y);
				modify(new SetBounds(nw, se));
			}
		}
	}
	
	protected final class SLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getS(bounds);
		}

		@Override
		public void setLocation(Point p) {
			Point nw = RectangleUtils.getNW(bounds);
			if (p.y - nw.y >= MINHEIGHT) {
				Point se = RectangleUtils.getSE(bounds);
				se.translate(0, p.y - se.y);
				modify(new SetBounds(nw, se));
			}
		}
	}
	
	protected final class ELocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getE(bounds);
		}
	
		@Override
		public void setLocation(Point p) {
			Point nw = RectangleUtils.getNW(bounds);
			if (p.x - nw.x >= MINWIDTH) {
				Point se = RectangleUtils.getSE(bounds);
				se.translate(p.x - se.x, 0);
				modify(new SetBounds(nw, se));
			}
		}
	}

	protected final class WLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getW(bounds);
		}

		@Override
		public void setLocation(Point p) {
			Point se = RectangleUtils.getSE(bounds);
			if (se.x - p.x >= MINWIDTH) {
				Point nw = RectangleUtils.getNW(bounds);
				nw.translate(p.x - nw.x, 0);
				modify(new SetBounds(nw, se));
			}
		}
	}
	
	protected final class NELocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getNE(bounds);
		}
	
		@Override
		public void setLocation(Point p) {
			Point sw = RectangleUtils.getSW(bounds);
			if (p.x - sw.x >= MINWIDTH && sw.y - p.y >= MINHEIGHT) {
				modify(new SetBounds(p, sw));
			}
		}
	}

	protected final class NWLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getNW(bounds);
		}
	
		@Override
		public void setLocation(Point p) {
			Point se = RectangleUtils.getSE(bounds);
			if (se.x - p.x >= MINWIDTH && se.y - p.y >= MINHEIGHT) {
				modify(new SetBounds(p, se));
			}
		}
	}

	protected final class SELocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getSE(bounds);
		}
	
		@Override
		public void setLocation(Point p) {
			Point nw = RectangleUtils.getNW(bounds);
			if (p.x - nw.x >= MINWIDTH && p.y - nw.y >= MINHEIGHT) {
				modify(new SetBounds(p, nw));
			}
		}
	}

	protected final class SWLocator implements Locatable {
		@Override
		public Point getLocation() {
			return RectangleUtils.getSW(bounds);
		}
	
		@Override
		public void setLocation(Point p) {
			Point ne = RectangleUtils.getNE(bounds);
			if (ne.x - p.x >= MINWIDTH && p.y - ne.y >= MINHEIGHT) {
				modify(new SetBounds(p, ne));
			}
		}
	}
}
