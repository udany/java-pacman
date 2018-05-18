package pacman;

import engine.base.Vector;
import engine.main.GameObject;
import engine.sound.SoundEffect;
import engine.window.Game;
import pacman.objects.Dot;
import pacman.objects.Ghost;
import pacman.objects.PlayerObject;
import pacman.objects.Wall;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Pacman extends Game {
    public Score score;
    private Vector scorePosition = new Vector(16, 310);

    public Pacman() {
        super(320, 320);
        setTitle("Pacman");

        panel.setBackground(Color.BLACK);

        score = new Score();

        PlayerObject player = new PlayerObject();
        player.setPosition(16, 16);

        objectList.add(player);
        objectList.addAll(Wall.builder("/data/stage1.walls.csv"));
        objectList.addAll(Dot.builder("/data/stage1.dots.csv"));
        objectList.addAll(Ghost.builder("/data/stage1.ghosts.csv"));

        SoundEffect s = new SoundEffect("/sounds/pacman_beginning.wav");
        s.setVolume(.1);
        s.start();

        player.onDie.addListener(x -> {
            score.add(-1000);
            player.setPosition(16,16);
            player.respawn();
        });

        player.onEat.addListener(x -> {
            score.add(20);
        });
    }

    @Override
    protected void update() {
        super.update();
        List<GameObject> dotsLeft = objectList.stream().filter(x->x instanceof Dot).collect(Collectors.toList());

        if (dotsLeft.size() == 0){
            win();
        }
    }

    @Override
    protected void draw(Graphics2D graphics) {
        super.draw(graphics);
        score.draw(graphics, scorePosition);
    }

    private void win(){
        List<GameObject> ghosts = objectList.stream().filter(x->x instanceof Ghost).collect(Collectors.toList());

        for (GameObject o : ghosts) {
            ((Ghost)o).scare();
        }
    }
}
