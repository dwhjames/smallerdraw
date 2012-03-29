package uk.ac.ox.softeng.dpa.smallerdraw.figures;

import java.awt.Dimension;
import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.command.ModifyCommand;

/**
 * An abstract figure for rectangular shapes.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public abstract class AbstractRectangularFigure extends AbstractFigure {

	public static final int MINWIDTH = 20, MINHEIGHT = 20;
	
	private Rectangle bounds;
	
	public AbstractRectangularFigure() {
		this.bounds = new Rectangle();
	}
	
	public AbstractRectangularFigure(AbstractRectangularFigure that) {
		super(that);
		this.bounds = new Rectangle(that.bounds);
	}
	
	public abstract AbstractRectangularFigure clone();
	
	@Override
	public Rectangle getBounds() {
		return this.bounds.getBounds();
	}
	
	/**
	 * Set the bounds of this figure.
	 * 
	 * @param r the bounds
	 */
	public void setRectangle(Rectangle r) {
		this.modify(new SetBounds(r));
	}
	
	@Override
	public void move(Dimension offset) {
		Rectangle r = new Rectangle(bounds);
		r.translate(offset.width, offset.height);
		this.modify(new SetBounds(r));
	}

	private final class SetBounds implements ModifyCommand {
		private final Rectangle r;

		private SetBounds(Rectangle r) {
			this.r = new Rectangle(r);
		}

		@Override
		public void execute() {
			bounds.setBounds(r);
		}
	}
}
