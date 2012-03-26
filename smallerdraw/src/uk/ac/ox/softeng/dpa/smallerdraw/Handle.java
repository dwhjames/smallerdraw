package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ox.softeng.dpa.smallerdraw.tools.ConnectorTool;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Observable;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Observer;

/**
 * Handles are graphical objects that are used to manipulate figures. They are
 * located relative to figures. Furthermore, the special connecting line can be
 * using to link two figures together through their handles.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see Figure
 * @see ConnectorTool
 */
public class Handle implements Drawable, Locatable, Observable<Point> {
	
	public static final int WIDTH = 4;
	public static final int HEIGHT = 4;
	
	private static final Color FILL_COLOR = new Color(0xADD8E6);
	private static final Color BORDER_COLOR = Color.BLUE;
	private static final Stroke BORDER_STROKE = new BasicStroke(1);
	
	private Locatable locator;
	
	private List<Observer<Point>> observers;
	
	public Handle(Locatable locator) {
		this.locator = locator;
		this.observers = new ArrayList<Observer<Point>>();
	}

	public Point getLocation() {
		return locator.getLocation();
	}

	public void setLocation(Point location) {
		locator.setLocation(location);
		locationChanged();
	}
	
	@Override
	public void draw(Graphics2D g) {
		Rectangle handle = new Rectangle(new Point(getLocation().x-WIDTH, getLocation().y-HEIGHT),
				                         new Dimension(WIDTH*2, HEIGHT*2));
		g.setPaint(FILL_COLOR);
		g.fill(handle);
		g.setPaint(BORDER_COLOR);
		g.setStroke(BORDER_STROKE);
		g.draw(handle);
	}

	/**
	 * Returns true if the given point is contained in the bounds of the handle.
	 * 
	 * @param p
	 *            the point to test
	 * @return true if the point is contained
	 * @see Figure#handleAt(Point)
	 * @see Figure#contains(Point)
	 */
	public boolean contains(Point p) {
		return Math.abs(locator.getLocation().x - p.x) <= WIDTH && Math.abs(locator.getLocation().y - p.y) <= HEIGHT;
	}

	@Override
	public void subscribe(Observer<Point> observer) {
		observers.add(observer);
	}

	/**
	 * Notify this handle that its underlying location may have changed,
	 * so that it may notify the objects that observe this handle.
	 * 
	 * @see Observable#subscribe(Observer)
	 * @see Observer#update(Object)
	 */
	public void locationChanged() {
		for (Observer<Point> observer : observers) {
			observer.update(getLocation());
		}
	}
}
