package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.game.Pirate;

/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
public class Cannonball extends Entity {

    private int firedBy;
    private int turns;

    public Cannonball(int entityId, int... args) {
        super(entityId, args[0], args[1], args[4]);
        this.firedBy = args[2];
        this.turns = args[3];
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        me.addCannonBall(this);
    }

    @Override
    public Command FireMe() {
        return null;
    }
}
