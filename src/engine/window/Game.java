package engine.window;

import engine.base.Size;
import engine.input.Keyboard;
import engine.main.GameObject;
import engine.window.GamePanel;
import util.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Game extends MyFrame {
    public Size size = new Size();

    protected GamePanel panel;

    protected List<GameObject> objectList;


    //classe keyboard só pode ser instanciada uma vez

    protected Keyboard keyboard = Keyboard.getInstance();

    public Game(int w, int h){
        super();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        size.height = h;
        size.width = w;
        setSize(size.width, size.height);
        setResizable(false);
        centerOnScreen();

        panel = new GamePanel(size);
        add(panel);
        pack();

        objectList = new ArrayList<>();

        panel.onPaint.addListener(g -> {
            update();
            draw(g);
        });
    }

    protected void update(){
        List<GameObject> objectsToRemove = objectList.stream().filter(x->x.isDestroyed()).collect(Collectors.toList());

        for(GameObject o : objectsToRemove) {
            objectList.remove(o);
        }

        for(GameObject o : objectList){
            o.update();
        }
        collisionChecking();
    }

    protected void collisionChecking(){
        List<GameObject> objectsToCheck = objectList.stream().filter(x->x.checkForCollisions()).collect(Collectors.toList());
        List<GameObject> solidObjects = objectList.stream().filter(x->x.isSolid()).collect(Collectors.toList());

        for (GameObject obj : objectsToCheck){
            for (GameObject solidObj : solidObjects){
                Shape objShape = obj.getCollisionArea();
                Shape solidShape = solidObj.getCollisionArea();

                if (objShape.intersects(solidShape.getBounds())){
                    obj.onCollision.emit(solidObj);
                }
            }
        }
    }

    protected void draw(Graphics2D graphics){
        for(GameObject o : objectList){
            o.draw(graphics);
        }
    }
}
