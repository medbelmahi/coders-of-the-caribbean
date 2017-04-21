package codecaribbean.command;

import codecaribbean.game.cell.Coord;

/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
public class FireCommand extends Command {

    private Coord coordinate;

    public FireCommand(Coord coordinate) {
        super("FIRE");
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return super.toString() + " " + coordinate.toString();
    }
}
