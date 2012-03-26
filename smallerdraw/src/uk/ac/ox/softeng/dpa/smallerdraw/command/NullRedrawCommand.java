package uk.ac.ox.softeng.dpa.smallerdraw.command;

import java.awt.Rectangle;

/**
 * An example of the both the Null Object and Singleton patterns. A
 * {@code NullRedrawCommand} is a command that does nothing, and as such,
 * there is no need for more than one instance.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class NullRedrawCommand implements RedrawCommand {

	private static final NullRedrawCommand instance = new NullRedrawCommand();

	private NullRedrawCommand() {
	}

	/**
	 * Returns the single instance of {@code NullRedrawCommand}.
	 * 
	 * @return the singleton
	 */
	public static NullRedrawCommand getInstance() {
		return instance;
	}

	@Override
	public void execute(Rectangle clip) {
	}
}
