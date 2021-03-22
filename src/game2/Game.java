package game2;

import controllers.AimNShoot;
import controllers.RandomAction;
import controllers.RotateNShoot;
import utilities.JEasyFrame;
import utilities.JEasyFrameFull;
//import utilities.SoundManager;
import utilities.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static game2.Constants.DELAY;
import static game2.Constants.setFullScreenDimensions;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static final int INITIAL_LIVES = 5;
    public static final int INITIAL_SAFETY_DURATION = 5000; // millisecs
    boolean shipIsSafe; // within initial safety period?
    public List<GameObject> objects;
    List<Ship> ships;
    public PlayerShip playerShip;
    Keys ctrl;
    int score, lives, level, remainingAsteroids, remainingSaucers;
    View view;
    boolean ended;
    long gameStartTime;  // start time for whole game
    long startTime;  // start time for current level/life
    boolean resetting;

    public Game(boolean fullScreen) {
        System.out.println("Constructor");
        if (fullScreen) setFullScreenDimensions();
        //SoundManager.play(SoundManager.thrust);
        view = new View(this);
        objects = new ArrayList<GameObject>();
        ships = new ArrayList<Ship>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {

            objects.add(new Asteroid());

        }
        System.out.println("Constructor 1");
        ctrl = new Keys();
        // playerShip = new PlayerShip(ctrl);
        // playerShip = new PlayerShip(new RotateNShoot());
        // playerShip = new PlayerShip(new RandomAction());
        playerShip = new PlayerShip(new AimNShoot(this));
        objects.add(playerShip);
        ships.add(playerShip);
        remainingAsteroids = N_INITIAL_ASTEROIDS;
        remainingSaucers = 0;
        addSaucers();
        score = 0;
        lives = INITIAL_LIVES;
        level = 1;
        ended = false;
        shipIsSafe = true;
        resetting = false;
        JFrame frame = fullScreen ? new JEasyFrameFull(view) : new JEasyFrame(view, "Asteroids 5");
        frame.setResizable(false);
        frame.addKeyListener(ctrl);

    }

    public static void main(String[] args) {
        Game game = new Game(false);
        game.gameLoop();
    }

    public void gameLoop() {
         gameStartTime = startTime = System.currentTimeMillis();
        while (!ended) {
            long time0 = System.currentTimeMillis();
            update();
            view.repaint();
            long timeToSleep = time0 + DELAY - System.currentTimeMillis();
            if (timeToSleep < 0)
                System.out.println("Warning: timeToSleep negative");
            else
                try {
                    Thread.sleep(timeToSleep);
                } catch (Exception e) {
                }


        }

        System.out.println("Your score was " + score);
        System.out.println("Game time " + (int) ((System.currentTimeMillis() - gameStartTime) / 1000));
    }

    public void incScore(int inc) {
        score += inc;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public boolean reset(boolean newlevel) {
        objects.clear();
        ships.clear();
        if (newlevel) level++;
        else
            lives--;
        if (lives == 0)
            return false;
        for (int i = 0; i < N_INITIAL_ASTEROIDS + (level - 1) * 5; i++) {
            objects.add(new Asteroid());

        }
        remainingAsteroids = N_INITIAL_ASTEROIDS + (level - 1) * 5;
        playerShip.reset();
        objects.add(playerShip);
        ships.add(playerShip);
        remainingSaucers = 0;
        addSaucers();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        shipIsSafe = true;
        startTime = System.currentTimeMillis();
        return true;

    }


    public void update() {

        // suppress collision detection at beginning of game
        if (shipIsSafe) {
            shipIsSafe = System.currentTimeMillis() < startTime + INITIAL_SAFETY_DURATION;
        } else
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    objects.get(i).collisionHandling(objects.get(j));
                }
            }


        ended = true;
        List<GameObject> alive = new ArrayList<>();
        for (GameObject o : objects) {
            if (!o.dead) {
                o.update();
                alive.add(o);
                if (o == playerShip) ended = false;
            }
            else if (o== playerShip){
                resetting = true;
                break;
            }
            else updateScore(o);
        }
        for (Ship ship:ships)
            if (ship.bullet != null) {
            alive.add(ship.bullet);
            ship.bullet = null;
        }


        synchronized (Game.class) {
            if (remainingAsteroids==0 && remainingSaucers==0){
                score += 1000;
                reset(true);
            }
                else if (resetting) {
                ended = !reset(false);
                resetting = false;
            }
            else {
            objects.clear();
            for (GameObject o : alive) objects.add(o);

            }
        }
    }

    public void updateScore(GameObject o) {
        if (o.getClass() == Asteroid.class) {
            score += 100;
            remainingAsteroids -= 1;
        }
        else if (o.getClass() == Saucer.class) {
            score += 500;
            remainingSaucers -= 1;
        }
    }

    private void addSaucers() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Adding saucer");
            Controller ctrl = (i % 3 != 0 ? new RandomAction() : new RotateNShoot());
            Color colorBody = (i % 3 != 0 ? Color.PINK : Color.GREEN);
            Random r = new Random();
            Vector2D s = new Vector2D(
                    r.nextInt(Constants.WORLD_WIDTH),
                    r.nextInt(Constants.WORLD_HEIGHT));
            Ship saucer = new Saucer(s, ctrl, colorBody, Color.white);
            objects.add(saucer);
            ships.add(saucer);
            remainingSaucers++;
        }

    }

}

