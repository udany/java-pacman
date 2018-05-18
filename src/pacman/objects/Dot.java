package pacman.objects;

import engine.base.Size;
import engine.base.Vector;
import engine.main.GameObject;
import util.CSVBuilder;

import java.awt.*;
import java.util.List;

public class Dot extends GameObject {

    private static Color color = new Color(250, 185, 176);

    public Dot(int x, int y) {
        position.x = x;
        position.y = y;
        size = new Size(16, 16);
        isSolid = true;
    }


    @Override
    public void update() { }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);

        Vector p = position.clone().add(7,7);

        graphics.fillRect((int) p.x, (int) p.y, 2, 2);
    }

    public static List<Dot> builder(String file) {
        return CSVBuilder.build(file, data-> new Dot(data.x * 16, data.y * 16));
    }
}
