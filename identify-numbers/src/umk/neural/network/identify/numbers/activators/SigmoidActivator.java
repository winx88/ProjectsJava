package umk.neural.network.identify.numbers.activators;

public class SigmoidActivator implements Activator {

	private double parameter;

	public double getParameter() {
		return parameter;
	}

	public void setParameter(double parameter) {
		this.parameter = parameter;
	}

	@Override
	public double eval(double in) {
		return 1 / (1 + Math.exp((-parameter) * in));
	}
}
