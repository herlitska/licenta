package ro.herlitska.attila.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyEvent;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameController implements GameEventHandler {

    final long startTime = System.nanoTime();

//    AnimationTimer gameLoop;

    private GameRoom room;

    private List<GameKeyCode> keysPressed = new ArrayList<>();
    private List<GameKeyCode> keysDown = new ArrayList<>();
    private List<GameKeyCode> keysReleased = new ArrayList<>();
   

    /*
    public GameController() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println("step");
                step();
            }
        };
    }
    */

//    public void startGame() {
//       gameLoop.start();
//        System.out.println("game started");
//    }

//    public void endGame() {
//        System.out.println("ending game");
//        gameLoop.stop();
//        System.out.println("game ended");
//    }

    @Override
    public synchronized void onKeyPressed(KeyEvent e) {
    	System.out.println("Pressed :" + e.getCode());
        switch (e.getCode()) {
        
        case A:
        	System.out.println("Pressed :" + e.getCode());
            keysDown.add(GameKeyCode.A);
            break;
        case S:
        	
            keysDown.add(GameKeyCode.S);
            break;
        case D:
        	
            keysDown.add(GameKeyCode.D);
            break;
        case W:
        	
            keysDown.add(GameKeyCode.W);
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

    // public synchronized void createObject(GameObject object) {
    // objects.add(object);
    // }
    @Override
    public void step() {
        // step
        room.stepEvent();
        
        
        // keyboard events
//        for (GameKeyCode key : keysPressed) {
//        	System.out.println("Pressed :" + key.name());
//            room.keyPressedEvent(key);
//            keysDown.add(key);
//        }
 //       keysPressed.clear();

        for (GameKeyCode key : keysDown) {
        	System.out.print("KeysDown :" + key.name());
            room.keyDownEvent(key);
            
        }
        
      
        keysDown.clear();
        for (GameKeyCode key : keysReleased) {
        	System.out.println("Released :" + key.name());
            room.keyReleasedEvent(key);
//            keysDown.remove(key);
            
        }
        
                
        keysReleased.clear();
//        keysDown.clear();
        

        // collision
        room.checkCollision();

        // draw
        room.drawEvent();
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }
}
