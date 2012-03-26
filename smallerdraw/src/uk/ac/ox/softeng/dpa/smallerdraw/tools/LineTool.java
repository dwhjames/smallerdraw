package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Point;

import uk.ac.ox.softeng.dpa.smallerdraw.Model;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.util.Geometry;

/**
 * A tool for drawing lines.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see LineFigure
 */
public class LineTool extends EmptyTool {

	private Point start;
	private LineFigure figure;

	public LineTool(Model model) {
		super(model);
	}
	
	@Override
	public void onMouseDown(Point pos) {
		start = pos;	
	}
	
	@Override
	public void onMouseDrag(Point pos) {
		if (figure == null) {
			figure = new LineFigure(start, pos);
			model.add(figure);
		} else {
			figure.setEnd(pos);
		}
	}
	
	@Override
	public void onMouseUp(Point pos) {
		if (figure != null &&
			(Geometry.pythagoras(pos.x - start.x, pos.y - start.y) < 5 * 5)) {
			model.remove(figure);
		}
		figure = null;
	}

}
