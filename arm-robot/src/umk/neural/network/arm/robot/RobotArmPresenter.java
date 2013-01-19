package umk.neural.network.arm.robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class RobotArmPresenter extends JComponent {

	private static final long serialVersionUID = 1L;

	private Point point = new Point(100, 100);
	private Point angles = new Point(120, 120);

	public RobotArmPresenter() {
		setSize(300, 300);
		setMinimumSize(new Dimension(300, 300));
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D gc = (Graphics2D) g.create();

		gc.setColor(getBackground());
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.setColor(getForeground());

		if (angles != null) {
			Point points[] = RobotArm.calc(angles);
			drawLine(gc, RobotArm.ATTACH_POINT.x, RobotArm.ATTACH_POINT.y, points[0].x, points[0].y);
			drawLine(gc, points[0].x, points[0].y, points[1].x, points[1].y);

			drawPoint(gc, RobotArm.ATTACH_POINT.x, RobotArm.ATTACH_POINT.y, "P0");
			drawPoint(gc, points[0].x, points[0].y, "P1");
			drawPoint(gc, points[1].x+6, points[1].y-4, "P2");
		}

		if (point != null) {
			gc.setColor(Color.RED);
			drawPoint(gc, point, "Pt");
		}
	}

	protected void drawLine(Graphics gc, int x1, int y1, int x2, int y2) {
		gc.drawLine(x1, getHeight() - y1, x2, getHeight() - y2);
	}

	protected void drawPoint(Graphics gc, int x, int y, String name) {
		drawPoint(gc, new Point(x, y), name);
	}

	protected void drawPoint(Graphics gc, Point point, String name) {
		gc.drawLine(point.x - 1, getHeight() - point.y - 1, point.x + 1, getHeight() - point.y + 1);
		gc.drawLine(point.x - 1, getHeight() - point.y + 1, point.x + 1, getHeight() - point.y - 1);
		if (name != null) {
			gc.drawString(name + "(" + point.x + "," + point.y + ")", point.x, getHeight() - point.y);
		}
	}

	private MouseAdapter mouseHandler = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			/**
			 * wybieramy punkt koncowy obliczamy kat
			 */
			point = e.getPoint();
			point.y = getHeight() - point.y;
			//angles = RobotArm.calcAngles(point);
			angles = RobotArm.net.eval(point);
			repaint();
		}
		
		
	};

	public void setPoint(Point point) {
		this.point = point;
		repaint();
	}

	public void setAngles(Point angles) {
		this.angles = angles;
		repaint();
	}

	public Point getPoint() {
		return point;
	}

	public Point getAngles() {
		return angles;
	}
}
