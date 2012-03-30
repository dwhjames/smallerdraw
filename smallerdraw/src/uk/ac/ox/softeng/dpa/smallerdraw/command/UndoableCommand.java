package uk.ac.ox.softeng.dpa.smallerdraw.command;

/**
 * A command that can be done and undone.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public interface UndoableCommand {
	void doIt();
	void undoIt();
}
