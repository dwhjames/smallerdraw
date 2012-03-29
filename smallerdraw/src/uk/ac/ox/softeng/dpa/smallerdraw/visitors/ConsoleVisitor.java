package uk.ac.ox.softeng.dpa.smallerdraw.visitors;

import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Picture;
import uk.ac.ox.softeng.dpa.smallerdraw.Visitor;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.GroupFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.OvalFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.PolygonFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.RectangleFigure;

/**
 * A simple visitor that dumps the figure names to the console.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class ConsoleVisitor implements Visitor {
	
	private String indent = "";

	@Override
	public void visit(Picture picture) {
		System.out.print(indent);
		System.out.println("Picture");
		indent = indent + "  ";
		for (Figure figure : picture.figures()) {
			figure.accept(this);
		}
		indent = indent.substring(2);
	}

	@Override
	public void visit(GroupFigure groupFigure) {
		System.out.print(indent);
		System.out.println("Group");
		indent = indent + "  ";
		for (Figure figure : groupFigure.getSubFigures()) {
			figure.accept(this);
		}
		indent = indent.substring(2);
	}

	@Override
	public void visit(LineFigure lineFigure) {
		System.out.print(indent);
		System.out.println("LineFigure");
	}

	@Override
	public void visit(OvalFigure ovalFigure) {
		System.out.print(indent);
		System.out.println("OvalFigure");
	}

	@Override
	public void visit(PolygonFigure polygonFigure) {
		System.out.print(indent);
		System.out.println("PolygonFigure");
	}

	@Override
	public void visit(RectangleFigure rectangleFigure) {
		System.out.print(indent);
		System.out.println("RectangleFigure");
	}

}
