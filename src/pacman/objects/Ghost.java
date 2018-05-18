package pacman.objects;

import engine.base.Size;
import engine.graphics.Sprite;
import engine.main.MotionObject;
import util.CSVBuilder;

import java.awt.event.KeyEvent;
import java.util.List;

public class Ghost extends MotionObject {
    private static double movementSpeed = 1.5;
    private static double chaos = .3;
    private static int[] directions = new int[] {KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};

    private static String[] sprites = new String[] {
            "/images/ghost_red.png",
            "/images/ghost_pink.png",
            "/images/ghost_blue.png",
            "/images/ghost_orange.png",
    };
    private static int currentSpriteIndex = 0;

    private static String getSprite(){
        String s = sprites[currentSpriteIndex];

        currentSpriteIndex = (currentSpriteIndex+1) % sprites.length;

        return s;
    }

    private Sprite scaredSprite;

    public Ghost(int x, int y){
        position.x = x;
        position.y = y;

        currentSprite = new Sprite(16, 16, getSprite());
        currentSprite.setFramesPerFrame(10);

        scaredSprite = new Sprite(16, 16, "/images/ghost_scare.png");
        scaredSprite.setFramesPerFrame(10);

        move();

        //// Collision
        size = new Size(16, 16);
        checkForCollisions = true;
        isSolid = true;
        onCollision.addListener(obj -> {
            if (obj instanceof Wall){
                position.subtract(speed);
                speed.set(0,0);
                move();
            }
        });
    }

    private void move(){
        move(directions[(int)Math.round(Math.random() * (directions.length-1))]);
    }

    private void move(int direction){
        switch (direction){
            case KeyEvent.VK_LEFT:
                speed.x = -movementSpeed;
                speed.y = 0;
                currentSprite.setState(1);
                break;
            case KeyEvent.VK_RIGHT:
                speed.x = movementSpeed;
                speed.y = 0;
                currentSprite.setState(0);
                break;
            case KeyEvent.VK_UP:
                speed.x = 0;
                speed.y = -movementSpeed;
                currentSprite.setState(2);
                break;
            case KeyEvent.VK_DOWN:
                speed.x = 0;
                speed.y = movementSpeed;
                currentSprite.setState(3);
                break;
        }
    }

    @Override
    public void update() {
        if (scared) return;

        super.update();

        if (position.x % 16 == 0 && position.y % 16 == 0){
            if (Math.random() <= chaos){
                move();
            }
        }
    }

    private boolean scared = false;
    public void scare(){
        scared = true;
        currentSprite = scaredSprite;
        speed.set(0, 0);
    }

    public static List<Ghost> builder(String file) {
        return  CSVBuilder.build(file, data -> new Ghost(data.x * 16, data.y * 16));
    }
}
