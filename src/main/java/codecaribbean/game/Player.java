package codecaribbean.game;

import codecaribbean.command.Command;
import codecaribbean.entity.factory.EntityType;

import java.util.Scanner;

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