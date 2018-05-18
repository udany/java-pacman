package engine.window;

import engine.base.Size;
import util.Event;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    private Size size;
    private Color background;
    public Event<Graphics2D> onPaint = new Event<>();
    private final int frameRate = 60;
    private Timer clock;

    GamePanel(Size s) {
        setLayout(null);

        if (s!=null){
            size = s;
        }
        background = Color.BLACK;
        clock = new Timer();

        int msPerFrame = 1000/frameRate;

        clock.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, msPerFrame, msPerFrame);
    }

    public void setBackground(Color color){
        background = color;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size.width, size.height);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(background);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        onPaint.emit((Graphics2D)graphics);
    }
}