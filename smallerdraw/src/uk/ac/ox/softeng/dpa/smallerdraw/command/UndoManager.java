package uk.ac.ox.softeng.dpa.smallerdraw.command;

import java.util.Stack;


/**
 * An undo manager for undoable commands.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class UndoManager {
	
	private Stack<UndoableCommand> undoStack = new Stack<UndoableCommand>();
	private Stack<UndoableCommand> redoStack = new Stack<UndoableCommand>();

	public void doIt(UndoableCommand command) {
		command.doIt();
		undoStack.push(command);
	}

	public void undoIt() {
		if (undoStack.size() > 0) {
			UndoableCommand command = undoStack.pop();
			command.undoIt();
			redoStack.push(command);
		}
	}

	public void redoIt() {
		if (redoStack.size() > 0) {
			UndoableCommand command = redoStack.pop();
			command.doIt();
			undoStack.push(command);
		}
	}
}
