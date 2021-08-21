package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game2.Constants.*;


public class Asteroid extends GameObject {
    public static final double VMIN = 100, VMAX = 150;
    public Sprite sprite;
    public double rotationPerFrame;


    public Asteroid(double x, double y, double vx, double vy, Sprite sprite) {
        super(new Vector2D(x, y),
                new Vector2D(vx, vy), sprite.getRadius());
    }

    public Asteroid() {
        super(new Vector2D(WORLD_WIDTH*Math.random(), WORLD_HEIGHT+Math.random()), new Vector2D(0,0), 0);
        double speed = VMIN+(VMAX-VMIN)*Math.random();
        double angle = Math.random() * 2 * Math.PI;
        vel.set(new Vector2D(speed*Math.cos(angle), speed*Math.sin(angle)));
        deathSound = SoundManager.bangSmall;
        rotationPerFrame = Math.random() * 0.1;
        double width = Math.min(Math.max(20 + new Random().nextGaussian()*30, 30), 50);

        // keep aspect ratio of original image
        Image im = Sprite.ASTEROID1;
        double height = width * im.getHeight(null)/im.getWidth(null);
        double direction = Math.random() * 2 * Math.PI;
        dir = new Vector2D(Math.cos(direction), Math.sin(direction));
        sprite = new Sprite(im, pos, dir, width, height);

    }

    public void draw(Graphics2D g) {
        //g.setColor(Color.RED);
        //g.fillOval((int) pos.x - radius, (int) pos.y - radius, 2 * radius, 2 * radius);
        sprite.draw(g);
    }

    @Override
    public void update() {
        super.update();
        dir.rotate(rotationPerFrame);
    }

    @Override
    public boolean canHit(GameObject other) {

        return other.getClass() == PlayerShip.class || other.getClass() == Bullet.class;
    }
}
