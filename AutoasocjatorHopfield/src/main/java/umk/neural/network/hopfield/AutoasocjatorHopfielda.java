package umk.neural.network.hopfield;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author winx
 */
public class AutoasocjatorHopfielda {

    private int WIDTH;
    private int HEIGHT;
    private int[][] patterns;
    private List<Przyklad> learningData;
    private Perceptron[] perceptron;
    private String path;
    private String[] picturesToRecognise = {"happy.bmp", "kwiat.bmp", "ludzik.bmp", "star.bmp"};

    public AutoasocjatorHopfielda(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        path = "src/main/resources/";

        this.createPatterns();
        this.learn();
    }

    private void createPatterns() {
        patterns = new int[picturesToRecognise.length][WIDTH * HEIGHT];
        BufferedImage readImage = null;
        byte[] imageData = new byte[WIDTH * HEIGHT];
        for (int i = 0; i < picturesToRecognise.length; i++) {
            try {
                readImage = ImageIO.read(new File(path + picturesToRecognise[i]));
            } catch (Exception e) {
                System.out.println("Unable to read picture patterns!");
            }

            for (int x = 0; x < WIDTH * HEIGHT; x++) {
                int reszta = x % WIDTH;
                imageData[x] = (byte) readImage.getRGB(reszta, (x - reszta) / WIDTH);
            }

            for (int j = 0; j < WIDTH * HEIGHT; j++) {
                this.patterns[i][j] = imageData[j] == 0 ? 1 : -1;
            }
        }
    }

    public void createLearningData() {
        learningData = new ArrayList<>();
        for (int i = 0; i < picturesToRecognise.length; i++) {
            learningData.add(new Przyklad(patterns[i]));
        }
    }

    private void learn() {
        createLearningData();
        perceptron = new Perceptron[WIDTH * HEIGHT];
        for (int i = 0; i < perceptron.length; i++) {
            perceptron[i] = new Perceptron(i, perceptron.length);
        }
        double[] tempWaga = new double[perceptron.length];

        for (int i = 0; i < perceptron.length; i++) {
            for (int k = 0; k < perceptron.length; k++) {
                tempWaga[k] = 0;
            }
            for (int p = 0; p < learningData.size(); p++) {
                for (int k = 0; k < perceptron.length; k++) {
                    perceptron[k].setSigma(learningData.get(p).getPrzyklad()[k]);
                }
                for (int j = 0; j < perceptron.length; j++) {
                    if (i != j) {
                        tempWaga[j] += perceptron[i].getSigma() * perceptron[j].getSigma();
                    }
                }
            }
            for (int k = 0; k < perceptron.length; k++) {
                tempWaga[k] /= perceptron.length;
            }
            perceptron[i].setWaga(tempWaga);
        }
    }

    public int[] recognise(int[] example) {
        int[] output = new int[WIDTH * HEIGHT];
        int los;
        for (int i = 0; i < perceptron.length; i++) {
            perceptron[i].setSigma(example[i]);
        }
        for (int i = 0; i < 100000; i++) {
            los = new Random().nextInt(perceptron.length);
            perceptron[los].funkcjaAktywacji(perceptron);
        }
        for (int i = 0; i < perceptron.length; i++) {
            output[i] = perceptron[i].getSigma();
        }
        return output;
    }

    public int[] getPattern(int pattern) {
        return patterns[pattern];
    }
}
