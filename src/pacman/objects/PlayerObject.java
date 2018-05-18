package pacman.objects;

import engine.base.Size;
import engine.graphics.Sprite;
import engine.input.Keyboard;
import engine.main.MotionObject;
import engine.sound.SoundEffect;
import engine.sound.SoundEffectCollection;
import util.Event;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayerObject extends MotionObject {
    private double movementSpeed = 2;

    private List<Integer> keys = new ArrayList<>();

    private SoundEffectCollection bumpSound;
    private SoundEffectCollection eatSound;
    private SoundEffect dieSound;
    private SoundEffect respawnSound;

    private Sprite mainSprite;
    private Sprite dieSprite;

    public Event onDie = new Event();
    public Event onEat = new Event();

    public PlayerObject(){
        /// Sprite
        mainSprite = new Sprite(16, 16, "/images/pacman.png");
        mainSprite.setFramesPerFrame(5);

        dieSprite = new Sprite(16, 16, "/images/pacman_die.png");
        dieSprite.setFramesPerFrame(5);

        currentSprite = mainSprite;


        /// Sounds
        bumpSound = new SoundEffectCollection(
                "/sounds/ow1.wav",
                "/sounds/ow2.wav");
        bumpSound.setVolume(.05);

        eatSound = new SoundEffectCollection(
                "/sounds/wakka_wakka1.wav",
                "/sounds/wakka_wakka2.wav",
                "/sounds/wakka_wakka3.wav",
                "/sounds/wakka_wakka4.wav");
        eatSound.setVolume(.1);

        dieSound = new SoundEffect("/sounds/die.wav");
        dieSound.setVolume(.2);

        respawnSound = new SoundEffect("/sounds/respawn.wav");
        respawnSound.setVolume(.2);

        /// KeyEvents
        Keyboard kb = Keyboard.getInstance();
        kb.onKeyDown.addListener(code -> {
            switch (code){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyDown(code);
                    break;
            }
        });

        kb.onKeyUp.addListener(code -> {
            switch (code){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyUp(code);
                    break;
//                case KeyEvent.VK_ENTER:
//                    respawn();
//                    break;
            }
        });




        //// Collision
        size = new Size(15, 15);
        checkForCollisions = true;
        onCollision.addListener(obj -> {
            if (obj instanceof Wall){
                position.subtract(speed);
                speed.set(0,0);
                bumpSound.start();
            } else if (obj instanceof Dot){
                obj.destroy();
                eatSound.start();
                onEat.emit();
            } else if (obj instanceof Ghost){
                die();
            }
        });
    }

    private boolean dead = false;
    public void die(){
        if (dead) return;
        dead = true;

        dieSprite.setFrame(0);
        dieSprite.onAnimationEnd.clear();
        dieSprite.onAnimationEnd.addListener(x->{
            currentSprite = null;
            onDie.emit();
        });

        speed.set(0,0);

        dieSound.start();
        currentSprite = dieSprite;
    }

    public void respawn(){
        if (!dead) return;

        respawnSound.start();
        currentSprite = mainSprite;
        speed.set(0,0);
        alignToGrid(16, 16);

        dead = false;
    }

    private void movementKeyDown(int key){
        keys.add(key);
    }
    private void movementKeyUp(int key){
        int idx = keys.indexOf(key);
        if (idx >= 0){
            keys.remove(idx);
        }
    }

    private void updateDirection(){
        if (keys.size() > 0){
            setDirection(keys.get(keys.size()-1));
        }
    }

    private void setDirection(int key){
        if (dead) return;

        switch (key){
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
    public void update(){
        if (position.x % 16 == 0 && position.y % 16 == 0){
            updateDirection();
        }

        super.update();
    }
}
