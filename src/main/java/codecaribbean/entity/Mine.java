package codecaribbean.entity;

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

    }
}
