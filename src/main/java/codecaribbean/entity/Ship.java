package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.game.Pirate;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public class Ship extends Entity {

    private static final int COOLDOWN_CANNON = 1;
    private static final int COOLDOWN_MINE = 4;

    int mineCooldown;
    int cannonCooldown;

    private int orientation;
    private int speed;
    private int rumStock;
    private Pirate owner;
    private int intPirate;
    private Command currentOrder;

    public Ship(int entityId, int... args) {
        super(entityId, args[0], args[1], args[6]);
        this.orientation = args[2];
        this.speed = args[3];
        this.rumStock = args[4];
        this.intPirate = args[5];
    }

    @Override
    public void update(int[] args) {
        System.err.println("ship update");
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
            this.owner = me;
            me.addShip(this);
        } else {
            this.owner = opponent;
            opponent.addShip(this);
        }
    }

    @Override
    public Command FireMe() {
        return null;
    }

    public void setOrder(Command command) {
        this.currentOrder = command;
    }

    public Command getOrder() {
        return this.currentOrder;
    }

    public boolean canFire() {
        return cannonCooldown == 0;
    }

    public Command doFire(Entity actionTarget) {
        this.cannonCooldown = COOLDOWN_CANNON;
        return actionTarget.FireMe();
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if (this.cannonCooldown > 0) {
            this.cannonCooldown--;
        }
        if (this.mineCooldown > 0) {
            this.mineCooldown--;
        }
    }
}