package uk.ac.ox.softeng.dpa.smallerdraw.command;

/**
 * An example of the Command pattern, {@code ModifyCommand} is for encapsulating
 * modifications to a figure.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 * @see uk.ac.ox.softeng.dpa.smallerdraw.figures.AbstractFigure#modify(ModifyCommand)
 */
public interface ModifyCommand {
	
	/**
	 * Execute the command.
	 */
	void execute();
}