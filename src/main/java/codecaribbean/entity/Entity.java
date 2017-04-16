package codecaribbean.entity;

import codecaribbean.command.Coordinate;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public class Entity {
    private Coordinate coordinate;
    private int id;

    public Entity(int entityId, int x, int y) {
        this.id = entityId;
        this.coordinate = new Coordinate(x, y);
    }
}
