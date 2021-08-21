package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import static game2.Constants.*;

public class PlayerShip extends Ship {
    public static final int RADIUS = 8;
    public static final double DRAWING_SCALE = 1.5;
    public boolean activeShield;
    public boolean isShield;
    public boolean isGarbage;
    public static final Color COLOR = Color.ORANGE;
    public int XP[] = { -6, 0, 6, 0 }, YP[] = { 8, 4, 8, -8 };
    public int XPTHRUST[] = { -5, 0, 5, 0 }, YPTHRUST[] = { 7, 3, 7, -7 };

    public PlayerShip(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2), new Vector2D(0, -1), RADIUS, ctrl, COLOR);
        dir = new Vector2D(0,-1);
    }

    @Override
    public void update() {
        super.update();
    }

    public void reset(){
        pos.set(new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2));
        vel.set(new Vector2D(0,0));
        dir.set(new Vector2D(0, -1));
        dead = false;

    }




    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(pos.x, pos.y);
        double rot = dir.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (thrusting) {
            g.setColor(Color.BLUE);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }


    @Override
    public boolean canHit(GameObject other) {
        if (activeShield){
            return false;
        }
       return other.getClass() == Shield.class || other.getClass() == Asteroid.class || other.getClass() == Garbage.class;
    }

    @Override
    public void hit() {
        if (isShield){
            isShield = false;
            activeShield = true;
            if (SoundManager.powerUp != null)
                SoundManager.play(SoundManager.powerUp);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("timer deactivated");
                    activeShield = false;
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 5000);
            return;
        }
        System.out.println(isGarbage);
        if (isGarbage){
            isGarbage = false;
            if (SoundManager.garbage != null){
                SoundManager.play(SoundManager.garbage);
            }
            return;
        }

        if (deathSound != null && !activeShield){
            System.out.println("player death");
            SoundManager.play(deathSound);
        }
        this.dead = true;
        }
}
