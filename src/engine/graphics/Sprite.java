package engine.graphics;

import engine.base.Vector;
import engine.base.Size;
import util.Event;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Sprite {
    protected BufferedImage image;
    protected Size size = new Size();
    protected int frameCount = 0;
    protected int state = 0;
    protected int stateCount = 0;
    protected int currentVirtualFrame = 0;

    public Event onAnimationEnd = new Event();

    public void setFramesPerFrame(int framesPerFrame) {
        this.framesPerFrame = framesPerFrame;
    }

    public void setState(int state) {
        this.state = state;
    }

    protected int framesPerFrame = 1;

    public int getWidth(){
        return size.width;
    }

    public int getHeight(){
        return size.height;
    }

    public void setImage(BufferedImage img) {
        image = img;
    }

    public int getStateCount(){
        if (stateCount != 0){
            return stateCount;
        }
        if (image != null){
            stateCount = image.getHeight() / getHeight();
            return stateCount;
        }
        return 0;
    }

    public int getFrameCount(){
        if (frameCount != 0){
            return frameCount;
        }
        if (image != null){
            frameCount = image.getWidth() / getWidth();
            return frameCount;
        }
        return 0;
    }

    public int getVirtualFrameCount(){
        return getFrameCount()*framesPerFrame;
    }

    public void nextFrame(){
        if (currentVirtualFrame == getVirtualFrameCount()-1){
            currentVirtualFrame = 0;
        }else{
            currentVirtualFrame++;
        }

        if (currentVirtualFrame == getVirtualFrameCount()-1){
            onAnimationEnd.emit();
        }
    }

    public void setFrame(int f){
        currentVirtualFrame = f * framesPerFrame;
    }

    public int getCurrentFrame(){
        return (int)Math.floor(currentVirtualFrame/framesPerFrame);
    }

    public Sprite(){}

    public Sprite(int w, int h, String file){
        size.width = w;
        size.height = h;
        setImage(file);
    }

    public void setImage(String file) {
        URL url = getClass().getResource(file);

        try{
            image = ImageIO.read(url);
        }catch (Exception e){
            System.out.println("Failed loading image "+url);
        }
    }

    public void draw (Graphics2D graphics, Vector p){
        nextFrame();

        Vector d1 = p.clone();
        Vector d2 = p.clone().add(size);

        int cFrame = getCurrentFrame();

        Vector s1 = new Vector(size.width * cFrame, size.height*state);
        Vector s2 = s1.clone().add(size);

        graphics.drawImage(image,
                (int)d1.x, (int)d1.y, (int)d2.x, (int)d2.y,
                (int)s1.x, (int)s1.y, (int)s2.x, (int)s2.y, null);
    }
}
