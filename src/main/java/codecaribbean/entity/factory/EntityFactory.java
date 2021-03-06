package codecaribbean.entity.factory;

import codecaribbean.entity.Barrel;
import codecaribbean.entity.Cannonball;
import codecaribbean.entity.Entity;
import codecaribbean.entity.Mine;
import codecaribbean.entity.Ship;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
public class EntityFactory {
    public static Entity crete(EntityType entityType, int entityId, int x, int y, int arg1, int arg2, int arg3, int arg4, int currentTurn) {

        switch (entityType) {
            case SHIP : return new Ship(entityId, x, y, arg1, arg2, arg3, arg4, currentTurn);
            case BARREL : return new Barrel(entityId, x, y, arg1, currentTurn);
            case CANNONBALL : return new Cannonball(entityId, x, y, arg1, arg2, currentTurn);
            case MINE : return new Mine(entityId, x, y, currentTurn);
            default:
                throw new IllegalArgumentException("Entity type not exist");
        }
    }
}
