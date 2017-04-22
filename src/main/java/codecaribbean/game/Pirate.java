package codecaribbean.game;

import codecaribbean.command.Command;
import codecaribbean.command.WaitCommand;
import codecaribbean.entity.Barrel;
import codecaribbean.entity.Cannonball;
import codecaribbean.entity.Mine;
import codecaribbean.entity.Ship;
import codecaribbean.strategy.DoFire;
import codecaribbean.strategy.GetMoreRum;
import codecaribbean.strategy.PlayStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MedBelmahi on 16/04/2017.
 */
public class Pirate {

    List<Ship> ships = new ArrayList<>();
    Set<Barrel> barrels = new HashSet<>();
    List<Mine> mines = new ArrayList<>();
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
        return ships.get(i).getOrder();
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
        strategies.add(new DoFire(this.mines, ship));
        strategies.add(new GetMoreRum(this.barrels, ship));
        strategies.add(new DoFire(this.opponent.ships, ship));

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
