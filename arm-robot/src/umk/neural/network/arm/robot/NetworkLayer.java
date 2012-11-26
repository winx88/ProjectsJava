package umk.neural.network.arm.robot;

import java.util.Random;

/**
 * Pojedyńcza warstwa sieci
 * @author winx
 * @date 23-11-2012
 */

public class NetworkLayer {

	private NetworkLayer prev = null;

	private NetworkLayer next = null;

	private int perceptronCount = 0;

	private double weights[][];

	private double values[];

	private double errors[];

	/**
	 * Ilosć perceptronów przy budowie warstwy.
	 * @param perceptronCount
	 */
	public NetworkLayer(int perceptronCount) {
		this.perceptronCount = perceptronCount;
		this.weights = new double[perceptronCount][];
		this.values = new double[perceptronCount];
		this.errors = new double[perceptronCount];
	}

	public void setPrev(NetworkLayer prev) {
		this.prev = prev;
		for (int i = 0; i < perceptronCount; i++) {
			weights[i] = initRandomWeights(prev.perceptronCount);
		}
		prev.next = this;
	}

	private double[] initRandomWeights(int count) {
		Random r = new Random();
		double weights[] = new double[count];
		for (int i = 0; i < count; i++) {
			weights[i] = r.nextDouble() - r.nextDouble();
		}
		return weights;
	}

	public NetworkLayer getPrev() {
		return prev;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public double[] getValues() {
		return values;
	}

	public double[][] getWeights() {
		return weights;
	}

	public void setWeights(double[][] weights) {
		this.weights = weights;
	}

	public NetworkLayer getNext() {
		return next;
	}

	public void calcForward(double input[]) {
//		String s = "VALUES: ";
//		for (int j = 0; j < input.length; j++) {
//			s += input[j] + " ; ";
//		}
//		System.out.println(s);
		for (int i = 0; i < perceptronCount; i++) {
			values[i] = 0;
			for (int j = 0; j < input.length; j++) {
				values[i] += input[j] * weights[i][j];
			}
			values[i] = sigmoid(values[i]);
		}
		if (next != null) {
			next.calcForward(values);
		}
	}
	/**
	 * funkcja aktywacyjna
	 * @param d
	 * @return
	 */
	private double sigmoid(double d) {
		return 1.0 / (1 + Math.exp(-d));
	}

	public void calcBackward(double diffs[]) {
		// recalculate my errors
		for (int i = 0; i < perceptronCount; i++) {
			errors[i] = diffs[i] * values[i] * (1 - values[i]);
		}

		if (prev != null) {
			// recalculate errors for previous layer
			double prevErr[] = new double[prev.perceptronCount];
			for (int j = 0; j < prev.perceptronCount; j++) {
				prevErr[j] = 0;
			}
			for (int i = 0; i < perceptronCount; i++) {
				for (int j = 0; j < prev.perceptronCount; j++) {
					prevErr[j] += errors[i] * weights[i][j];
				}
			}
			// recalculate weights between previous layer
			for (int i = 0; i < perceptronCount; i++) {
				// i-ty perceptron tej warstwy
				for (int j = 0; j < prev.perceptronCount; j++) {
					// j-ty perceptron poprzedniej warstwy
					weights[i][j] -= RobotArm.LEARN_RATE * errors[i] * prev.values[j];
				}
			}
			// invoke recursively back propagation
			prev.calcBackward(prevErr);
		}
	}
}
