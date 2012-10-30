package umk.neural.network.identify.numbers.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

public class JPixelPatternButton extends JToggleButton {

	private static final long serialVersionUID = 6763870824844562153L;

	private final PixelPattern<Boolean> pattern;

	private final int index;

	public JPixelPatternButton(int index, PixelPattern<Boolean> pattern, ActionListener al) {
		this.pattern = pattern;
		this.index = index;
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPixelPatternButton.this.pattern.setPixel(JPixelPatternButton.this.index, isSelected());
			}
		});
		addActionListener(al);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics graphics = g.create();
		if (isSelected()) {
			graphics.setColor(Color.BLACK);
		} else {
			graphics.setColor(Color.WHITE);
		}
		graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
}
