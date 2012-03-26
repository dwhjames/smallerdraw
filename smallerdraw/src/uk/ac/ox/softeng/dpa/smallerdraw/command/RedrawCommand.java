package uk.ac.ox.softeng.dpa.smallerdraw.command;

import java.awt.Rectangle;

import uk.ac.ox.softeng.dpa.smallerdraw.Figure;

/**
 * An example of the Command pattern, {@code RedrawCommand} is for encapsulating
 * redraw requests.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see Figure#setRedrawCommand(RedrawCommand)
 */
public interface RedrawCommand {
	
	/**
	 * Execute the command with a clipping area.
	 * 
	 * @param clip the clipping area
	 */
	void execute(Rectangle clip);
}
