package engine.main;

import engine.base.*;
import engine.graphics.Sprite;
import engine.window.Game;
import util.Event;
import util.EventData;

import java.awt.*;

public abstract class GameObject implements IObject {
    protected Size size = new Size();
    protected Vector position = new Vector();
    protected Sprite currentSprite;

    public int getWidth(){
        return size.width;
    }

    public int getHeight(){
        return size.height;
    }

    public double getX(){
        return position.x;
    }

    public double getY(){
        return position.y;
    }

    public GameObject setX(double x){
        position.x = x;
        return this;
    }
    public GameObject setY(double y){
        position.y = y;
        return this;
    }
    public GameObject setPosition(double x, double y){
        setX(x);
        setY(y);
        return this;
    }
    public GameObject alignToGrid(double x, double y){
        setX(
                Math.round((getX()/x))*x
        );
        setY(
                Math.round((getY()/y))*y
        );

        return this;
    }

    public abstract void update();

    public void draw (Graphics2D graphics){
        if (currentSprite != null){
            currentSprite.draw(graphics, position);
        }
    }

    public Shape getCollisionArea(){
        return new Rectangle((int)position.x, (int)position.y, size.width, size.height);
    }

    public Event<GameObject> onCollision = new Event<>();

    protected boolean checkForCollisions = false;

    /**
     * Means it should check for collisions with other objects
     * @return
     */
    public boolean checkForCollisions(){
        return checkForCollisions;
    }

    protected boolean isSolid = false;

    /**
     * Means another object can collide with this
     * @return
     */
    public boolean isSolid(){
        return isSolid;
    }

    public Event<EventData> onDestroy = new Event<>();

    public boolean isDestroyed() {
        return destroyed;
    }

    private boolean destroyed = false;
    public void destroy(){
        destroyed = true;
        onDestroy.emit();
    }

}
