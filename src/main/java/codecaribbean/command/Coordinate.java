package codecaribbean.command;

import com.sun.javafx.geom.Point2D;

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

    @Override
    public String toString() {
        return x + " " + y;
    }

    public double distance(Coordinate coordinate) {
        return Point2D.distance(this.x, this.y, coordinate.x, coordinate.y);
    }
}
