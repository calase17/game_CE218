package game2;

import utilities.Vector2D;

import java.awt.*;

public class Bullet extends GameObject {
    private double lifetime;
    public static final int RADIUS = 2;
    public static final int BULLET_LIFE = 50;
    public static final int INITIAL_SPEED = 100;

    boolean firedByPlayerShip;

    public Bullet(Vector2D pos, Vector2D vel, boolean byPlayerShip) {
        super(pos, vel, RADIUS);
        this.lifetime = BULLET_LIFE;
        firedByPlayerShip = byPlayerShip;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= 1;
        if (lifetime <= 0) dead = true;
    }

    @Override
    public void draw(Graphics2D g)
        {g.setColor(Color.WHITE);
            g.fillOval((int) pos.x-RADIUS, (int) pos.y-RADIUS, 2*RADIUS, 2*RADIUS);


        }

    @Override
    public boolean canHit(GameObject other) {
        return firedByPlayerShip || other.getClass() == PlayerShip.class || other.getClass() == Bullet.class;
    }

    @Override
    public void hit() {
        dead = true;

    }
}
