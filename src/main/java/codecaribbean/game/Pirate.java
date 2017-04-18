package codecaribbean.game;

import codecaribbean.command.Command;
import codecaribbean.entity.Barrel;
import codecaribbean.entity.Ship;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
public class Pirate {

    private Set<Command> commands;
    Set<Ship> ships = new HashSet<>();
    Set<Barrel> barrels = new HashSet<>();
    private int id;

    public Pirate(int id) {
        this.id = id;
    }

    public Command getAction(int i) {
        return ships.;
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

        return ship.moveTo(barrelTreeSet.first());
    }

    public void addBarrels(Barrel barrel) {
        this.barrels.add(barrel);
    }
}
