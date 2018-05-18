package pacman;

import engine.base.Vector;

import java.awt.*;

public class Score {
    private int current;

    public void add(int score){
        current += score;
        if (current < 0){
            current = 0;
        }
    }

    public void draw(Graphics2D graphics, Vector p){
        graphics.drawString("Score: "+current, (float)p.x, (float)p.y);
    }
}
