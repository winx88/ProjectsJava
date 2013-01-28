package umk.neural.network.pca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umk.neural.network.utils.ImageVector;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 44202514566339254L;

	int NumOfImages = 20;
	int NumOfEigenVectors = 6;
	List<BufferedImage> BitmapImageList;
	List<BufferedImage> ResultsList;
	ArrayList<double[]> DataTable;
	ArrayList<ImageVector> vectors;
	ArrayList<ImageVector> vectWlasne;
	int DataSize;

	public static void main(String[] args) {
		new MainWindow();
	}

	public MainWindow() {
		super("PCA");
		pack();
		setSize(1050, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		BitmapImageList = new ArrayList<BufferedImage>();
		ResultsList = new ArrayList<BufferedImage>();
		DataTable = new ArrayList<double[]>();
		vectors = new ArrayList<ImageVector>();
		vectWlasne = new ArrayList<ImageVector>();
		buildUI();
		setVisible(true);
	}

	private void buildUI() {
		setLayout(new BorderLayout());
		JPanel presentersPanel = new JPanel();
		presentersPanel.setLayout(new BoxLayout(presentersPanel,
				BoxLayout.X_AXIS));

		loadImageFromFile();
		DataSize = BitmapImageList.get(0).getHeight()
				* BitmapImageList.get(0).getWidth();

		getPixelData();
		createVectors();
		reduceDim();
		drawVector();

		JPanel jPanel = new JPanel();

		for (BufferedImage b : ResultsList) {
			JLabel jLabel = new JLabel(new ImageIcon(b));
			jPanel.add(jLabel);
		}
		this.add(jPanel);

	}

	private void loadImageFromFile() {
		for (int i = 1; i <= NumOfImages; i++) {
			BufferedImage img;
			try {
				img = ImageIO
						.read(new File("src/main/resources/" + i + ".png"));
				BitmapImageList.add(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getPixelData() {

		for (int vectId = 0; vectId < BitmapImageList.size(); vectId++) {
			double[] data = new double[DataSize];
			int counter = 0;

			for (int i = 0; i < BitmapImageList.get(vectId).getWidth(); i++) {
				for (int j = 0; j < BitmapImageList.get(vectId).getHeight(); j++) {
					data[counter] = BitmapImageList.get(vectId).getRGB(i, j);
					counter++;
				}
			}

			DataTable.add(data);
		}
	}

	private void createVectors() {
		for (int i = 0; i < NumOfImages; i++)
			vectors.add(new ImageVector(DataTable.get(i), DataSize));
	}

	private void reduceDim() {
		vectWlasne.clear();
		for (int i = 0; i < NumOfEigenVectors; i++) {

			ImageVector imgVect = algorithmOj(vectors);

			vectWlasne.add(imgVect);

			for (int j = 0; j < vectors.size(); j++) {
				ImageVector xl = vectors.get(j);
				ImageVector wl = vectWlasne.get(vectWlasne.size() - 1);

				double temp1 = ImageVector.Mnozenie(wl, xl);
				double temp2 = ImageVector.Mnozenie(wl, wl);
				double tmp = temp1 / temp2;

				ImageVector temp3 = ImageVector.Mnozenie(tmp, wl);
				ImageVector temp4 = ImageVector.Odejmowanie(xl, temp3);
				vectors.set(j, temp4);
			}
		}
	}

	private void drawVector() {
		for (int i = 0; i < vectWlasne.size(); i++) {

			BufferedImage bmp = new BufferedImage(BitmapImageList.get(0)
					.getWidth(), BitmapImageList.get(0).getHeight(),
					BufferedImage.TYPE_INT_RGB);

			double min = vectWlasne.get(i).Min();
			double max = vectWlasne.get(i).Max();

			for (int y = 0; y < bmp.getHeight(); y++) {
				for (int x = 0; x < bmp.getWidth(); x++) {
					int position = x * bmp.getHeight() + y;

					double e = vectWlasne.get(i).GetValue(position);
					double value = (e - min) * (255 / (max - min));
					Color c = new Color((int)value,(int)value,(int)value);
					bmp.setRGB(x, y, c.getRGB());
				}
			}
			ResultsList.add(bmp);

		}
	}

	ImageVector algorithmOj(List<ImageVector> vects) {
		Random rand = new Random();

		for (int i = 0; i < vects.size(); i++) {
			vects.get(i).Normalize();
		}

		ImageVector weightVect = new ImageVector(vects.get(0).Size);
		weightVect.Randomize();
		weightVect.Normalize();

		double eta = 0.5;

		for (int i = 0; i < 50; i++) {

			ImageVector randVect = vects.get(rand.nextInt(21) % vects.size());
			double y = ImageVector.Mnozenie(weightVect, randVect);

			ImageVector temp1 = ImageVector.Mnozenie(y, weightVect);
			ImageVector temp2 = ImageVector.Odejmowanie(randVect, temp1);
			ImageVector temp3 = ImageVector.Mnozenie(eta * y, temp2);
			ImageVector temp4 = ImageVector.Dodawanie(weightVect, temp3);

			weightVect = temp4;
		}

		return weightVect;
	}
}
