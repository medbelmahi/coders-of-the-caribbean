package codecaribbean.strategy;

import codecaribbean.command.Command;
import codecaribbean.entity.Entity;
import codecaribbean.entity.Mine;
import codecaribbean.entity.Ship;

import java.util.*;

/**
 * Created by MedBelmahi on 21/04/2017.
 */
public class DoFire implements PlayStrategy {

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
