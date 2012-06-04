import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author winx
 * 
 */
public class Mian {

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static void WKTrans(String pathToFile, int loop) throws IOException {
		BufferedImage image = ImageIO.read(new File(pathToFile));
		int W = image.getWidth();
		int H = image.getHeight();
		BufferedImage tempImg = deepCopy(image);
		for (int k = 0; k < loop; k++) {
			for (int y = 0; y < W; y++)
				for (int x = 0; x < H; x++) {
					tempImg.setRGB((x + y) % W, (x + 2 * y) % W,
							image.getRGB(x, y));
				}
			ImageIO.write(tempImg, "jpg", new File("./WKTrans/" + k
					+ "trans.jpg"));
			image = deepCopy(tempImg);
		}
	}

	public static void PPTrans(String pathToFile, int loop) throws IOException {
		BufferedImage image = ImageIO.read(new File(pathToFile));
		int W = image.getWidth();
		int H = image.getHeight();
		BufferedImage tempImg = deepCopy(image);
		for (int k = 0; k < loop; k++) {
			for (int y = 0; y < W; y++)
				for (int x = 0; x < H; x++) {
					
					if (x < H / 2) {
						tempImg.setRGB(2 * x, (int)(y / 2), image.getRGB(x, y));
					} else {
						tempImg.setRGB(2 * x - H, (int)((y + H) / 2),
								image.getRGB(x, y));
					}
				}
			ImageIO.write(tempImg, "jpg", new File("./PPTrans/" + k
					+ "trans.jpg"));
			image = deepCopy(tempImg);
		}
	}

	/**
	 * :*
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("GoGoGo......");
		try {
			PPTrans("./1.jpg", 200);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end...<jupi>");

	}

}
