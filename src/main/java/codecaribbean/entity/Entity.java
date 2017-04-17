package codecaribbean.entity;

import codecaribbean.command.Coordinate;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public abstract class Entity {
    private Coordinate coordinate;
    private int id;

    public Entity(int entityId, int x, int y) {
        this.id = entityId;
        this.coordinate = new Coordinate(x, y);
    }

    public void update(int[] args) {
        this.id = args[0];
        this.coordinate.update(args[1], args[2]);
    }

    public abstract boolean appropriateTO(int i);
}
