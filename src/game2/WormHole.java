package game2;

import utilities.SoundManager;
import utilities.Vector2D;
import static game2.Constants.WORLD_HEIGHT;
import static game2.Constants.WORLD_WIDTH;

import java.awt.*;

public class WormHole extends GameObject{
    Sprite sprite;


    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() == PlayerShip.class;
    }

    public WormHole(){
        super(new Vector2D(WORLD_WIDTH *Math.random(), WORLD_HEIGHT * Math.random()), new Vector2D(0,0), 10);
        double width = 50;
        Image im = Sprite.WORMHOLE;
        double height = width * im.getHeight(null) / im.getHeight(null);
        sprite = new Sprite(im, pos, dir, width, height);

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics2D g) {
        sprite.draw(g);
    }
}
