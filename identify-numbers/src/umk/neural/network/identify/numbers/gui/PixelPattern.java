package umk.neural.network.identify.numbers.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PixelPattern<T> implements Serializable {

	private static final long serialVersionUID = 1839697084179602167L;

	private int width;

	private int height;

	private String name;

	private List<T> pixels;

	public PixelPattern(int width, int height) {
		this.width = width;
		this.height = height;
		allocPixels();
	}

	private void allocPixels() {
		pixels = new ArrayList<T>(width * height);
		for (int i = 0; i < width * height; i++) {
			pixels.add((T) new Boolean(false));
		}
	}
	
	PixelPattern<T> copy(){
		PixelPattern<Boolean> copy = new PixelPattern<Boolean>(width, height);
		List<Boolean> pix = (List<Boolean>)pixels;
		for(int i = 0; i < pix.size(); i++){
			copy.getPixels().set(i, pix.get(i));
		}
		return (PixelPattern<T>)copy;
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

	public List<T> getPixels() {
		return pixels;
	}

	public void setPixels(List<T> pixels) {
		this.pixels = pixels;
	}

	public void setPixel(int index, T pixel) {
		pixels.set(index, pixel);
	}

	public T getPixel(int index) {
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
