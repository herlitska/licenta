package ro.herlitska.attila.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyEvent;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameKeyCode;
import ro.herlitska.attila.util.Utils;

public class GameController implements Runnable {

    private boolean running = false;
    private Thread thread;

    private List<GameObject> objects;

    private final double COLLISION_DIST = 10.0;

    private List<GameKeyCode> keysPressed = new ArrayList<>();
    private List<GameKeyCode> keysDown = new ArrayList<>();
    private  List<GameKeyCode> keysReleased = new ArrayList<>();

    public GameController(List<GameObject> objects) {
        this.objects = objects;
    }

    public synchronized void startGame() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void endGame() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
        case A:
            keysPressed.add(GameKeyCode.A);
            break;
        case S:
            keysPressed.add(GameKeyCode.S);
            break;
        case D:
            keysPressed.add(GameKeyCode.D);
            break;
        case W:
            keysPressed.add(GameKeyCode.W);
            break;
        default:
            break;
        }
    }
    
    public synchronized void onKeyReleased(KeyEvent e) {
        switch (e.getCode()) {
        case A:
            keysReleased.add(GameKeyCode.A);
            break;
        case S:
            keysReleased.add(GameKeyCode.S);
            break;
        case D:
            keysReleased.add(GameKeyCode.D);
            break;
        case W:
            keysReleased.add(GameKeyCode.W);
            break;
        default:
            break;
        }
    }

    public synchronized void createObject(GameObject object) {
        objects.add(object);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta > 1) {
                step();
                delta--;
            }
        }
    }

    private void step() {
        // step
        objects.forEach(GameObject::stepEvent);

        // keyboard events
        for (GameKeyCode key : keysPressed) {
            for (GameObject object : objects) {
                object.keyPressedEvent(key);
            }
        }
        keysPressed.clear();
        
        for (GameKeyCode key : keysDown) {
            for (GameObject object : objects) {
                object.keyDownEvent(key);
            }
        }
        
        for (GameKeyCode key : keysReleased) {
            for (GameObject object : objects) {
                object.keyReleasedEvent(key);
            }
        }
        keysReleased.clear();
        keysDown.clear();

        // collision
        for (int i = 0; i < objects.size() - 1; i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject first = objects.get(i);
                GameObject second = objects.get(j);
                if (Utils.dist(first.getX(), second.getX(), first.getY(), second.getY()) < COLLISION_DIST) {
                    first.collisionEvent(second);
                    second.collisionEvent(first);
                }
            }
        }

        // draw
        objects.forEach(GameObject::drawEvent);
    }

    
}
