package codecaribbean.command;

import codecaribbean.entity.Entity;
import codecaribbean.game.cell.Coord;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public class MoveCommand extends Command {
    private Entity ship;
    private Coord coordinate;

    public MoveCommand(Coord coordinate) {
        super("MOVE");
        this.coordinate = coordinate;
    }

    public MoveCommand(Entity ship, Coord coordinate) {
        super("MOVE");
        this.ship = ship;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return super.toString() + " " + coordinate.toString();
    }
}
