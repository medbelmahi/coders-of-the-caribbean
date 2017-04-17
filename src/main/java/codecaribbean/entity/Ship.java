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
    private int intPirate;

    public Ship(int entityId, int... args) {
        super(entityId, args[0], args[1]);
        this.orientation = args[2];
        this.speed = args[3];
        this.rumStock = args[4];
        this.intPirate = args[5];
    }

    @Override
    public void update(int[] args) {
        super.update(args);
        this.orientation = args[3];
        this.speed = args[4];
        this.rumStock = args[5];
        this.intPirate = args[6];
    }

    @Override
    public boolean appropriateTO(int i) {
        return intPirate == i;
    }

    public void setPirate(Pirate pirate) {
        this.pirate = pirate;
    }
}