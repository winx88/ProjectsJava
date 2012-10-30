package umk.neural.network.identify.numbers.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class Patterns extends AbstractListModel implements Serializable {

	private static final long serialVersionUID = 7728815813876348687L;

	private List<PixelPattern<Boolean>> patterns = new ArrayList<PixelPattern<Boolean>>();

	@Override
	public Object getElementAt(int index) {
		return patterns.get(index);
	}

	@Override
	public int getSize() {
		return patterns.size();
	}

	public void addPattern(PixelPattern<Boolean> pattern) {
		patterns.add(pattern);
		fireIntervalAdded(this, 0, patterns.size());
	}

	public void delPattern(int index) {
		patterns.remove(index);
		fireIntervalRemoved(this, 0, patterns.size());
	}

	public boolean hasPattern(PixelPattern<Boolean> pattern) {
		return patterns.contains(pattern);
	}

	public List<PixelPattern<Boolean>> getPatterns() {
		return patterns;
	}

	public PixelPattern<Boolean> get(int index) {
		return patterns.get(index);
	}

}
