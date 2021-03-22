package game2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static game2.Constants.*;

public class View extends JComponent {
    public static final Color BG_COLOR = Color.BLACK, TEXT_COLOR = Color.RED, TEXT_BG_COLOR = Color.YELLOW;
    private Game game;
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;

    public View(Game game) {
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchX = imWidth > FRAME_WIDTH ? 1 : FRAME_WIDTH / imWidth;
        double stretchY = imHeight > FRAME_HEIGHT ? 1 : FRAME_HEIGHT / imHeight;
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);

    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        //g.setColor(BG_COLOR);
        //g.fillRect(0, 0, getWidth(), getHeight() - SCORE_PANEL_HEIGHT);
        g.drawImage(im, bgTransf, null);
        synchronized (Game.class) {
            for (GameObject ob : game.objects)
                ob.draw(g);
        }
        g.setColor(TEXT_BG_COLOR);
        g.fillRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);
        g.setColor(TEXT_COLOR);
        g.drawRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);
        g.setFont(new Font("dialog", Font.BOLD, (2 * SCORE_PANEL_HEIGHT / 3)));
        g.drawString("Score " + Integer.toString(game.getScore()), 10, getHeight() - SCORE_PANEL_HEIGHT / 3);
        g.drawString("Lives " + Integer.toString(game.getLives()), 200, getHeight() - SCORE_PANEL_HEIGHT / 3);
        g.drawString("Level " + Integer.toString(game.getLevel()), 400, getHeight() - SCORE_PANEL_HEIGHT / 3);

    }

    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
