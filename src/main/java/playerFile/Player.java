import java.util.stream.*;
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
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
class FireCommand extends Command {

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


/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
class MineCommand extends Command {
    protected MineCommand() {
        super("MINE");
    }
}



/**
 * Created by MedBelmahi on 15/04/2017.
 */
class MoveCommand extends Command {
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

    @Override
    public Command FireMe() {
        return null;
    }
}



/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
class Cannonball extends Entity {

    private int firedBy;
    private int turns;

    public Cannonball(int entityId, int... args) {
        super(entityId, args[0], args[1], args[4]);
        this.firedBy = args[2];
        this.turns = args[3];
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        me.addCannonBall(this);
    }

    @Override
    public Command FireMe() {
        return null;
    }
}



/**
 * Created by MedBelmahi on 15/04/2017.
 */
abstract class Entity {
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




/**
 * Created by MedBelmahi on 16/04/2017.
 */
class EntityFactory {
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


/**
 * Created by MedBelmahi on 16/04/2017.
 */
enum EntityType {
    SHIP, BARREL, CANNONBALL, MINE
}



/**
 * Created by Mohamed BELMAHI on 20/04/2017.
 */
class Mine extends Entity {

    public Mine(int entityId, int x, int y, int currentTurn) {
        super(entityId, x, y, currentTurn);
    }

    @Override
    public void updateData(Pirate me, Pirate opponent) {
        me.addMine(this);
    }

    @Override
    public Command FireMe() {
        return new FireCommand(this.coordinate);
    }
}



/**
 * Created by MedBelmahi on 15/04/2017.
 */
class Ship extends Entity {

    private static final int COOLDOWN_CANNON = 1;
    private static final int COOLDOWN_MINE = 4;

    int mineCooldown;
    int cannonCooldown;

    private int orientation;
    private int speed;
    private int rumStock;
    private Pirate owner;
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
        System.err.println("ship update");
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
            this.owner = me;
            me.addShip(this);
        } else {
            this.owner = opponent;
            opponent.addShip(this);
        }
    }

    @Override
    public Command FireMe() {
        return null;
    }

    public void setOrder(Command command) {
        this.currentOrder = command;
    }

    public Command getOrder() {
        return this.currentOrder;
    }

    public boolean canFire() {
        return cannonCooldown == 0;
    }

    public Command doFire(Entity actionTarget) {
        this.cannonCooldown = COOLDOWN_CANNON;
        return actionTarget.FireMe();
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if (this.cannonCooldown > 0) {
            this.cannonCooldown--;
        }
        if (this.mineCooldown > 0) {
            this.mineCooldown--;
        }
    }
}




/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Cell {
}



/**
 * Created by MedBelmahi on 20/04/2017.
 */
class Coord {
    private static final int MAP_WIDTH = 23;
    private static final int MAP_HEIGHT = 21;

    private final static int[][] DIRECTIONS_EVEN = new int[][] { { 1, 0 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 } };
    private final static int[][] DIRECTIONS_ODD = new int[][] { { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 1 } };
    private final int x;
    private final int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord(Coord other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double angle(Coord targetPosition) {
        double dy = (targetPosition.y - this.y) * Math.sqrt(3) / 2;
        double dx = targetPosition.x - this.x + ((this.y - targetPosition.y) & 1) * 0.5;
        double angle = -Math.atan2(dy, dx) * 3 / Math.PI;
        if (angle < 0) {
            angle += 6;
        } else if (angle >= 6) {
            angle -= 6;
        }
        return angle;
    }

    CubeCoordinate toCubeCoordinate() {
        int xp = x - (y - (y & 1)) / 2;
        int zp = y;
        int yp = -(xp + zp);
        return new CubeCoordinate(xp, yp, zp);
    }

    Coord neighbor(int orientation) {
        int newY, newX;
        if (this.y % 2 == 1) {
            newY = this.y + DIRECTIONS_ODD[orientation][1];
            newX = this.x + DIRECTIONS_ODD[orientation][0];
        } else {
            newY = this.y + DIRECTIONS_EVEN[orientation][1];
            newX = this.x + DIRECTIONS_EVEN[orientation][0];
        }

        return new Coord(newX, newY);
    }

    boolean isInsideMap() {
        return x >= 0 && x < MAP_WIDTH && y >= 0 && y < MAP_HEIGHT;
    }

    public int distanceTo(Coord dst) {
        return this.toCubeCoordinate().distanceTo(dst.toCubeCoordinate());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coord other = (Coord) obj;
        return y == other.y && x == other.x;
    }

    @Override
    public String toString() {
        return GameUtils.join(x, y);
    }
}



/**
 * Created by MedBelmahi on 20/04/2017.
 */
class CubeCoordinate {
    static int[][] directions = new int[][] { { 1, -1, 0 }, { +1, 0, -1 }, { 0, +1, -1 }, { -1, +1, 0 }, { -1, 0, +1 }, { 0, -1, +1 } };
    int x, y, z;

    public CubeCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Coord toOffsetCoordinate() {
        int newX = x + (z - (z & 1)) / 2;
        int newY = z;
        return new Coord(newX, newY);
    }

    CubeCoordinate neighbor(int orientation) {
        int nx = this.x + directions[orientation][0];
        int ny = this.y + directions[orientation][1];
        int nz = this.z + directions[orientation][2];

        return new CubeCoordinate(nx, ny, nz);
    }

    int distanceTo(CubeCoordinate dst) {
        return (Math.abs(x - dst.x) + Math.abs(y - dst.y) + Math.abs(z - dst.z)) / 2;
    }

    @Override
    public String toString() {
        return GameUtils.join(x, y, z);
    }
}




/**
 * Created by MedBelmahi on 16/04/2017.
 */
class Game {
    private Pirate me;
    private Pirate opponent;
    private int currentTurn;
    private List<Coord> cannonBallExplosions;

    private Grid grid;

    private Map<Integer, Entity> entities;

    Game() {
        this.entities = new HashMap<>();
        this.currentTurn = 0;
        this.me = new Pirate(1, null);
        this.opponent = new Pirate(0, this.me);
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
        System.err.println("Turn number : " + this.currentTurn);
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
                System.err.println("reoved entity : " + entity.toString());
                entities.remove(entity);
            }
        }
        System.err.println("fin remove...");
    }
}



/**
 * Created by MedBelmahi on 20/04/2017.
 */
class GameUtils {
    @SafeVarargs
    public static final <T> String join(T... v) {
        return Stream.of(v).map(String::valueOf).collect(Collectors.joining(" "));
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

    Set<Ship> ships = new HashSet<>();
    Set<Barrel> barrels = new HashSet<>();
    Set<Mine> mines = new HashSet<>();
    Set<Cannonball> cannonballs = new HashSet<>();
    private int id;
    private Pirate opponent;


    public Pirate(int id, Pirate opponent) {
        this.id = id;
        if (opponent != null) {
            this.opponent = opponent;
            opponent.opponent = this;
        }
    }

    public Command getAction(int i) {
        for (Ship ship : ships) {
            System.err.println("here 1");
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
        System.err.println("ships size : " + ships.size());
        for (Ship ship : ships) {
            final Command command = buildShipOrder(ship);
            ship.setOrder(command);
        }
    }

    private Command buildShipOrder(final Ship ship) {

        List<PlayStrategy> strategies = new ArrayList<>();
        System.err.println("mines size : " + mines.size());
        System.err.println("barrels size : " + barrels.size());
        strategies.add(new DoFire(mines, ship));
        strategies.add(new GetMoreRum(barrels, ship));

        for (PlayStrategy strategy : strategies) {
            System.err.println("strategy name : " + strategy.toString());
            if (strategy.isApplicable()) {
                return strategy.buildAction();
            }
        }

        return new WaitCommand();
    }

    public void addBarrels(Barrel barrel) {
        this.barrels.add(barrel);
    }

    public void initTurn() {
        ships.clear();
        barrels.clear();
        this.cannonballs.clear();
        this.mines.clear();
    }

    public void addMine(Mine mine) {
        this.mines.add(mine);
    }

    public void addCannonBall(Cannonball cannonball) {
        this.cannonballs.add(cannonball);
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
                System.err.println("order N : " + i);
                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                Command command = pirateGame.doAction(i);
                //System.out.println("MOVE 11 10"); // Any valid action, such as "WAIT" or "MOVE x y"
                System.out.println(command.toString());
            }
        }
    }
}





/**
 * Created by MedBelmahi on 21/04/2017.
 */
class DoFire implements PlayStrategy {

    public static final int UNDER_ATTCK_RANG = 10;

    private Set<Mine> targets = new HashSet<>();
    private Ship ship;
    private Entity actionTarget;

    public DoFire(Set<Mine> targets, Ship ship) {
        this.targets = targets;
        this.ship = ship;
    }

    @Override
    public Command buildAction() {
        System.err.printf("do fire");
        return ship.doFire(actionTarget);
    }

    @Override
    public Boolean isApplicable() {

        if (ship.canFire() && !this.targets.isEmpty()) {

            TreeSet<Entity> targetTreeSet = new TreeSet<>(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return ship.distance(e1) > ship.distance(e2) ? 1 : -1;
                }
            });

            targetTreeSet.addAll(this.targets);

            Entity currentTarget = targetTreeSet.first();

            if (currentTarget.distance(this.ship) < UNDER_ATTCK_RANG) {
                this.actionTarget = currentTarget;

                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }
}




/**
 * Created by MedBelmahi on 21/04/2017.
 */
class GetMoreRum implements PlayStrategy {

    private Set<Barrel> barrels;
    private Ship ship;

    public GetMoreRum(Set<Barrel> barrels, Ship ship) {
        this.barrels = barrels;
        this.ship = ship;
    }

    @Override
    public Command buildAction() {
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

    @Override
    public Boolean isApplicable() {
        return !this.barrels.isEmpty();
    }
}


/**
 * Created by MedBelmahi on 21/04/2017.
 */
class PlaceMine {
}



/**
 * Created by MedBelmahi on 21/04/2017.
 */
interface PlayStrategy {
    Command buildAction();

    Boolean isApplicable();
}