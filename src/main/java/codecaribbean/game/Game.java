package codecaribbean.game;

import codecaribbean.command.Command;
import codecaribbean.entity.Entity;
import codecaribbean.entity.factory.EntityFactory;
import codecaribbean.entity.factory.EntityType;
import codecaribbean.game.cell.Coord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Game {
    private Pirate me;
    private Pirate opponent;
    private int currentTurn;
    private List<Coord> cannonBallExplosions;

    private Grid grid;

    private Map<Integer, Entity> entities;

    Game() {
        this.entities = new HashMap<>();
        this.currentTurn = 0;
        this.me = new Pirate(1, null);
        this.opponent = new Pirate(0, this.me);
    }

    private void addEntity(int id, Entity entity) {
        this.entities.put(id, entity);
    }

    void updateOrCreateEntity(EntityType entityType, int... args) {
        Entity entity = entities.get(args[0]);
        if (entity != null) {
            entity.update(args);
        } else {
            entity = EntityFactory.crete(entityType, args[0], args[1], args[2], args[3], args[4], args[5], args[6], currentTurn);
            addEntity(args[0], entity);
        }
    }

    public void nextTurn() {
        this.currentTurn++;
    }

    public Command doAction(int i) {
        return me.getAction(i);
    }

    public void processing() {
        updateData();
        me.buildCommands();
    }

    private void updateData() {
        me.initTurn();
        opponent.initTurn();
        for (Entity entity : entities.values()) {
            if (!entity.isDead(currentTurn)) {
                entity.updateData(me, opponent);
            } else {
                System.err.println("remove entity");
                entities.remove(entity);
            }
        }
    }
}
