package codecaribbean.command;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public abstract class Command {
    private String commandLabel;

    protected Command(String commandLabel) {
        this.commandLabel = commandLabel;
    }

    @Override
    public String toString() {
        return commandLabel;
    }
}
