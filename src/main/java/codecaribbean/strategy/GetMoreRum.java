package codecaribbean.strategy;

import codecaribbean.command.Command;
import codecaribbean.entity.Barrel;
import codecaribbean.entity.Ship;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by MedBelmahi on 21/04/2017.
 */
public class GetMoreRum implements PlayStrategy {

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
