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
import uk.ac.ox.softeng.dpa.smallerdraw.Visitor;
import uk.ac.ox.softeng.dpa.smallerdraw.command.RedrawCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.util.RectangleUtils;

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
	
	protected void traverse(FigureAction action) {
		for (Figure figure : subFigures) {
			action.execute(figure);
		}
	}

	/** 
	 * Draw this group figure by drawing all of its subfigures,
	 * and then draw the group figure’s handle.
	 * 
	 * @param g the graphics context
	 * @see AbstractFigure#draw(Graphics2D)
	 */
	@Override
	public void draw(final Graphics2D g) {
		traverse(new FigureAction() {
			@Override
			public void execute(Figure figure) {
				figure.draw(g);
			}
		});
		drawHandles(g);
	}

	@Override
	public Rectangle getBounds() {
		final Rectangle r = new Rectangle(0, 0, -1, -1);
		traverse(new FigureAction() {
			@Override
			public void execute(Figure figure) {
				r.add(figure.getBounds());
			}
		});
		return r;
	}

	@Override
	public void move(final Dimension offset) {
		traverse(new FigureAction() {
			@Override
			public void execute(Figure figure) {
				figure.move(offset);
			}
		});
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

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
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
			return RectangleUtils.getCenter(getBounds());
		}
	}

	private interface FigureAction {
		void execute(Figure figure);
	}
}
