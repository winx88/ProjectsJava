package umk.neural.network.activators;

public interface Activator {

	/**
	 * Oblicza wynik funkcję aktywującą
	 * 
	 * @param in
	 *            dana wejściowa
	 * @return wynik fukncji aktywującej
	 */
	double eval(double in);

}
