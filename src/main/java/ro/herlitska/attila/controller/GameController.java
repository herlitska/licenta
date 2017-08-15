package ro.herlitska.attila.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyEvent;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameController implements Runnable, GameEventHandler {

    private boolean running = false;
    private Thread thread;

    private GameRoom room;

    private List<GameKeyCode> keysPressed = new ArrayList<>();
    private List<GameKeyCode> keysDown = new ArrayList<>();
    private  List<GameKeyCode> keysReleased = new ArrayList<>();

    public GameController() {
    }
    
    public synchronized void startGame() {
        thread = new Thread(this);
        thread.start();
        running = true;
        System.out.println("game started");
    }

    public synchronized void endGame() {
        try {
            System.out.println("ending game");
            running = false;
            thread.join();            
            System.out.println("game ended");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
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
    
    @Override
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

//    public synchronized void createObject(GameObject object) {
//        objects.add(object);
//    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {
//            System.out.println("running");
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
        room.stepEvent();       

        // keyboard events
        for (GameKeyCode key : keysPressed) {
            room.keyPressedEvent(key);
        }
        keysPressed.clear();
        
        for (GameKeyCode key : keysDown) {
            room.keyDownEvent(key);
        }
        
        for (GameKeyCode key : keysReleased) {
            room.keyReleasedEvent(key);
        }
        keysReleased.clear();
        keysDown.clear();

        // collision
        room.checkCollision();

        // draw
        room.drawEvent();
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }
}
