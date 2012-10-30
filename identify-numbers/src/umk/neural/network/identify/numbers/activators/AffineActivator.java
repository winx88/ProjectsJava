package umk.neural.network.identify.numbers.activators;

public class AffineActivator implements Activator {

	private double a, b;

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public void setA(double a) {
		this.a = a;
	}

	public void setB(double b) {
		this.b = b;
	}

	@Override
	public double eval(double in) {
		return a * in + b;
	}
}
