package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.command.FireCommand;
import codecaribbean.command.MoveCommand;
import codecaribbean.game.Pirate;
import codecaribbean.game.cell.Coord;
import codecaribbean.strategy.DoFire;

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
    public Command FireMe(Ship ship) {

        Coord position = this.coordinate;
        int distance = 0;
        int countTurns = 0;
        int turns = 0;
        do {
            countTurns++;

            for (int i = 1; i <= this.speed; i++) {
                position = position.neighbor(this.orientation);
            }

            distance = ship.coordinate.distanceTo(position);
            turns = (distance + 1) / 3;
            turns = turns == 0 ? 1 : turns;

        } while (turns != countTurns && distance < DoFire.UNDER_ATTACK_RANG);

        if (distance < DoFire.UNDER_ATTACK_RANG) {
            return new FireCommand(position);
        }

        return new MoveCommand(this.coordinate);
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
        System.err.println("Do Fire ... ship : " + this.id + "\ttarget : " + actionTarget.id);
        return actionTarget.FireMe(this);
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

    public boolean needRums() {
        return rumStock < 90;
    }

    public Coord positionAfter(int turns) {
        Coord position = this.coordinate;
        for (int i = 0; i < turns; i++) {
            for (int j = 0; j < this.speed; j++) {
                position = position.neighbor(this.orientation);
            }
        }
        return position;
    }
}