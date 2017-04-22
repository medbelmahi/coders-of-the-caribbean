package codecaribbean.strategy;

import codecaribbean.command.Command;
import codecaribbean.entity.Cannonball;
import codecaribbean.entity.Ship;

import java.util.List;

/**
 * Created by MedBelmahi on 22/04/2017.
 */
public class Runaway implements PlayStrategy {

    private List<Cannonball> cannonballs;
    private Ship ship;
    private Cannonball dangerBall;

    public Runaway(List<Cannonball> cannonballs, Ship ship) {
        this.cannonballs = cannonballs;
        this.ship = ship;
    }

    @Override
    public Command buildAction() {
        return null;
    }

    @Override
    public Boolean isApplicable() {

        for (Cannonball cannonball : this.cannonballs) {
            if (cannonball.willDestroy(ship)) {
                this.dangerBall = cannonball;
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }
}