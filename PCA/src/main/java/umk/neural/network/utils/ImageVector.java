package umk.neural.network.utils;

import java.util.Random;

public class ImageVector {

	double[] data;
	public int Size;

	// Konstruktory

	public ImageVector(double[] dane, int size) {
		Size = size;
		data = new double[Size];

		for (int i = 0; i < Size; i++)
			data[i] = dane[i];
	}

	public ImageVector(int size) {
		data = new double[size];
		Size = size;
		for (int i = 0; i < size; i++)
			data[i] = 0;

	}

	public ImageVector(ImageVector v) {
		Size = v.Size;
		data = new double[Size];

		for (int i = 0; i < v.Size; i++)
			data[i] = v.data[i];
	}

	public ImageVector() {
	}

	// Metody

	public double GetValue(int index) {
		return data[index];
	}

	public void Normalize() {
		double length = Length();

		for (int i = 0; i < Size; i++)
			data[i] = data[i] / length;
	}

	public double Min() {
		if (Size > 0) {
			double min = data[0];
			for (int i = 0; i < Size; i++) {
				if (min > data[i]) {
					min = data[i];
				}
			}
			return min;
		} else
			return 0;
	}

	public double Max() {
		if (Size > 0) {
			double max = data[0];
			for (int i = 0; i < Size; i++) {
				if (max < data[i]) {
					max = data[i];
				}
			}
			return max;
		} else
			return 0;
	}

	public void Randomize() {
		Random rand = new Random();
		for (int i = 0; i < Size; i++) {
			// data[i] = rand.Next() % 100;
			data[i] = rand.nextDouble();
		}
	}

	public double Length() {
		double sum = 0;
		for (int i = 0; i < Size; i++) {
			sum += data[i] * data[i];
		}
		return Math.sqrt(sum);
	}

	public static double Mnozenie(ImageVector lVect, ImageVector rVect) {
		double answer = 0;
		for (int i = 0; i < lVect.Size; i++) {
			answer += lVect.GetValue(i) * rVect.GetValue(i);
		}
		return answer;
	}

	public static ImageVector Dodawanie(ImageVector lVect, ImageVector rVect) {
		int dims = lVect.Size;
		ImageVector imgVect = new ImageVector(dims);

		for (int i = 0; i < lVect.Size; i++) {
			double value = lVect.data[i] + rVect.data[i];
			imgVect.data[i] = value;
		}
		return imgVect;
	}

	public static ImageVector Odejmowanie(ImageVector lVect, ImageVector rVect) {
		ImageVector imgVect = new ImageVector(lVect.Size);

		for (int i = 0; i < imgVect.Size; i++) {
			double wartosc = lVect.data[i] - rVect.data[i];
			imgVect.data[i] = wartosc;
		}
		return imgVect;
	}

	public static ImageVector Mnozenie(double scalar, ImageVector vect) {
		ImageVector imgVect = new ImageVector(vect.Size);
		for (int i = 0; i < imgVect.Size; i++) {
			imgVect.data[i] = scalar * vect.data[i];
		}
		return imgVect;
	}
}
