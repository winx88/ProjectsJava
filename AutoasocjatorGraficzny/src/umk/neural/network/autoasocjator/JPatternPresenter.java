package umk.neural.network.autoasocjator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class JPatternPresenter extends JPanel {

	private static final long serialVersionUID = -3658482611182796756L;

	private PixelPattern pattern = new PixelPattern(10, 10);

	public JPatternPresenter() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int x = arg0.getX() * pattern.getWidth() / getWidth();
				int y = arg0.getY() * pattern.getHeight() / getHeight();
				if (x >= 0 && x < pattern.getWidth() && y >= 0 && y < pattern.getHeight()) {
					int index = y * pattern.getWidth() + x;
					Boolean pixel = pattern.getPixel(index);
					pattern.setPixel(index, !pixel);
					repaint();
				}
			}
		});

		// workaround for not repainting...
		new Thread() {
			public void run() {
				while (true) {
					try {
						JPatternPresenter.this.repaint();
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics gc = g.create();
		if (pattern != null) {
			for (int y = 0; y < pattern.getHeight(); y++) {
				for (int x = 0; x < pattern.getWidth(); x++) {
					if (pattern.getPixel(y * pattern.getWidth() + x)) {
						gc.setColor(Color.BLACK);
					} else {
						gc.setColor(Color.GRAY);
					}
					int left = (x * getWidth()) / pattern.getWidth();
					int top = (y * getHeight()) / pattern.getHeight();
					int width = ((x + 1) * getWidth()) / pattern.getWidth();
					int height = ((y + 1) * getHeight()) / pattern.getHeight();
					gc.fillRect(left, top, width, height);
					gc.setColor(Color.BLUE);
					gc.drawRect(left, top, width, height);
				}
			}
			gc.setColor(Color.BLUE);
			gc.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
			gc.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	public void setPattern(PixelPattern pattern) {
		this.pattern = pattern;
		repaint();
	}

	public PixelPattern getPattern() {
		return pattern;
	}
}
