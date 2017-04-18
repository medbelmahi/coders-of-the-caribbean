package codecaribbean.entity;

import codecaribbean.command.Command;
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
    private Command currentOrder;

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

    private boolean appropriateTO(int i) {
        return intPirate == i;
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        if (me.isMyShip(intPirate)) {
            this.pirate = me;
            me.addShip(this);
        } else {
            this.pirate = opponent;
            opponent.addShip(this);
        }
    }

    public void setOrder(Command command) {
        this.currentOrder = command;
    }
}