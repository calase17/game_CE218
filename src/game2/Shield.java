package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game2.Constants.WORLD_HEIGHT;
import static game2.Constants.WORLD_WIDTH;

public class Shield extends GameObject{
    Sprite sprite;

    public Shield(double x, double y, double vx, double vy, Sprite sprite) {
        super(new Vector2D(x, y),
                new Vector2D(vx, vy), sprite.getRadius());
    }

    public Shield(){
        super(new Vector2D(WORLD_WIDTH *Math.random(), WORLD_HEIGHT * Math.random()), new Vector2D(0,0), 10);
        double width = 35;
        Image im = Sprite.SHIElD_POWERUP;
        shieldSound = SoundManager.powerUp;
        double height = width * im.getHeight(null) / im.getHeight(null);
        sprite = new Sprite(im, pos, dir, width, height);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean canHit(GameObject other) {

        return other.getClass() == PlayerShip.class;
    }



    public void draw(Graphics2D g) {
        sprite.draw(g);
    }
}
