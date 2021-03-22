package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Saucer extends Ship{
    public static final int HEIGHT = 12;
    public static final int WIDTH = 24;
    public static final int WIDTH_ELLIPSE = 20;

    public Color colorBelt;

    public Saucer(Vector2D s, Controller ctrl, Color colorBody, Color colorBelt) {
        super(s, new Vector2D(), (WIDTH + HEIGHT) / 4.0, ctrl, colorBody);
        dir.set(Vector2D.polar(Math.random()*2*Math.PI, 1));
        deathSound = SoundManager.bangMedium;
        this.colorBelt = colorBelt;
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(pos.x, pos.y);
        Ellipse2D ellipse = new Ellipse2D.Double(-WIDTH_ELLIPSE / 2.0,
                -HEIGHT / 2.0, WIDTH_ELLIPSE, HEIGHT);
        g.setColor(colour);
        g.fill(ellipse);
        g.setColor(colorBelt);
        g.drawLine(-WIDTH / 2, 0, WIDTH / 2, 0);
        g.setTransform(at);
    }


    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip ||
                other instanceof Bullet && ((Bullet) other).firedByPlayerShip;
    }

    @Override
    public String toString() {
        return "Saucer[" + pos + ", " + vel + ", " + dir + "]";
    }

}
