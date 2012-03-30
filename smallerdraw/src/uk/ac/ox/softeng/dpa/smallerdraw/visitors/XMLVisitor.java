package uk.ac.ox.softeng.dpa.smallerdraw.visitors;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Picture;
import uk.ac.ox.softeng.dpa.smallerdraw.Visitor;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.GroupFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.OvalFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.PolygonFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.RectangleFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.util.RectangleUtils;

/**
 * A vistor that writes XML to an OutputStream.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see OutputStream
 * @see XMLStreamWriter
 */
public class XMLVisitor implements Visitor {

	private XMLStreamWriter xmlWriter;
	private static final String fillColor = "#D3D3D3";

	public XMLVisitor(OutputStream outputStream) {
		Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream,
				Charset.forName("UTF-8")));
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
		try {
			xmlWriter = xmlOutputFactory.createXMLStreamWriter(writer);
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	public void start(Picture picture) {
		try {
			visit(picture);
			xmlWriter.flush();
			xmlWriter.close();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(Picture picture) {
		try {
			xmlWriter.writeStartDocument();
			xmlWriter.writeDTD("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
			xmlWriter.writeStartElement("svg");
			xmlWriter.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			xmlWriter.writeAttribute("version", "1.1");
			
			xmlWriter.writeStartElement("g");
			xmlWriter.writeAttribute("stroke", "black");
			xmlWriter.writeAttribute("stroke-width", "2px");
			
			for (Figure figure : picture.figures()) {
				figure.accept(this);
			}
			
			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(GroupFigure groupFigure) {
		try {
			xmlWriter.writeStartElement("g");

			for (Figure figure : groupFigure.getSubFigures()) {
				figure.accept(this);
			}

			xmlWriter.writeEndElement();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(LineFigure lineFigure) {
		try {
			xmlWriter.writeEmptyElement("line");
			xmlWriter.writeAttribute("x1", String.valueOf(lineFigure.getStart().x));
			xmlWriter.writeAttribute("y1", String.valueOf(lineFigure.getStart().y));
			xmlWriter.writeAttribute("x2", String.valueOf(lineFigure.getEnd().x));
			xmlWriter.writeAttribute("y2", String.valueOf(lineFigure.getEnd().y));
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(OvalFigure ovalFigure) {
		try {
			xmlWriter.writeEmptyElement("ellipse");
			Rectangle r = ovalFigure.getBounds();
			Point c = RectangleUtils.getCenter(r);
			xmlWriter.writeAttribute("cx", String.valueOf(c.x));
			xmlWriter.writeAttribute("cy", String.valueOf(c.y));
			xmlWriter.writeAttribute("rx", String.valueOf(r.width / 2));
			xmlWriter.writeAttribute("ry", String.valueOf(r.height / 2));
			writeFilledAttribute(ovalFigure);
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(PolygonFigure polygonFigure) {
		try {
			xmlWriter.writeEmptyElement("polygon");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < polygonFigure.getSize(); i++) {
				Point p = polygonFigure.getPoint(i);
				builder.append(p.x).append(',').append(p.y).append(' ');
			}
			xmlWriter.writeAttribute("points", builder.toString());
			writeFilledAttribute(polygonFigure);
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(RectangleFigure rectangleFigure) {
		try {
			xmlWriter.writeEmptyElement("rect");
			Rectangle r = rectangleFigure.getBounds();
			xmlWriter.writeAttribute("x", String.valueOf(r.x));
			xmlWriter.writeAttribute("y", String.valueOf(r.y));
			xmlWriter.writeAttribute("width", String.valueOf(r.width));
			xmlWriter.writeAttribute("height", String.valueOf(r.height));
			writeFilledAttribute(rectangleFigure);
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}
	
	private void writeFilledAttribute(Figure figure) {
		try {
			if (figure.isFilled()) {
				xmlWriter.writeAttribute("fill", fillColor);
			} else {
				xmlWriter.writeAttribute("fill", "none");
			}
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}
}
