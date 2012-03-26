package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import uk.ac.ox.softeng.dpa.smallerdraw.tools.EmptyTool;

/**
 * An implementation of the {@link View} interface. A canvas is a Java Swing
 * component, as it extends {@link JComponent}. The key functionality is the
 * subscription to mouse events and painting the component.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
@SuppressWarnings("serial")
public class Canvas extends JComponent implements View {

	private Tool tool;
	private Drawable model;

	public Canvas(Drawable model) {
		this.tool = new EmptyTool();
		this.model = model;

		setDoubleBuffered(true);
		setOpaque(false);

		ToolAdapter adapter = new ToolAdapter();
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
	}

	/**
	 * Set the tool that will receive mouse events from this component.
	 * 
	 * @param tool
	 *            the tool to use
	 */
	public void setActiveTool(Tool tool) {
		this.tool = tool;
	}

	@Override
	public void redraw(Rectangle clip) {
		repaint(clip);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// let background be painted first
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.setPaint(Color.WHITE);
		g2.fill(g.getClip());
		model.draw(g2);
	}

	/**
	 * An adapter to the {@link MouseListener} and {@link MouseMotionListener}
	 * interfaces.
	 */
	private final class ToolAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				tool.onMouseDown(e.getPoint());
				break;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				tool.onMouseDrag(e.getPoint());
				break;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			tool.onMouseMove(e.getPoint());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				tool.onMouseUp(e.getPoint());
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			repaint();
		}
	}

}
