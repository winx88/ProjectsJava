package umk.neural.network.autoasocjator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class Patterns extends AbstractListModel implements Serializable {

	private static final long serialVersionUID = 7728815813876348687L;

	private List<PixelPattern> patterns = new ArrayList<PixelPattern>();

	@Override
	public Object getElementAt(int index) {
		return patterns.get(index);
	}

	@Override
	public int getSize() {
		return patterns.size();
	}

	public void addPattern(PixelPattern pattern) {
		patterns.add(pattern);
		fireIntervalAdded(this, 0, patterns.size());
	}

	public void delPattern(int index) {
		patterns.remove(index);
		fireIntervalRemoved(this, 0, patterns.size());
	}

	public boolean hasPattern(PixelPattern pattern) {
		return patterns.contains(pattern);
	}

	public List<PixelPattern> getPatterns() {
		return patterns;
	}

	public PixelPattern get(int index) {
		return patterns.get(index);
	}

}
