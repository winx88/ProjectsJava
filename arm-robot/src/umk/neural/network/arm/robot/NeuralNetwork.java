package umk.neural.network.arm.robot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzilna z budowę sieci
 * @author winx
 * @date 23-11-2012
 */

public class NeuralNetwork {

	// private static final int ITERATIONS = 1;
	private List<NetworkLayer> layers;

	private NetworkLayer in;

	private NetworkLayer out;

	public NeuralNetwork() {
		initLayers();
	}

	private void initLayers() {
		layers = new ArrayList<NetworkLayer>();
		in = new NetworkLayer(2);
		NetworkLayer hidden1 = new NetworkLayer(3);
		hidden1.setPrev(in);
		NetworkLayer hidden2 = new NetworkLayer(3);
		hidden2.setPrev(hidden1);
		NetworkLayer hidden3 = new NetworkLayer(3);
		hidden3.setPrev(hidden2);
		out = new NetworkLayer(2);
		out.setPrev(hidden3);
		/**
		 * budowa warstw
		 */
		layers.add(in);
		layers.add(hidden1);
		layers.add(hidden2);
		layers.add(hidden3);
		layers.add(out);
	}
	
	/**
	 * Uczenie sieci 
	 * @param iterations ilosc interacji do nauki
	 */

	public void startLearning(int iterations) {
		for (int i = 0; i < iterations; i++) {
			Point t = RobotArm.randomPoint();
			Point angles = RobotArm.calcAngles(t);

			double normalizedIn[] = RobotArm.normalizeInput(t);
			double normalizedOut[] = RobotArm.normalizeOutput(angles);
			learn(normalizedIn, normalizedOut);
		}
	}

	private void learn(double input[], double output[]) {
		in.setValues(input);
		in.getNext().calcForward(in.getValues());
//		System.out.println("IN: P(" + in.getValues()[0] + "," + in.getValues()[1] + ") OUT: P(" + out.getValues()[0]
//				+ "," + out.getValues()[1] + ")");
		double diffs[] = { out.getValues()[0] - output[0], out.getValues()[1] - output[1] };
//		System.out.println("Expected: P(" + output[0] + "," + output[1] + ") Actual: P(" + out.getValues()[0] + ","
//				+ out.getValues()[1] + ")");
//		System.out.println("Error: P(" + diffs[0] + "," + diffs[1] + ")");
		out.calcBackward(diffs);
	}

	public Point eval(Point input) {
		double normalizedIn[] = RobotArm.normalizeInput(input);
		in.setValues(normalizedIn);
		in.getNext().calcForward(in.getValues());
//		System.out.println("IN: P(" + in.getValues()[0] + "," + in.getValues()[1] + ") OUT: P(" + out.getValues()[0]
//				+ "," + out.getValues()[1] + ")");
		Point output = RobotArm.unnormalizeOutput(out.getValues());
//		System.out.println("OUT: P(" + output.x + "," + output.y + ")");
		return output;
	}

}
