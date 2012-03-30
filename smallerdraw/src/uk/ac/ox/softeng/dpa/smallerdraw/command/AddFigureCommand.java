package uk.ac.ox.softeng.dpa.smallerdraw.command;

import uk.ac.ox.softeng.dpa.smallerdraw.Figure;

/**
 * A command for the operation of adding a figure.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public interface AddFigureCommand {
	void execute(Figure figure);
}
