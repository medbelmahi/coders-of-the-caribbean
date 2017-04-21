package codecaribbean.entity;

import codecaribbean.command.Command;
import codecaribbean.command.MoveCommand;
import codecaribbean.game.Pirate;
import codecaribbean.game.cell.Coord;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public abstract class Entity {
    protected Coord coordinate;
    public int id;
    private int currentTurn;

    public Entity(int entityId, int x, int y, int currentTurn) {
        this.id = entityId;
        this.coordinate = new Coord(x, y);
        this.currentTurn = currentTurn;
    }

    public void update(int[] args) {
        nextTurn();
        this.id = args[0];
        this.coordinate = new Coord(args[1], args[2]);
    }

    public boolean isDead(int currentTurn) {
        System.err.println("ship turn : " + this.currentTurn);
        System.err.println("game turn : " + currentTurn);
        return this.currentTurn < currentTurn;
    }

    public abstract void updateData(Pirate me, Pirate opponent);

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Entity) obj).id;
    }

    public double distance(Entity entity) {
        return this.coordinate.distanceTo(entity.coordinate);
    }

    public Command moveTo(Barrel first) {
        return new MoveCommand(this, first.coordinate);
    }

    protected void nextTurn() {
        this.currentTurn++;
    }

    public abstract Command FireMe();
}
