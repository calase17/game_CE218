package game2;

//import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

public abstract class Ship extends GameObject{
    public static final double STEER_RATE = Math.PI/20;
    public static final double MAG_ACC = 200;

    public static final double DRAG = 0.01;
    public static final long SHOT_DELAY=500;
    public long timeLastShot = System.currentTimeMillis();
    public Bullet bullet = null;
    public boolean thrusting = false;
    public Color colour;
    public Controller ctrl;
    public Vector2D dir;

    public Ship(Vector2D pos, Vector2D vel, double radius, Controller ctrl, Color col){
        super(pos, vel, radius);

        dir = new Vector2D(0,-1);
        this.ctrl = ctrl;
        this.colour = col;
    }

    protected void mkBullet(){
        bullet = new Bullet(new Vector2D(pos), new Vector2D(vel), this.getClass() == PlayerShip.class);
        // ensure bullet is clear of ship to avoid collision
        bullet.pos.addScaled(dir, (radius+bullet.radius)*1.1);
        bullet.vel.addScaled(dir, Bullet.INITIAL_SPEED);

    }

    public void update() {
        super.update();
        Action action = ctrl.action();
        dir.rotate(action.turn * STEER_RATE);
        vel.addScaled(dir, action.thrust * MAG_ACC * Constants.DT);
        vel.mult(1-DRAG);
        long timeNow = System.currentTimeMillis();
        if (action.shoot && timeNow - timeLastShot > SHOT_DELAY) {
            mkBullet();
            action.shoot = false;
            timeLastShot = timeNow;
            //SoundManager.fire();
        }
        thrusting=action.thrust!=0;

    }

}
