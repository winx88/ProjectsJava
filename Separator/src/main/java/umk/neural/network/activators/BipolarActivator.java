package umk.neural.network.activators;

public class BipolarActivator extends ThresholdActivator {

	public BipolarActivator() {
	}

	public BipolarActivator(double threshold) {
		setThreshold(threshold);
	}

	@Override
	public double eval(double in) {
		if (in < getThreshold()) {
			// bad 0
			return -1;
		} else {
			return 1;
		}
	}
}
