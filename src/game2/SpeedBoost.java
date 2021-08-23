package game2;

import utilities.Vector2D;

import java.awt.*;

import static game2.Constants.WORLD_HEIGHT;
import static game2.Constants.WORLD_WIDTH;

public class SpeedBoost extends GameObject{
    Sprite sprite;

    @Override
    public void update() {
        super.update();
    }

    public SpeedBoost(){
        super(new Vector2D(WORLD_WIDTH *Math.random(), WORLD_HEIGHT * Math.random()), new Vector2D(0,0), 10);
        double width = 35;
        Image im = Sprite.ROCKET;
        double height = width * im.getHeight(null) / im.getHeight(null);
        sprite = new Sprite(im, pos, dir, width, height);

    }

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() == PlayerShip.class;
    }

    @Override
    public void draw(Graphics2D g) {
        sprite.draw(g);
    }
}
