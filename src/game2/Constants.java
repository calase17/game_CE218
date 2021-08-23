package game2;
import utilities.ImageManager;
import utilities.JEasyFrameFull;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static int FRAME_HEIGHT = 500;
    public static int FRAME_WIDTH = 1000;
    public static final int SCORE_PANEL_HEIGHT = 30;
    public static int WORLD_FACTOR = 1;
    public static int WORLD_WIDTH = WORLD_FACTOR * FRAME_WIDTH;
    public static int WORLD_HEIGHT = WORLD_FACTOR * FRAME_HEIGHT;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT+SCORE_PANEL_HEIGHT);
    public static final int DELAY = 20; // milliseconds
    public static final double DT = DELAY/1000.0;  // delay in seconds
    public static Image ASTEROID1, MILKYWAY1,SHIELD,GARBAGE, WORMHOLE, ROCKET;

    static{
        try{
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("galaxy");
            SHIELD = ImageManager.loadImage("shield");
            GARBAGE = ImageManager.loadImage("garbage");
            WORMHOLE = ImageManager.loadImage("wormhole");
            ROCKET = ImageManager.loadImage("rocket");
        }
        catch (IOException e){
            e.printStackTrace();

        }
    }

    public static void setFullScreenDimensions() {
        FRAME_HEIGHT = JEasyFrameFull.HEIGHT;
        FRAME_WIDTH = JEasyFrameFull.WIDTH;
        WORLD_WIDTH = WORLD_FACTOR * FRAME_WIDTH;
        WORLD_HEIGHT = WORLD_FACTOR * FRAME_HEIGHT;
    }


}
