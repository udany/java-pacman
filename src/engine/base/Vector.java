package engine.base;

public class Vector {
    public double x;
    public double y;

    public Vector(){}
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector clone(){
        return new Vector(x, y);
    }

    public Vector add(double x, double y){
        this.x += x;
        this.y += y;

        return this;
    }
    public Vector add(Vector p){
        x += p.x;
        y += p.y;

        return this;
    }
    public Vector add(Size size){
        x += size.width;
        y += size.height;
        return this;
    }


    public Vector subtract(Vector p){
        x -= p.x;
        y -= p.y;

        return this;
    }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }
}
