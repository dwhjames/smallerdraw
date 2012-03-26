package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Point;

import uk.ac.ox.softeng.dpa.smallerdraw.Model;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.PolygonFigure;

/**
 * A tool for drawing polygons.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see PolygonFigure
 */
public class PolygonTool extends EmptyTool {
	
	private int total, i = 0;
	private Point start;
	private boolean down;
	private LineFigure lineFigure;
	private PolygonFigure figure;
	
	public PolygonTool(Model model, int total) {
		super(model);
		this.total = total;
	}
	
	@Override
	public void onMouseDown(Point pos) {
		start = pos;
		if (!down) {
			i++;
		}
		down = true;
	}
	
	@Override
	public void onMouseDrag(Point pos) {
		onMouseMove(pos);
	}
	
	@Override
	public void onMouseMove(Point pos) {
		if (!pos.equals(start)) {
			if (down) {
				switch (i) {
				case 1:
					model.add(lineFigure = new LineFigure(start, pos));
					break;
				case 2:
					model.remove(lineFigure);
					model.add(figure = new PolygonFigure(new Point[] {lineFigure.getStart(),
							                                          lineFigure.getEnd(),
							                                          pos}));
					break;
				default:
					if (i < total) {
						figure.add(pos);
					} else {
						i = 0;
					}
					break;
				}
				down = false;
			} else {
				switch (i) {
				case 0:
					break;
				case 1:
					lineFigure.setEnd(pos);
					break;
				default:
					figure.setPoint(i, pos);
					break;
				}
			}
		}
	}
}
