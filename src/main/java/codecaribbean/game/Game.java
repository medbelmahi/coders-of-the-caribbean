package codecaribbean.game;

import codecaribbean.entity.Entity;
import codecaribbean.entity.factory.EntityFactory;
import codecaribbean.entity.factory.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Game {
    private Pirate me;
    private Pirate opponent;

    private Grid grid;

    private Map<Integer, Entity> entities;

    Game() {
        this.entities = new HashMap<>();
    }

    private void addEntity(int id, Entity entity) {
        this.entities.put(id, entity);
    }

    void updateOrCreateEntity(EntityType entityType, int... args) {
        Entity entity = entities.get(args[0]);
        if (entity != null) {
            entity.update(args);
        } else {
            entity = EntityFactory.crete(entityType, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            addEntity(args[0], entity);
        }
    }

}
