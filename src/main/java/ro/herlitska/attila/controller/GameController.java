package ro.herlitska.attila.controller;

import java.util.List;

import javafx.scene.input.KeyEvent;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.util.Utils;

public class GameController implements Runnable {

    private boolean running = false;
    private Thread thread;

    private List<GameObject> objects;
    
    private final double COLLISION_DIST = 10.0;

    public GameController() {

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
        objects.forEach(GameObject::stepEvent);
        
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
        
        objects.forEach(GameObject::drawEvent);
    }
}
