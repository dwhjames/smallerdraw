package uk.ac.ox.softeng.dpa.smallerdraw.tools;

import java.awt.Point;

import uk.ac.ox.softeng.dpa.smallerdraw.Canvas;
import uk.ac.ox.softeng.dpa.smallerdraw.Tool;
import uk.ac.ox.softeng.dpa.smallerdraw.Model;

/**
 * The Null Object pattern for {@linkplain Tool Tools}.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see Canvas
 */
public class EmptyTool implements Tool {
	
	protected Model model;
	
	/**
	 * The default constructor.
	 */
	public EmptyTool() { }
	
	/**
	 * A constructor for subclasses.
	 * 
	 * @param model the model
	 */
	public EmptyTool(Model model) {
		this.model = model;
	}
	
	@Override
	public void onMouseDown(Point pos) { }
	
	@Override
	public void onMouseDrag(Point pos) { }
	
	@Override
	public void onMouseMove(Point pos) { }
	
	@Override
	public void onMouseUp(Point pos) { }
}
