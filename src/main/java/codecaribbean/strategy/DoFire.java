package codecaribbean.strategy;

import codecaribbean.command.Command;
import codecaribbean.entity.Entity;
import codecaribbean.entity.Ship;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by MedBelmahi on 21/04/2017.
 */
public class DoFire implements PlayStrategy {

    public static final int UNDER_ATTACK_RANG = 10;

    private List<? extends Entity> targets;
    private Ship ship;
    private Entity actionTarget;

    public DoFire(List<? extends Entity> targets, Ship ship) {
        this.targets = targets;
        this.ship = ship;
    }

    @Override
    public Command buildAction() {
        return ship.doFire(actionTarget);
    }

    @Override
    public Boolean isApplicable() {
        return applicableFor(this.targets);
    }

    private Boolean applicableFor(List<? extends Entity> targets) {
        if (ship.canFire() && !targets.isEmpty()) {

            TreeSet<Entity> targetTreeSet = new TreeSet<>(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return ship.distance(e1) > ship.distance(e2) ? 1 : -1;
                }
            });

            targetTreeSet.addAll(targets);

            for (Entity currentTarget : targetTreeSet) {
                if (currentTarget.distance(this.ship) < UNDER_ATTACK_RANG) {
                    if (!currentTarget.isUnderAttack()) {
                        this.actionTarget = currentTarget;
                        return Boolean.TRUE;
                    }
                } else {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.FALSE;
    }
}
