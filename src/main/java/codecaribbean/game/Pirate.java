package codecaribbean.game;

import codecaribbean.command.Command;
import codecaribbean.entity.Barrel;
import codecaribbean.entity.Ship;
import codecaribbean.strategy.GetMoreRum;
import codecaribbean.strategy.PlayStrategy;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
public class Pirate {

    Set<Ship> ships = new HashSet<>();
    Set<Barrel> barrels = new HashSet<>();
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

        PlayStrategy getMoreRum = new GetMoreRum(barrels, ship);

        return getMoreRum.buildAction();
    }

    public void addBarrels(Barrel barrel) {
        this.barrels.add(barrel);
    }

    public void initTurn() {
        ships.clear();
        barrels.clear();
    }
}
