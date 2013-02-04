package umk.neural.network.hopfield;

/**
 *
 * @author winx
 */
public class Przyklad {

    private int[] przyklad;

    public Przyklad(int[] p) {
        przyklad = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            przyklad[i] = p[i];
        }
    }

    public int[] getPrzyklad() {
        return przyklad;
    }
}
