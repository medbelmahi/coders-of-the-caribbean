package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.command.MoveCommand;
import codecaribbean.game.Pirate;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public class Barrel extends Entity{

    private int amount;

    public Barrel(int entityId, int x, int y, int arg1) {
        super(entityId, x, y);
        this.amount = arg1;
    }

    @Override
    public void update(int[] args) {
        super.update(args);
        this.amount = args[3];
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        me.addBarrels(this);
    }
}
