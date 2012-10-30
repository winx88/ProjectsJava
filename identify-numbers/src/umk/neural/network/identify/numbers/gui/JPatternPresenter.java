package umk.neural.network.identify.numbers.gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class JPatternPresenter extends JPanel {

	private static final long serialVersionUID = -3738976367513112155L;

	private PixelPattern<Boolean> pattern;

	public JPatternPresenter(PixelPattern<Boolean> pixelPattern, ActionListener al) {
		this.pattern = pixelPattern;
		initUI(al);
	}

	private void initUI(ActionListener al) {
		this.setLayout(new GridLayout(pattern.getHeight(), pattern.getWidth()));
		for (int i = 0; i < pattern.getWidth() * pattern.getHeight(); i++) {
			Boolean selected = pattern.getPixel(i);
			JPixelPatternButton pixel = new JPixelPatternButton(i, pattern, al);
			pixel.setSelected(selected);
			add(pixel);
		}
	}

	public PixelPattern<Boolean> getPattern() {
		return pattern;
	}

}
