package codecaribbean.entity;

/**
 * Created by MedBelmahi on 15/04/2017.
 */
public class Barrel extends Entity{

    private int amount;

    public Barrel(int entityId, int x, int y, int arg1) {
        super(entityId, x, y);
        this.amount = arg1;
    }

    @Override
    public void update(int[] args) {
        super.update(args);
        this.amount = args[3];
    }

    @Override
    public boolean appropriateTO(int i) {
        return false;
    }
}
