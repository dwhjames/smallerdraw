package uk.ac.ox.softeng.dpa.smallerdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import uk.ac.ox.softeng.dpa.smallerdraw.command.RedrawCommand;
import uk.ac.ox.softeng.dpa.smallerdraw.command.NullRedrawCommand;

/**
 * An implementation of the {@link SelectableModel} interface. A picture is a
 * collection of figures, which can be drawn and selected.
 * 
 * @author Daniel W.H. James
 * @version DPA March 2012
 */
public class Picture implements SelectableModel, Drawable {
	
	private static final Color SELECTION_FILL_COLOR = new Color(0xF5F5F5);
	private static final Stroke SELECTION_STROKE = new BasicStroke(2);
	
	private List<Figure> figures = new ArrayList<Figure>();
	private List<View> views = new ArrayList<View>();
	
	private Rectangle selection = new Rectangle();
	
	private final RedrawCommand nullCommand = NullRedrawCommand.getInstance();
	private final RedrawCommand redrawCommand = new RedrawModelCommand();
	
	@Override
	public void add(Figure figure) {
		figures.add(figure);
		figure.setRedrawCommand(redrawCommand);
		redrawCommand.execute(figure.getBounds());
	}
	
	public void add(View canvas) {
		views.add(canvas);
	}
	
	@Override
	public void remove(Figure figure) {
		figures.remove(figure);
		figure.setRedrawCommand(nullCommand);
		redrawCommand.execute(figure.getBounds());
	}
	
	@Override
	public void delete(Figure figure) {
		figure.dispose();
		this.remove(figure);
	}
	
	@Override
	public Iterable<Figure> figures() {
		return figures;
	}
	
	@Override
	public Iterable<Figure> figuresReversed() {
		return new Iterable<Figure>() {
			@Override
			public Iterator<Figure> iterator() {
				return new FiguresReversedIterator();
			}
		};
	}
	
	@Override
	public Iterable<Figure> selectedFigures() {
		return new Iterable<Figure>() {
			@Override
			public Iterator<Figure> iterator() {
				return new SelectedFiguresIterator();
			}
		};
	}
	
	@Override
	public void setSelection(Rectangle rectangle) {
		Rectangle r = selection;
		selection = rectangle;
		if (!selection.isEmpty()) {
			for (Figure figure : figures) {
				figure.setSelected(selection.contains(figure.getBounds()));
			}
		}
		r.add(rectangle);
		redraw(r);
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (!selection.isEmpty() && g.getClip().intersects(selection)) {
			g.setPaint(SELECTION_FILL_COLOR);
			g.fill(selection);
		}
		for (Figure figure : figures) {
			if (g.getClip().intersects(figure.getBounds())) {
				figure.draw(g);
			}
		}
		if (!selection.isEmpty() && g.getClip().intersects(selection)) {
			// g.setStroke()
			g.setPaint(Color.RED);
			g.setStroke(SELECTION_STROKE);
			g.draw(selection);
		}
	}
	
	private void redraw(Rectangle clip) {
		clip.grow(Handle.WIDTH + 2, Handle.HEIGHT + 2);
		for (View view : views) {
			view.redraw(clip);
		}
	}
	
	private final class RedrawModelCommand implements RedrawCommand {
		@Override
		public void execute(Rectangle clip) {
			redraw(clip);
		}
	}

	private final class FiguresReversedIterator implements Iterator<Figure> {
		int i = figures.size();
	
		@Override
		public boolean hasNext() {
			return i > 0;
		}

		@Override
		public Figure next() {
			if (hasNext()) {
				return figures.get(--i);
			} else {
				throw new NoSuchElementException();
			}
		}
	
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final class SelectedFiguresIterator implements Iterator<Figure> {
		private boolean hasNext;
		private Figure next;
		private final Iterator<Figure> iter;
		{
			iter = figures.iterator();
			findNext();
		}
	
		@Override
		public boolean hasNext() {
			return hasNext;
		}
	
		@Override
		public Figure next() {
			if (hasNext) {
				Figure result = next;
				findNext();
				return result;
			} else {
				throw new NoSuchElementException();
			}
		}
	
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	
		private void findNext() {
			hasNext = false;
			while (iter.hasNext() && !hasNext) {
				Figure f = iter.next();
				if (f.isSelected()) {
					next = f;
					hasNext = true;
				}
			}
		}
	}
}
