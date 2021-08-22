package game2;

import controllers.RandomAction;
import controllers.RotateNShoot;
import utilities.JEasyFrame;
import utilities.JEasyFrameFull;

import utilities.SoundManager;
import utilities.Vector2D;

import javax.swing.*;
import java.awt.*;


import java.io.*;

import java.util.*;
import java.util.List;
import java.util.Timer;


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
    int score, lives, level, remainingAsteroids, remainingGarbage;
    View view;
    boolean ended;
    long gameStartTime;  // start time for whole game
    long startTime;  // start time for current level/life
    boolean resetting;
    Timer shieldTimer;
    boolean addShield;

    public Game(boolean fullScreen) {
        System.out.println("Constructor");
        if (fullScreen) setFullScreenDimensions();
        //SoundManager.play(SoundManager.thrust);
        view = new View(this);
        objects = new ArrayList<GameObject>();
        ships = new ArrayList<Ship>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(new Asteroid());
            objects.add(new Garbage());
        }
        objects.add(new Shield());
        objects.add(new WormHole());
        ctrl = new Keys();
        playerShip = new PlayerShip(ctrl);
        objects.add(playerShip);
        ships.add(playerShip);
        remainingAsteroids = N_INITIAL_ASTEROIDS;
        remainingGarbage = 5;
        score = 0;
        lives = INITIAL_LIVES;
        level = 1;
        ended = false;
        shipIsSafe = true;
        resetting = false;
        int delay = ((int) (Math.random() * (10000 - 5000))) + 5000;
        shieldTimer = new Timer();
        shieldTimer.schedule(new ShieldTimer(),delay);
        JFrame frame = fullScreen ? new JEasyFrameFull(view) : new JEasyFrame(view, "AstroGains");
        frame.setResizable(false);
        frame.addKeyListener(ctrl);

    }

    class ShieldTimer extends TimerTask{
        @Override
        public void run() {
            addShield = true;
            if (!playerShip.activeShield){
                int delay = ((int) (Math.random() * (10000 - 5000))) + 5000;
                objects.add(new Shield());
                shieldTimer.schedule(new ShieldTimer(), delay);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Game game = new Game(false);
        game.gameLoop();
    }

    public void gameLoop() throws IOException {
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
        saveScore();
        int highScore = getHighScore();
        JOptionPane.showMessageDialog(null, "The high score is: " + highScore + "\n Your score was: " + score);
    }

    public void incScore(int inc) {
        score += inc;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() throws IOException {
        ArrayList<Integer> scoreArray = new ArrayList<>();
        String filename = "high_score.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            int scoreLine = Integer.parseInt(line);
            scoreArray.add(scoreLine);
        }
        bufferedReader.close();
        Collections.sort(scoreArray, Collections.reverseOrder());
        return scoreArray.get(0);
    }

    public void saveScore() throws IOException {
        String filename = "high_score.txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
        bufferedWriter.write(score + "\n");
        bufferedWriter.close();
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
            objects.add(new Garbage());
        }

        remainingAsteroids = N_INITIAL_ASTEROIDS + (level - 1) * 5;
        playerShip.reset();
        objects.add(playerShip);
        ships.add(playerShip);
        remainingGarbage = 0;
        int delay = ((int) (Math.random() * (10000 - 5000))) + 5000;

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
        } else{
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    objects.get(i).collisionHandling(objects.get(j));
                }
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
            if(addShield){
                addShield = false;
            }
            if (remainingAsteroids==0 && remainingGarbage ==0){
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
        if (o.getClass() == Garbage.class) {
            score += 100;
            remainingGarbage -= 1;
        }
        }

}

