package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.command.FireCommand;
import codecaribbean.game.Pirate;

/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
public class Mine extends Entity {


    public Mine(int entityId, int x, int y, int currentTurn) {
        super(entityId, x, y, currentTurn);
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        me.addMine(this);
    }

    @Override
    public Command FireMe(Ship ship) {
        this.isUnderAttack = true;
        return new FireCommand(this.coordinate);
    }


}
