package pacman.objects;

import engine.base.Size;
import engine.graphics.Tileset;
import engine.main.GameObject;
import util.CSVBuilder;

import java.awt.*;
import java.util.List;

public class Wall extends GameObject {
    private static Tileset tileset = new Tileset(16, 16, "/images/wall.png");

    private int index = 0;
    public static boolean debug = false;

    public Wall(int x, int y, int index) {
        position.x = x;
        position.y = y;
        size = new Size(16, 16);
        isSolid = true;
        this.index = index;
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        tileset.draw(graphics, position, index);

        if (debug) {
            graphics.setColor(new Color(0, 0, 255, 50));

            graphics.fillRect((int) position.x, (int) position.y, size.width, size.height);
        }
    }

    public static List<Wall> builder(String file) {
        return  CSVBuilder.build(file, data -> new Wall(data.x * 16, data.y * 16, data.v));
    }
}
