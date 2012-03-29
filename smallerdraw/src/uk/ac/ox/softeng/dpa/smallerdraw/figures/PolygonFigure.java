package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * A figure for polygons.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class PolygonFigure extends AbstractFigure {
	
	/*
	 * invariant: length >= 3 
	 */
	private Point[] polygon;
	
	public PolygonFigure(Point[] polygon) {
		this.polygon = polygon;
		initializeHandles();
	}
	
	public PolygonFigure(PolygonFigure that) {
		super(that);
		this.polygon = Arrays.copyOf(that.polygon, that.polygon.length);
		initializeHandles();
	}
	
	private void initializeHandles() {
		handles = new Handle[polygon.length];
		for (int i = 0; i < polygon.length; i++) {
			handles[i] = new Handle(new PolygonPointLocator(i));
		}
	}
	
	public PolygonFigure clone() {
		return new PolygonFigure(this);
	}
	
	/**
	 * Extend this figure with another point.
	 * 
	 * @param p a new point
	 */
	public void add(final Point p) {
		this.modify(new ModifyCommand() {
			@Override
			public void execute() {
				polygon = Arrays.copyOf(polygon, polygon.length + 1);
				polygon[polygon.length-1] = p;
				handles = Arrays.copyOf(handles, handles.length + 1);
				handles[handles.length-1] = new Handle(new PolygonPointLocator(handles.length-1));
			}
		});
	}

	/**
	 * Returns the location of the {@code i}th point in this figure.
	 * 
	 * @param i the index of the polygon
	 * @return the location of point i
	 */
	public Point getPoint(int i) {
		return polygon[i];
	}
	
	/**
	 * Sets the location of the {@code i}th point in this figure.
	 * 
	 * @param i the index of the polygon
	 * @param p the new location 
	 */
	public void setPoint(final int i, final Point p) {
		this.modify(new ModifyCommand() {
			@Override
			public void execute() {
				polygon[i] = p;
			}
		});
	}

	@Override
	public Rectangle getBounds() {
		return createShape().getBounds();
	}

	@Override
	public boolean contains(Point p) {
		if (isFilled()) {
			return createShape().contains(p);
		} else {
			int i = 0;
			// invariant: no intersection with edges between points 0 -- 1 -- .. -- i
			while (i < polygon.length &&
				   !Geometry.linePointIntersect(polygon[i],
						                        polygon[(i + 1) % polygon.length],
						                        p)) {
				i++;
			}
			return i < polygon.length;
		}
	}

	@Override
	public void move(final Dimension offset) {
		this.modify(new ModifyCommand() {
			@Override
			public void execute() {
				for (Point p : polygon) {
					p.translate(offset.width, offset.height);
				}
			}
		});
	}

	@Override
	protected Shape createShape() {
		GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO, polygon.length);
		path.moveTo(polygon[0].x, polygon[0].y);
		for (int i = 1; i < polygon.length; i++) {
			path.lineTo(polygon[i].x, polygon[i].y);
		}
		path.closePath();
		return path;
	}

	protected final class PolygonPointLocator implements Locatable {
		private final int index;
		
		protected PolygonPointLocator(int index) {
			this.index = index;
		}
		
		@Override
		public Point getLocation() {
			return getPoint(index);
		}

		@Override
		public void setLocation(Point p) {
			setPoint(index, p);
		}
	}
}
