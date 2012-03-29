package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * A figure for lines.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class LineFigure extends AbstractFigure {

	private Point start, end;
	
	public LineFigure(Point start, Point end) {
		this.start = start;
		this.end   = end;
		initializeHandles();
	}
	
	public LineFigure(LineFigure that) {
		super(that);
		this.start = new Point(that.start);
		this.end = new Point(that.end);
		initializeHandles();
	}
	
	private void initializeHandles() {
		handles = new Handle[] {
			new Handle(new StartLocator()),
			new Handle(new EndLocactor())
		};
	}
	
	public LineFigure clone() {
		return new LineFigure(this);
	}

	@Override
	public Rectangle getBounds() {
		return Geometry.rectangleFromPoints(start, end);
	}
	
	@Override
	public boolean isFilled() {
		return false;
	}
	
	@Override
	public void setFilled(boolean filled) { }

	@Override
	public void move(final Dimension offset) {
		this.modify(new ModifyCommand() {
			@Override
			public void execute() {
				start.translate(offset.width, offset.height);
				end.translate(offset.width, offset.height);
			}
		});
	}

	@Override
	public boolean contains(Point p) {
		return Geometry.linePointIntersect(start, end, p);
	}

	/**
	 * Returns the location of the start of this line figure.
	 * 
	 * @return the start location
	 */
	public Point getStart() {
		return new Point(start);
	}
	
	/**
	 * Sets the location of the start of this line figure.
	 * 
	 * @param p the new start location
	 */
	public void setStart(final Point p) {
		this.modify(new SetStartLocation(p));
	}
	
	/**
	 * Returns the location of the end of this line figure.
	 * 
	 * @return the end location
	 */
	public Point getEnd() {
		return new Point(end);
	}
	
	/**
	 * Sets the location of the end of this line figure.
	 * 
	 * @param p the new end location
	 */
	public void setEnd(final Point p) {
		this.modify(new SetEndLocation(p));
	}
	
	@Override
	protected Shape createShape() {
		return new Line2D.Double(getStart(), getEnd());
	}

	/**
	 * A command for modifying the start location of a line figure.
	 * 
	 * @see AbstractFigure#modify(ModifyCommand)
	 */
	protected final class SetStartLocation implements ModifyCommand {
		private final Point p;
		
		protected SetStartLocation(final Point p) {
			this.p = p;
		}
		
		@Override
		public void execute() {
			start.setLocation(p);
		}
	}
	
	/**
	 * A command for modifying the end location of a line figure.
	 * 
	 * @see AbstractFigure#modify(ModifyCommand)
	 */
	protected final class SetEndLocation implements ModifyCommand {
		private final Point p;
		
		protected SetEndLocation(final Point p) {
			this.p = p;
		}
		
		@Override
		public void execute() {
			end.setLocation(p);
		}
	}
	
	/**
	 * A locator for the {@link Handle} at the start of the line figure.
	 * 
	 * @see Handle
	 */
	protected final class StartLocator implements Locatable {
		@Override
		public Point getLocation() {
			return getStart();
		}
		
		@Override
		public void setLocation(final Point p) {
			setStart(p);
		}
	}
	
	/**
	 * A locator for the {@link Handle} at the end of the line figure.
	 * 
	 * @see Handle
	 */
	protected final class EndLocactor implements Locatable {
		@Override
		public Point getLocation() {
			return getEnd();
		}

		@Override
		public void setLocation(final Point p) {
			setEnd(p);
		}
	}
}
