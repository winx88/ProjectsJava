package umk.neural.network.autoasocjator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PixelPattern implements Serializable {

	private static final long serialVersionUID = 1839697084179602167L;

	private int width;

	private int height;

	private String name;

	private List<Boolean> pixels;

	public PixelPattern(int width, int height) {
		this.width = width;
		this.height = height;
		allocPixels();
	}

	private void allocPixels() {
		pixels = new ArrayList<Boolean>(width * height);
		for (int i = 0; i < width * height; i++) {
			pixels.add(new Boolean(false));
		}
	}

	PixelPattern copy() {
		PixelPattern copy = new PixelPattern(width, height);
		List<Boolean> pix = pixels;
		for (int i = 0; i < pix.size(); i++) {
			copy.getPixels().set(i, pix.get(i));
		}
		return copy;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		allocPixels();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		allocPixels();
	}

	public List<Boolean> getPixels() {
		return pixels;
	}

	public void setPixels(List<Boolean> pixels) {
		this.pixels = pixels;
	}

	public void setPixel(int index, Boolean pixel) {
		pixels.set(index, pixel);
	}

	public Boolean getPixel(int index) {
		return pixels.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
