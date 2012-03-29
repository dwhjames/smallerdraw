package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.command.RedrawCommand;

/**
 * A figure that is a grouping of other figures.
 *  
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class GroupFigure extends AbstractFigure {
	
	private final List<Figure> subFigures;
	
	public GroupFigure(List<Figure> subFigures) {
		this.subFigures = subFigures;
		for (Figure figure : subFigures) {
			figure.setSelected(false);
		}
		initializeHandles();
	}
	
	public GroupFigure(GroupFigure that) {
		super(that);
		this.subFigures = new ArrayList<Figure>();
		for (Figure figure : that.subFigures) {
			this.subFigures.add(figure.clone());
		}
		initializeHandles();
	}
	
	private void initializeHandles() {
		handles = new Handle[] {
			new Handle(new GroupLocator())
		};
	}
	
	public GroupFigure clone() {
		return new GroupFigure(this);
	}
	
	/**
	 * Returns an iterable for the subfigures of this group figure.
	 * 
	 * @return an iterable of the subfigures
	 */
	public Iterable<Figure> getSubFigures() {
		return subFigures;
	}
	
	/** 
	 * Draw this group figure by drawing all of its subfigures,
	 * and then draw the group figure’s handle.
	 * 
	 * @param g the graphics context
	 * @see AbstractFigure#draw(Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		for (Figure figure : subFigures) {
			figure.draw(g);
		}
		drawHandles(g);
	}

	@Override
	public Rectangle getBounds() {
		Rectangle r = new Rectangle(0, 0, -1, -1);
		for (Figure figure : subFigures) {
			r.add(figure.getBounds());
		}
		return r;
	}

	@Override
	public void move(Dimension offset) {
		for (Figure figure : subFigures) {
			figure.move(offset);
		}
	}

	@Override
	public boolean contains(Point p) {
		for (Figure figure : subFigures) {
			if (figure.contains(p)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setRedrawCommand(RedrawCommand redrawCommand) {
		super.setRedrawCommand(redrawCommand);
		for (Figure figure : subFigures) {
			figure.setRedrawCommand(redrawCommand);
		}
	}
	
	/**
	 * A group figure cannot be filled or unfilled.
	 * 
	 * @return false
	 * @see AbstractFigure#isFilled()
	 */
	@Override
	public boolean isFilled() {
		return false;
	}
	
	/**
	 * A group figure cannot be filled or unfilled.
	 * 
	 * @see AbstractFigure#setFilled(boolean)
	 */
	@Override
	public void setFilled(boolean filled) {
		return;
	}

	/**
	 * A group figure has no shape.
	 * 
	 * @return always returns null
	 * @see AbstractFigure#createShape()
	 * @see #draw(Graphics2D)
	 */
	@Override
	protected Shape createShape() {
		return null;
	}

	private final class GroupLocator implements Locatable {
		@Override
		public void setLocation(Point p) {
			Point c = getLocation();
			move(new Dimension(p.x - c.x, p.y - c.y));
		}
	
		@Override
		public Point getLocation() {
			Rectangle r = getBounds();
			Point p = r.getLocation();
			p.translate(r.width / 2, r.height / 2);
			return p;
		}
	}

}
