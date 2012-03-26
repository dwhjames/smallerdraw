package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.Tool;
import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Locatable;
import uk.ac.ox.softeng.dpa.smallerdraw.SelectableModel;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * A tool for making selections.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see SelectableModel
 */
public class SelectionTool implements Tool {
	
	private SelectableModel model;
	private Point start;
	private Locatable locator;
	
	public SelectionTool(SelectableModel model) {
		this.model = model;
	}

	@Override
	public void onMouseDown(final Point pos) {
		for (Figure figure : model.figures()) {
			if (figure.isSelected() && (locator = figure.handleAt(pos)) != null) {
				break;
			}
			if (figure.contains(pos)) {
				figure.setSelected(!figure.isSelected());
				locator = new Relocator(pos);
				break;
			}
		}
		if (locator == null) {
			start = pos;
			model.setSelection(new Rectangle(pos));
		}
	}

	@Override
	public void onMouseDrag(Point pos) {
		if (locator != null) {
			locator.setLocation(pos);
		} else {
			model.setSelection(Geometry.rectangleFromPoints(start, pos));
		}
	}

	@Override
	public void onMouseUp(Point pos) {
		if (locator != null) {
			locator = null;
		} else {
			model.setSelection(new Rectangle());
		}
	}

	@Override
	public void onMouseMove(Point pos) { }

	/**
	 * A locatable object for the currently selected figures.
	 */
	private final class Relocator implements Locatable {
		private Point location;
	
		private Relocator(Point location) {
			this.location = location;
		}
	
		@Override
		public void setLocation(Point p) {
			Dimension offset = new Dimension(p.x - location.x, p.y - location.y);
			for (Figure figure : model.selectedFigures()) {
				figure.move(offset);
			}
			location = p;
		}
	
		@Override
		public Point getLocation() {
			return location;
		}
	}

}
