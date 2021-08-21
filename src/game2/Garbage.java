package game2;


import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game2.Constants.WORLD_HEIGHT;
import static game2.Constants.WORLD_WIDTH;

public class Garbage extends GameObject {
    Sprite sprite;

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() == PlayerShip.class;
    }

    public Garbage(){
        super(new Vector2D(WORLD_WIDTH *Math.random(), WORLD_HEIGHT * Math.random()), new Vector2D(0,0), 8);
        double width = 35;
        Image im = Sprite.GARBAGE;
        garbageSound = SoundManager.garbage;
        double height = width * im.getHeight(null) / im.getHeight(null);
        sprite = new Sprite(im, pos, dir, width, height);
    }

    @Override
    public void update() {
        super.update();
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }
}
