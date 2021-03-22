package controllers;

import game2.Action;
import game2.Controller;

public class RandomAction implements Controller {
    public Action action;
    public RandomAction() {
        action = new Action();
    }

    @Override
    public Action action() {
        action.shoot = Math.random() < 0.05;
        double turn = Math.random();
        action.turn = turn < 0.1 ? 1 : turn > 0.9 ? -1 : 0;
        action.thrust = Math.random() < 0.3 ? 1 : 0;
        return action;
    }
}
