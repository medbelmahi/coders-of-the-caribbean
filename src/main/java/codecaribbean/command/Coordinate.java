package codecaribbean.command;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
