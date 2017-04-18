package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.command.Coordinate;
import codecaribbean.command.MoveCommand;
import codecaribbean.game.Pirate;
import com.sun.javafx.geom.Point2D;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public abstract class Entity {
    protected Coordinate coordinate;
    public int id;
    private int currentTurn;

    public Entity(int entityId, int x, int y) {
        this.id = entityId;
        this.coordinate = new Coordinate(x, y);
        this.currentTurn = 0;
    }

    public void update(int[] args) {
        this.id = args[0];
        this.coordinate.update(args[1], args[2]);
    }

    public boolean isDead(int currentTurn) {
        return this.currentTurn < currentTurn;
    }

    public abstract void updateData(Pirate me, Pirate opponent);

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Entity) obj).id;
    }

    public double distance(Entity entity) {
        return this.coordinate.distance(entity.coordinate);
    }

    public Command moveTo(Barrel first) {
        return new MoveCommand(this, first.coordinate);
    }

}
