package umk.neural.network.hopfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author winx
 */
public class GUIPanel extends JPanel implements MouseListener {

    private int fieldWidth;
    private int fieldHeight;
    private int scale = 30;
    private Graphics2D graphic;
    private boolean[][] matrix;

    public GUIPanel(int w, int h, boolean mouseListener) {
        fieldWidth = w;
        fieldHeight = h;

        matrix = new boolean[fieldHeight][fieldWidth];
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                matrix[i][j] = false;
            }
        }

        if (mouseListener) {
            this.addMouseListener(this);
        }
        this.setPreferredSize(new Dimension(fieldWidth * scale + 1, fieldHeight * scale + 1));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic = (Graphics2D) g;

        graphic.setPaint(Color.white);
        graphic.fillRect(0, 0, fieldWidth * scale, fieldHeight * scale);

        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (matrix[i][j]) {
                    graphic.setPaint(Color.black);
                    graphic.fillRect(j * scale, i * scale, scale, scale);
                } else {
                    graphic.setPaint(Color.white);
                    graphic.fillRect(j * scale, i * scale, scale, scale);
                }
            }
        }

        graphic.setPaint(Color.blue);
        graphic.drawRect(0, 0, fieldWidth * scale, fieldHeight * scale);
        for (int i = 1; i < fieldWidth; i++) {
            graphic.drawLine(scale * i, 0, scale * i, fieldHeight * scale);
        }
        for (int i = 1; i < fieldHeight; i++) {
            graphic.drawLine(0, scale * i, fieldWidth * scale, scale * i);
        }
    }

    public int[] whichFieldClicked(int x, int y) {
        int[] p = new int[2];

        for (int i = 0; i < fieldWidth; i++) {
            if (x >= (i * scale) && x <= (i + 1) * scale) {
                p[0] = i;
                break;
            }
        }
        for (int i = 0; i < fieldHeight; i++) {
            if (y >= (i * scale) && y <= (i + 1) * scale) {
                p[1] = i;
                break;
            }
        }
        return p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int[] f;
        if ((x <= (scale * fieldWidth)) && (x >= 0) && (y >= 0) && (y <= (scale * fieldHeight))) {
            f = whichFieldClicked(x, y);
            matrix[f[1]][f[0]] = !matrix[f[1]][f[0]];
        }
        this.repaint();
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

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(boolean[][] b) {
        this.matrix = b;
    }
}
