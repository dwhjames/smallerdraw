package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import uk.ac.ox.softeng.dpa.smallerdraw.figures.OvalFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.figures.RectangleFigure;
import uk.ac.ox.softeng.dpa.smallerdraw.tools.ConnectorTool;
import uk.ac.ox.softeng.dpa.smallerdraw.tools.LineTool;
import uk.ac.ox.softeng.dpa.smallerdraw.tools.PolygonTool;
import uk.ac.ox.softeng.dpa.smallerdraw.tools.RectangularTool;
import uk.ac.ox.softeng.dpa.smallerdraw.tools.SelectionTool;

/**
 * Setup the Java Swing GUI
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class SmallerDraw {
	
	static {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SmallerDraw");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("SmallerDraw");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addComponentsToPane(frame.getContentPane());
		frame.setJMenuBar(menuBar);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private static final Picture picture = new Picture();
	
	private static final JMenuBar menuBar = new JMenuBar();
	private static final JPopupMenu popupMenu = new JPopupMenu();
	static {
		JMenu menu;
		Action action;
		// begin File menu
		menu = new JMenu("File");
		menuBar.add(menu);
		
		menu.add(new JMenuItem("Open"));
		menu.add(new JMenuItem("Save"));
		// end File menu
		
		// begin Edit menu
		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		action = new SelectMenuAction(picture);
		menu.add(new JMenuItem(action));
		popupMenu.add(new JMenuItem(action));
		
		action = new SelectNoneMenuAction(picture);
		menu.add(new JMenuItem(action));
		popupMenu.add(new JMenuItem(action));
		
		menu.addSeparator();
		popupMenu.addSeparator();
		
		action = new WiredMenuAction(picture);
		menu.add(new JMenuItem(action));
		popupMenu.add(new JMenuItem(action));
		
		action = new FilledMenuAction(picture);
		menu.add(new JMenuItem(action));
		popupMenu.add(new JMenuItem(action));
		// end Edit menu
	}
	
	private static final Canvas canvas = new Canvas(picture);
	private static final SelectionTool selectionTool = new SelectionTool(picture);
	static {
		picture.add(canvas);
		canvas.setActiveTool(selectionTool);
		canvas.addMouseListener(new PopupListener());
	}
	
	private static final Action[] actions =
	{
		new ToolAction("Select",
				       selectionTool),
		new ToolAction("Connect",
				       new ConnectorTool(picture)),
		new ToolAction("Line",
				       new LineTool(picture)),
		new ToolAction("Rectangle",
				       new RectangularTool<RectangleFigure>(picture, RectangleFigure.class)),
		new ToolAction("Oval",
				       new RectangularTool<OvalFigure>(picture, OvalFigure.class)),
		new ToolAction("Triangle",
				       new PolygonTool(picture, 3)),
		new ToolAction("Pentagon",
				       new PolygonTool(picture, 5)),
		new ToolAction("Hexagon",
				       new PolygonTool(picture, 6))
	};
	
	private static void addComponentsToPane(Container pane) {
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.PAGE_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tools");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		toolPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder,
				            BorderFactory.createCompoundBorder(titledBorder, emptyBorder)));
		
		for (Action action : actions) {
			JButton button = new JButton(action);
			button.putClientProperty("JButton.buttonType", "roundRect");
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			toolPanel.add(button);
		}
		toolPanel.add(Box.createVerticalGlue());
		
		pane.add(toolPanel, BorderLayout.LINE_START);
		
		canvas.setMinimumSize(new Dimension(320, 240));
		canvas.setPreferredSize(new Dimension(640, 480));
		pane.add(canvas, BorderLayout.CENTER);
	}
	
	/**
	 * The action for the 'Select All' menu item.
	 */
	@SuppressWarnings("serial")
	private static final class SelectMenuAction extends AbstractAction {
		private final SelectableModel model;

		private SelectMenuAction(SelectableModel model) {
			super("Select All");
			this.model = model;
			putValue(ACCELERATOR_KEY,
					 KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Figure figure : model.figures()) {
				figure.setSelected(true);
			}
		}
	}

	/**
	 * The action for the 'Select None' menu item.
	 */
	@SuppressWarnings("serial")
	private static final class SelectNoneMenuAction extends AbstractAction {
		private final SelectableModel model;

		private SelectNoneMenuAction(SelectableModel model) {
			super("Select None");
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Figure figure : model.figures()) {
				figure.setSelected(false);
			}
		}
	}

	/**
	 * The action for the 'Wired' menu item.
	 */
	@SuppressWarnings("serial")
	private static final class WiredMenuAction extends AbstractAction {
		private final SelectableModel model;

		private WiredMenuAction(SelectableModel model) {
			super("Wired");
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Figure figure : model.selectedFigures()) {
				figure.setFilled(false);
			}
		}
	}
	
	/**
	 * The action for the 'Filled' menu item.
	 */
	@SuppressWarnings("serial")
	private static final class FilledMenuAction extends AbstractAction {
		private final SelectableModel model;

		private FilledMenuAction(SelectableModel model) {
			super("Filled");
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Figure figure : model.selectedFigures()) {
				figure.setFilled(true);
			}
		}
	}

	/**
	 * The action for tool buttons.
	 */
	@SuppressWarnings("serial")
	private static final class ToolAction extends AbstractAction {
		private Tool tool;
		
		private ToolAction(String name, Tool tool) {
			super(name);
			this.tool = tool;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			canvas.setActiveTool(tool);
		}
	}
	
	/**
	 * The mouse event listener to trigger the popup menu 
	 */
	private static final class PopupListener extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}
		
		private void maybeShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	            popupMenu.show(e.getComponent(), e.getX(), e.getY());
	        }
	    }
		
	}
}
