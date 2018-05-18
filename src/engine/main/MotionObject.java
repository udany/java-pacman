package engine.main;

import engine.base.Vector;

public abstract class MotionObject extends GameObject {
    protected Vector speed = new Vector(0,0);

    @Override
    public void update() {
        position.add(speed);
    }


}
