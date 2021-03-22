package controllers;

import game2.Bullet;
import game2.Constants;
import game2.GameObject;
import game2.Ship;
import utilities.Vector2D;

public class Controllers {
    public static GameObject findTarget(GameObject ship, Iterable<GameObject> gameObjects) {
        double minDistance = Constants.WORLD_WIDTH;
        GameObject closestTarget = null;
        for (GameObject obj : gameObjects) {
            if (obj==ship || !ship.canHit(obj) || (obj instanceof Bullet))
                continue;
            double dist = ship.dist(obj);
            if (dist < minDistance) {
                closestTarget = obj;
                minDistance = dist;
            }
        }
        if (closestTarget==null) System.out.println("No target");
        return closestTarget;
    }

    public static double angleToTarget(GameObject ship, GameObject target) {
        Vector2D targetPosition = new Vector2D(target.pos).addScaled(target.vel, Constants.DT * 5);
        return ship.dir.angle(targetPosition.subtract(ship.pos));
    }

    public static int aim(GameObject ship, GameObject target) {
        double angle = angleToTarget(ship, target);
        if (Math.abs(angle) < 0.8 * Ship.STEER_RATE)
            return 0;
        else
            return angle > 0 ? 1 : -1;
    }

}
