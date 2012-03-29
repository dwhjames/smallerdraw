package uk.ac.ox.softeng.dpa.smallerdraw;

import uk.ac.ox.softeng.dpa.smallerdraw.figures.GroupFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.OvalFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.PolygonFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.RectangleFigure;

/**
 * The interface for the Visitor pattern.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public interface Visitor {
	void visit(Picture picture);
	void visit(GroupFigure groupFigure);
	void visit(LineFigure lineFigure);
	void visit(OvalFigure ovalFigure);
	void visit(PolygonFigure polygonFigure);
	void visit(RectangleFigure rectangleFigure);
}
