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
import uk.ac.ox.softeng.dpa.smallerdraw.figures.ConnectingLineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.GroupFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.LineFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.OvalFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.PolygonFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.RectangleFigure;

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
			xmlWriter.writeStartElement("picture");

			for (Figure figure : picture.figures()) {
				figure.accept(this);
			}

			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(GroupFigure groupFigure) {
		try {
			xmlWriter.writeStartElement("group");

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
			xmlWriter.writeStartElement("line");
			if (lineFigure instanceof ConnectingLineFigure) {
				xmlWriter.writeAttribute("connecting", "true");
			}
			writePoint(lineFigure.getStart());
			writePoint(lineFigure.getEnd());
			xmlWriter.writeEndElement();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(OvalFigure ovalFigure) {
		try {
			xmlWriter.writeStartElement("oval");
			writeRectangle(ovalFigure.getBounds());
			xmlWriter.writeEndElement();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(PolygonFigure polygonFigure) {
		try {
			xmlWriter.writeStartElement("polygon");
			for (int i = 0; i < polygonFigure.getSize(); i++) {
				writePoint(polygonFigure.getPoint(i));
			}
			xmlWriter.writeEndElement();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void visit(RectangleFigure rectangleFigure) {
		try {
			xmlWriter.writeStartElement("rectangle");
			writeRectangle(rectangleFigure.getBounds());
			xmlWriter.writeEndElement();
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	private void writePoint(Point p) {
		try {
			xmlWriter.writeEmptyElement("point");
			xmlWriter.writeAttribute("x", String.valueOf(p.x));
			xmlWriter.writeAttribute("y", String.valueOf(p.y));
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}

	private void writeRectangle(Rectangle r) {
		try {
			xmlWriter.writeEmptyElement("rect");
			xmlWriter.writeAttribute("x", String.valueOf(r.x));
			xmlWriter.writeAttribute("y", String.valueOf(r.y));
			xmlWriter.writeAttribute("w", String.valueOf(r.width));
			xmlWriter.writeAttribute("h", String.valueOf(r.height));
		} catch (XMLStreamException ex) {
			ex.printStackTrace();
		}
	}
}
