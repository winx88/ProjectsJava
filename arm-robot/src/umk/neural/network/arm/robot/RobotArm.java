package umk.neural.network.arm.robot;

import java.awt.Point;
import java.util.Random;

public class RobotArm {

	public final static int ARM_LENGTH = 100;

	public final static Point ATTACH_POINT = new Point(0, 200);

	public final static double LEARN_RATE = 0.2;

	public final static NeuralNetwork net = new NeuralNetwork();

	public static void main(String[] args) {
		new Window();
	}

	public static Point[] calc(Point angles) {
		Point armPoints[] = new Point[2];
		armPoints[0] = new Point((int) (sinDegree(angles.x) * ARM_LENGTH) + ATTACH_POINT.x,
				(int) (-cosDegree(angles.x) * ARM_LENGTH) + ATTACH_POINT.y);
		
		armPoints[1] = new Point((int) (-sinDegree(angles.x + angles.y) * ARM_LENGTH) + armPoints[0].x,
				(int) (cosDegree(angles.x + angles.y) * ARM_LENGTH) + armPoints[0].y);

		/*System.out.println("Angles to ArmPoints: A(" + angles.x + "," + angles.y + ") -> P1(" + armPoints[0].x + ","
				+ armPoints[0].y + ") P2(" + armPoints[1].x + "," + armPoints[1].y + ")");*/
		return armPoints;
	}

	public static double sinDegree(double degree) {
		return Math.sin(degree * Math.PI / 180);
	}

	public static double cosDegree(double degree) {
		return Math.cos(degree * Math.PI / 180);
	}

	public static Point randomPoint() {
		Random r = new Random();
		int alpha = r.nextInt(181);
		double length = r.nextDouble() * 1.8 + 0.1; // 0.1 - 1.9
		int x = (int) (ARM_LENGTH * length * sinDegree((double) alpha));
		int y = (int) (ARM_LENGTH * length * cosDegree((double) alpha));

		Point p = new Point();
		p.x = x + ATTACH_POINT.x;
		p.y = y + ATTACH_POINT.y;

		/*System.out.println("Random Point: " + alpha + " -> P(" + x + "," + y + ") -> P(" + p.x + "," + p.y + ")");*/
		return p;
	}

	public static double[] normalizeOutput(Point angles) {
		double normalized[] = new double[2];
		normalized[0] = (double) angles.x / 180;
		normalized[1] = (double) angles.y / 180;

//		System.out.println("Normalized Output: P(" + angles.x + "," + angles.y + ") -> N(" + normalized[0] + ","
//				+ normalized[1] + ")");
		return normalized;
	}

	public static Point unnormalizeOutput(double[] normalized) {
		Point p = new Point();
		p.x = (int) (normalized[0] * 180);
		p.y = (int) (normalized[1] * 180);

//		System.out.println("Unnormalized Output: N(" + normalized[0] + "," + normalized[1] + ") -> P(" + p.x + ","
//				+ p.y + ")");
		return p;
	}

	public static double[] normalizeInput(Point input) {
		double normalized[] = new double[2];
		normalized[0] = (double) (input.x - ATTACH_POINT.x) / (ARM_LENGTH * 2);
		normalized[1] = (double) (input.y - ATTACH_POINT.y) / (ARM_LENGTH * 2);

//		System.out.println("Normalized Input: P(" + input.x + "," + input.y + ") -> N(" + normalized[0] + ","
//				+ normalized[1] + ")");
		return normalized;
	}

	public static Point unnormalizeInput(double[] normalized) {
		Point p = new Point();
		p.x = (int) (normalized[0] * ARM_LENGTH * 2);
		p.y = (int) (normalized[1] * ARM_LENGTH * 2);
		p.x += ATTACH_POINT.x;
		p.y += ATTACH_POINT.y;

//		System.out.println("Unnormalized Input: N(" + normalized[0] + "," + normalized[1] + ") -> P(" + p.x + "," + p.y
//				+ ")");
		return p;
	}

	public static Point calcAngles(Point target) {
		// todo checks
		double d = target.distance(ATTACH_POINT);
		double d2 = d / 2;
		double sinb = d2 / ARM_LENGTH;
		double b = Math.asin(sinb) * 2;
		b *= (180 / Math.PI);

		int dx = target.x - ATTACH_POINT.x;
		int dy = target.y - ATTACH_POINT.y;
		double a1 = Math.atan((double) dx / dy);
		a1 *= (180 / Math.PI);
		if (a1 < 0 ){
			a1 = -a1;
		}else{
			a1 = 180 -a1;
		}
		double a = a1 + (180 - b) / 2;

		Point p = new Point();
		p.x = (int) a;
		p.y = (int) b;

		System.out.println("Point to Angles: P(" + target.x + "," + target.y + ") -> A(" + p.x + "," + p.y + ")");
		/*System.out.print("{" + target.x + "," + target.y + "},{" + p.x + "," + p.y + "},");*/
		return p;
	}

}
