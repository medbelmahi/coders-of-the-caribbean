package codecaribbean.command;

/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
public class FireCommand extends Command {

    private Coordinate coordinate;
    protected FireCommand(Coordinate coordinate) {
        super("FIRE");
        this.coordinate = coordinate;
    }
}
