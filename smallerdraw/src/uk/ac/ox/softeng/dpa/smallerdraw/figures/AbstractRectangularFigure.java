package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * An abstract figure for rectangular shapes.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class AbstractRectangularFigure extends AbstractFigure {

	public static final int MINWIDTH = 20, MINHEIGHT = 20;
	
	private Rectangle bounds = new Rectangle();
	
	public AbstractRectangularFigure() {
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
	public Rectangle getBounds() {
		return this.bounds.getBounds();
	}
	
	/**
	 * Set the bounds of this figure.
	 * 
	 * @param r the bounds
	 */
	public void setRectangle(Rectangle r) {
		Point p = r.getLocation();
		p.translate(r.width, r.height);
		this.modify(new SetBounds(r.getLocation(), p));
	}
	
	@Override
	public void move(Dimension offset) {
		final Point nw = getNW();
		final Point se = getSE();
		nw.translate(offset.width, offset.height);
		se.translate(offset.width, offset.height);
		this.modify(new SetBounds(nw, se));
	}

	protected Point getCenter() {
		final Point p = bounds.getLocation();
		p.translate(bounds.width / 2, bounds.height / 2);
		return p;
	}

	protected Point getN() {
		Point p = bounds.getLocation();
		p.translate(bounds.width / 2, 0);
		return p;
	}

	protected Point getS() {
		final Point p = bounds.getLocation();
		p.translate(bounds.width / 2, bounds.height);
		return p;
	}

	protected Point getE() {
		final Point p = bounds.getLocation();
		p.translate(bounds.width, bounds.height / 2);
		return p;
	}

	protected Point getW() {
		Point p = bounds.getLocation();
		p.translate(0, bounds.height / 2);
		return p;
	}

	protected Point getNE() {
		Point p = bounds.getLocation();
		p.translate(bounds.width, 0);
		return p;
	}

	protected Point getNW() {
		return bounds.getLocation();
	}

	protected Point getSE() {
		Point p = bounds.getLocation();
		p.translate(bounds.width, bounds.height);
		return p;
	}

	protected Point getSW() {
		Point p = bounds.getLocation();
		p.translate(0, bounds.height);
		return p;
	}

	protected void setCenter(final Point p) {
		final Point c = getCenter();
		this.move(new Dimension(p.x - c.x, p.y - c.y));
	}

	protected void setN(final Point p) {
		final Point se = getSE();
		if (se.y - p.y >= MINHEIGHT) {
			final Point nw = getNW();
			nw.translate(0, p.y - nw.y);
			this.modify(new SetBounds(nw, se));
		}
	}
	
	protected void setW(final Point p) {
		final Point se = getSE();
		if (se.x - p.x >= MINWIDTH) {
			final Point nw = getNW();
			nw.translate(p.x - nw.x, 0);
			this.modify(new SetBounds(nw, se));
		}
	}
	
	protected void setS(final Point p) {
		final Point nw = getNW();
		if (p.y - nw.y >= MINHEIGHT) {
			final Point se = getSE();
			se.translate(0, p.y - se.y);
			this.modify(new SetBounds(nw, se));
		}
	}
	
	protected void setE(final Point p) {
		final Point nw = getNW();
		if (p.x - nw.x >= MINWIDTH) {
			final Point se = getSE();
			se.translate(p.x - se.x, 0);
			this.modify(new SetBounds(nw, se));
		}
	}
	
	protected void setNE(final Point p) {
		final Point sw = getSW();
		if (p.x - sw.x >= MINWIDTH && sw.y - p.y >= MINHEIGHT) {
			this.modify(new SetBounds(p, sw));
		}
	}

	protected void setNW(final Point p) {
		final Point se = getSE();
		if (se.x - p.x >= MINWIDTH && se.y - p.y >= MINHEIGHT) {
			this.modify(new SetBounds(p, se));
		}
	}

	protected void setSE(final Point p) {
		final Point nw = getNW();
		if (p.x - nw.x >= MINWIDTH && p.y - nw.y >= MINHEIGHT) {
			this.modify(new SetBounds(p, nw));
		}
	}

	protected void setSW(final Point p) {
		final Point ne = getNE();
		if (ne.x - p.x >= MINWIDTH && p.y - ne.y >= MINHEIGHT) {
			this.modify(new SetBounds(p, ne));
		}
	}

	protected final class SetBounds implements ModifyCommand {
		private final Rectangle r;

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
			return getCenter();
		}
	
		@Override
		public void setLocation(Point p) {
			setCenter(p);
		}
		
	}

	protected final class NLocator implements Locatable {
		@Override
		public Point getLocation() {
			return getN();
		}

		@Override
		public void setLocation(Point p) {
			setN(p);
		}
	}
	
	protected final class SLocator implements Locatable {
		@Override
		public Point getLocation() {
			return getS();
		}

		@Override
		public void setLocation(Point p) {
			setS(p);
		}
	}
	
	protected final class ELocator implements Locatable {
		@Override
		public Point getLocation() {
			return getE();
		}
	
		@Override
		public void setLocation(Point p) {
			setE(p);
		}
	}

	protected final class WLocator implements Locatable {
		@Override
		public Point getLocation() {
			return getW();
		}

		@Override
		public void setLocation(Point p) {
			setW(p);
		}
	}
	
	protected final class NELocator implements Locatable {
		@Override
		public Point getLocation() {
			return getNE();
		}
	
		@Override
		public void setLocation(Point p) {
			setNE(p);
		}
	}

	protected final class NWLocator implements Locatable {
		@Override
		public Point getLocation() {
			return getNW();
		}
	
		@Override
		public void setLocation(Point p) {
			setNW(p);
		}
	}

	protected final class SELocator implements Locatable {
		@Override
		public Point getLocation() {
			return getSE();
		}
	
		@Override
		public void setLocation(Point p) {
			setSE(p);
		}
	}

	protected final class SWLocator implements Locatable {
		@Override
		public Point getLocation() {
			return getSW();
		}
	
		@Override
		public void setLocation(Point p) {
			setSW(p);
		}
	}
}
