package umk.neural.network.identify.numbers.activators;

public class ThresholdActivator implements Activator {

	private double threshold = 0;

	/**
	 * Tworzy progową funkcję aktywującą z progiem równym 0 (funkcja znakowa)
	 */
	public ThresholdActivator() {
	}

	/**
	 * Tworzy progową funkcję aktywującą z progiem threshold
	 * 
	 * @param threshold
	 *            próg funkcji aktywującej
	 */
	public ThresholdActivator(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * Pobiera aktualną wartość progu funkcji aktywującej
	 * 
	 * @return
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * Zmienia próg funkcji aktywującej
	 * 
	 * @param threshold
	 *            nowa wartość progu
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public double eval(double in) {
		if (in < threshold) {
			return -1;
		} else {
			return 1;
		}
	}

}
