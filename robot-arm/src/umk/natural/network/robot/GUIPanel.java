package umk.natural.network.robot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GUIPanel extends JPanel implements MouseListener {

    private Robot robot;
    private double[] klik;
    private double[] punkty;

    public GUIPanel(Robot r) {
        robot = r;
        punkty = new double[4];
        punkty[0] = 260;
        punkty[1] = 260;
        punkty[2] = 320;
        punkty[3] = 270;
        klik = new double[2];

        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(400, 500));
        this.setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        //rysujRobota(g2);
        g2.setPaint(Color.green);
        g2.fillOval(robot.getPunktStartowyRamienia()[0] - 3, robot.getPunktStartowyRamienia()[1] - 3, 6, 6);
        g2.setPaint(Color.blue);
        g2.fillOval((int) klik[0] - 3, (int) klik[1] - 3, 6, 6);

        g2.setPaint(Color.gray);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(robot.getPunktStartowyRamienia()[0], robot.getPunktStartowyRamienia()[1], (int) punkty[0], (int) punkty[1]);
        g2.drawLine((int) punkty[0], (int) punkty[1], (int) punkty[2], (int) punkty[3]);
        //rysujRaczke(g2, (int) punkty[2], (int) punkty[3]);
    }

    private void rysujRobota(Graphics2D g2) {
        try {
            g2.drawImage(ImageIO.read(new File("robot.jpg")), 40, 40, null);
        } catch (IOException ex) {
            System.out.println("Error while reading robot image!");
        }
    }

    private void rysujRaczke(Graphics2D g2, int x, int y) {
        try {
            g2.drawImage(ImageIO.read(new File("raczka.png")), x-20, y-20, null);
        } catch (IOException ex) {
            System.out.println("Error while reading hand image!");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        klik[0] = e.getX();
        klik[1] = e.getY();

        punkty = robot.sprawdzaj(klik);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
