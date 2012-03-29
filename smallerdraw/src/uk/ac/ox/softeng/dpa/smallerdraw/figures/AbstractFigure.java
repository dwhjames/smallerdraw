package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

import uk.ac.ox.softeng.dpa.smallerdraw.Disposable;
import uk.ac.ox.softeng.dpa.smallerdraw.Drawable;
import uk.ac.ox.softeng.dpa.smallerdraw.Figure;
import uk.ac.ox.softeng.dpa.smallerdraw.Handle;
import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.command.NullRedrawCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.command.RedrawCommand;

/**
 * This class contains the common functionality of figures, including drawing
 * and setting the selected and filled properties.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class AbstractFigure implements Figure {
	
	private static final Color SELECTED_FILL_COLOR = new Color(0xADD8E6);
	private static final Color FILL_COLOR = new Color(0xD3D3D3);
	/**
	 * The {@link Stroke} object to draw the border of this figure.
	 */
	protected static final Stroke BORDER_STROKE = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
	private boolean selected = false;
	private boolean filled = false;
	private RedrawCommand redrawCommand = NullRedrawCommand.getInstance();
	
	protected Handle[] handles = new Handle[0];
	
	public AbstractFigure() { }
	
	public AbstractFigure(AbstractFigure that) {
		this.selected = that.selected;
		this.filled = that.filled;
		this.redrawCommand = that.redrawCommand;
		// handles will be copied in subclasses
	}
	
	public abstract AbstractFigure clone();
	
	@Override
	public void setRedrawCommand(RedrawCommand redrawCommand) {
		this.redrawCommand = redrawCommand;
	}

	/**
	 * An example of the Template Method pattern: subclasses must implement
	 * the {@link #createShape() createShape} method to complete the drawing
	 * routine.
	 * 
	 * @param g a Java 2D graphics context
	 * @see #createShape()
	 * @see #drawHandles(Graphics2D)
	 * @see Drawable#draw(Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		Shape s = createShape();
		if (isFilled()) {
			g.setPaint(getFillPaint());
			g.fill(s);
		}
		g.setPaint(getBorderPaint());
		g.setStroke(BORDER_STROKE);
		g.draw(s);
		drawHandles(g);
	}
	
	/**
	 * Draw the handles of this figure.
	 * 
	 * @param g a Java 2D graphics context
	 */
	protected void drawHandles(Graphics2D g) {
		if (selected) {
			for (Handle handle : handles) {
				handle.draw(g);
			}
		}
	}

	/**
	 * Returns a {@link Shape} object that represents this figure.
	 * 
	 * @return a shape for this figure
	 */
	protected abstract Shape createShape();

	/**
	 * Returns the {@link Paint} for the border of this figure.
	 * 
	 * @return the paint to border this figure
	 */
	protected Paint getBorderPaint() {
		return isSelected() ? Color.BLUE : Color.BLACK;
	}

	/**
	 * Returns the {@link Paint} to fill this figure.
	 * 
	 * @return the paint to fill this figure
	 */
	protected Paint getFillPaint() {
		return isSelected() ? SELECTED_FILL_COLOR : FILL_COLOR;
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			redrawCommand.execute(getBounds());
		}
	}
	
	@Override
	public boolean isFilled() {
		return filled;
	}
	
	@Override
	public void setFilled(boolean filled) {
		this.filled = filled;
		redrawCommand.execute(getBounds());
	}

	@Override
	public Handle handleAt(Point p) {
		for (Handle handle : handles) {
			if (handle.contains(p)) {
				return handle;
			}
		}
		return null;
	}
	
	/**
	 * Dispose of this figure by disposing of all of its handles
	 * 
	 * @see Disposable#dispose()
	 * @see Handle#dispose()
	 */
	@Override
	public void dispose() {
		for (Handle handle : handles) {
			handle.dispose();
		}
	}
	
	/**
	 * Modify the state of this figure using a {@link ModifyCommand
	 * ModifyCommand}. This method ensures that a redraw request is issued to
	 * cover both the old and the new state, and that the handles are notified. 
	 * 
	 * @param command 
	 */
	protected void modify(ModifyCommand command) {
		Rectangle r = getBounds();
		command.execute();
		r.add(getBounds());
		redrawCommand.execute(r);
		for (Handle handle : handles) {
			handle.locationChanged();
		}
	}
}
