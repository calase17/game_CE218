package controllers;

import game2.*;

public class AimNShoot implements Controller{
    public static final double SHOOTING_DISTANCE = 1.5 * Bullet.BULLET_LIFE
            * Bullet.INITIAL_SPEED * Constants.DT;

    public static final double SHOOTING_ANGLE = Math.PI / 12;
    public Game game;
    private Action action = new Action();



    public AimNShoot(Game game) {
        this.game = game;
    }

    @Override
    public Action action() {
        GameObject target = Controllers.findTarget(game.playerShip, game.objects);
        if (target == null) {
            action.turn = 0;
            action.shoot = false;
            return action;
        }
        action.turn = Controllers.aim(game.playerShip, target);
        double distanceToTarget = game.playerShip.dist(target);
        action.shoot = distanceToTarget < SHOOTING_DISTANCE + target.radius;
        action.thrust = 0;
        return action;
    }

}
