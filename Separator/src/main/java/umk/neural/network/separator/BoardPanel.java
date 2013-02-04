/**
 * 
 */
package umk.neural.network.separator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

/**
 * @author winx
 * 
 */
public class BoardPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics2D g2;

	/**
	 * @return the g2
	 */
	public Graphics2D getG2() {
		return g2;
	}

	/**
	 * @param g2
	 *            the g2 to set
	 */
	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}

	private List<Bpoint> listOfPoint = new ArrayList<Bpoint>();

	public BoardPanel() {

		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(400, 420));
		this.setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;

		for (Bpoint bp : listOfPoint) {
			if (bp.getButton() == -1) {
				g2.setPaint(Color.red);
				g2.fillOval(bp.getP().x - 3, bp.getP().y - 3, 8, 8);
			}
			if (bp.getButton() == 1) {
				g2.setPaint(Color.green);
				g2.fillOval(bp.getP().x - 3, bp.getP().y - 3, 8, 8);
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int button = -1;
		if (e.getButton() == MouseEvent.BUTTON1) {
			button = -1;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			button = 1;
		}
		Bpoint bp = new Bpoint();
		bp.setButton(button);
		bp.setP(e.getPoint());
		listOfPoint.add(bp);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the listOfPoint
	 */
	public List<Bpoint> getListOfPoint() {
		return listOfPoint;
	}

	/**
	 * @param listOfPoint
	 *            the listOfPoint to set
	 */
	public void setListOfPoint(List<Bpoint> listOfPoint) {
		this.listOfPoint = listOfPoint;
	}

}
