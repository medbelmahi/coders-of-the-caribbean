package codecaribbean.entity;

import codecaribbean.game.Pirate;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public class Ship extends Entity {
    private int orientation;
    private int speed;
    private int rumStock;
    private Pirate pirate;

    public Ship(int entityId, int... args) {
        super(entityId, args[0], args[1]);
        this.orientation = args[2];
        this.speed = args[3];
        this.rumStock = args[4];
    }
}