import java.math.*;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.awt.*;
import java.util.*;




/**
 * Created by MedBelmahi on 15/04/2017.
 */
abstract class Command {
    private String commandLabel;

    protected Command(String commandLabel) {
        this.commandLabel = commandLabel;
    }

    @Override
    public String toString() {
        return commandLabel;
    }
}



/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    public double distance(Coordinate coordinate) {
        return Point2D.distance(this.x, this.y, coordinate.x, coordinate.y);
    }
}



/**
 * Created by MedBelmahi on 15/04/2017.
 */
class MoveCommand extends Command {
    private Entity ship;
    private Coordinate coordinate;

    public MoveCommand(Coordinate coordinate) {
        super("MOVE");
        this.coordinate = coordinate;
    }

    public MoveCommand(Entity ship, Coordinate coordinate) {
        super("MOVE");
        this.ship = ship;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return super.toString() + " " + coordinate.toString();
    }
}


/**
 * Created by MedBelmahi on 15/04/2017.
 */
class SlowerCommand extends Command {
    public SlowerCommand() {
        super("SLOWER");
    }
}


/**
 * Created by MedBelmahi on 15/04/2017.
 */
class WaitCommand extends Command {
    public WaitCommand() {
        super("WAIT");
    }
}




/**
 * Created by MedBelmahi on 15/04/2017.
 */
class Barrel extends Entity{

    private int amount;

    public Barrel(int entityId, int x, int y, int arg1, int currentTurn) {
        super(entityId, x, y, currentTurn);
        this.amount = arg1;
    }

    @Override
    public void update(int[] args) {
        super.update(args);
        this.amount = args[3];
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        me.addBarrels(this);
    }
}



/**
 * Created by MedBelmahi on 15/04/2017.
 */
abstract class Entity {
    protected Coordinate coordinate;
    public int id;
    private int currentTurn;

    public Entity(int entityId, int x, int y, int currentTurn) {
        this.id = entityId;
        this.coordinate = new Coordinate(x, y);
        this.currentTurn = currentTurn;
    }

    public void update(int[] args) {
        nextTurn();
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

    private void nextTurn() {
        this.currentTurn++;
    }
}




/**
 * Created by MedBelmahi on 16/04/2017.
 */
class EntityFactory {
    public static Entity crete(EntityType entityType, int entityId, int x, int y, int arg1, int arg2, int arg3, int arg4, int currentTurn) {

        switch (entityType) {
            case SHIP : return new Ship(entityId, x, y, arg1, arg2, arg3, arg4, currentTurn);
            case BARREL : return new Barrel(entityId, x, y, arg1, currentTurn);
            default:
                throw new IllegalArgumentException("Entity type not exist");
        }
    }
}


/**
 * Created by MedBelmahi on 16/04/2017.
 */
enum EntityType {
    SHIP, BARREL
}



/**
 * Created by MedBelmahi on 15/04/2017.
 */
class Ship extends Entity {
    private int orientation;
    private int speed;
    private int rumStock;
    private Pirate pirate;
    private int intPirate;
    private Command currentOrder;

    public Ship(int entityId, int... args) {
        super(entityId, args[0], args[1], args[6]);
        this.orientation = args[2];
        this.speed = args[3];
        this.rumStock = args[4];
        this.intPirate = args[5];
    }

    @Override
    public void update(int[] args) {
        super.update(args);
        this.orientation = args[3];
        this.speed = args[4];
        this.rumStock = args[5];
        this.intPirate = args[6];
    }

    private boolean appropriateTO(int i) {
        return intPirate == i;
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        if (me.isMyShip(intPirate)) {
            this.pirate = me;
            me.addShip(this);
        } else {
            this.pirate = opponent;
            opponent.addShip(this);
        }
    }

    public void setOrder(Command command) {
        this.currentOrder = command;
    }

    public Command getOrder() {
        return this.currentOrder;
    }
}



/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Cell {
}




/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Game {
    private Pirate me;
    private Pirate opponent;
    private int currentTurn;

    private Grid grid;

    private Map<Integer, Entity> entities;

    Game() {
        this.entities = new HashMap<>();
        this.currentTurn = 0;
        this.me = new Pirate(1);
        this.opponent = new Pirate(0);
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


/**
 * Created by MedBelmahi on 15/04/2017.
 */
class Grid {
    private Cell[][] cells;
}




/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Pirate {

    private Set<Command> commands;
    Set<Ship> ships = new HashSet<>();
    Set<Barrel> barrels = new HashSet<>();
    private int id;

    public Pirate(int id) {
        this.id = id;
    }

    public Command getAction(int i) {
        for (Ship ship : ships) {
            return ship.getOrder();
        }
        return null;
    }

    public boolean isMyShip(int intPirate) {
        return id == intPirate;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

    public void buildCommands() {
        for (Ship ship : ships) {
            final Command command = buildShipOrder(ship);
            ship.setOrder(command);
        }
    }

    private Command buildShipOrder(final Ship ship) {

        TreeSet<Barrel> barrelTreeSet = new TreeSet<>(new Comparator<Barrel>() {
            @Override
            public int compare(Barrel b1, Barrel b2) {
                return ship.distance(b1) > ship.distance(b2) ? 1 : -1;
            }
        });

        barrelTreeSet.addAll(barrels);
        System.err.println("barrels size : " + barrels.size());
        return ship.moveTo(barrelTreeSet.first());
    }

    public void addBarrels(Barrel barrel) {
        this.barrels.add(barrel);
    }

    public void initTurn() {
        ships.clear();
        barrels.clear();
    }
}




/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        Game pirateGame = new Game();

        // game loop
        while (true) {

            pirateGame.nextTurn();

            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();

                pirateGame.updateOrCreateEntity(EntityType.valueOf(entityType), entityId, x, y, arg1, arg2, arg3, arg4);
            }

            pirateGame.processing();

            for (int i = 0; i < myShipCount; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                Command command = pirateGame.doAction(i);
                //System.out.println("MOVE 11 10"); // Any valid action, such as "WAIT" or "MOVE x y"
                System.out.println(command.toString());
            }
        }
    }
}