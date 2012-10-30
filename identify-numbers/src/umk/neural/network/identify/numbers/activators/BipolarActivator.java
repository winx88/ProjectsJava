package umk.neural.network.identify.numbers.activators;

public class BipolarActivator extends ThresholdActivator {

	public BipolarActivator() {
	}

	public BipolarActivator(double threshold) {
		setThreshold(threshold);
	}

	@Override
	public double eval(double in) {
		if (in < getThreshold()) {
			return 0;
		} else {
			return 1;
		}
	}
}
