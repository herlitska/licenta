package ro.herlitska.attila.controller;

import java.util.HashMap;
import java.util.Map;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameController implements GameEventHandler {

    private GameRoom room;
    
    private Map<GameKeyCode, Boolean> keysDown = new HashMap<>();    
    
    public GameController() {
        for (GameKeyCode keyCode : GameKeyCode.values()) {
            keysDown.put(keyCode, false);
        }        
    }
    
    @Override
    public void keyPressed(GameKeyCode keyCode) {
        keysDown.put(keyCode, true);
    }
    
    @Override
    public void keyReleased(GameKeyCode keyCode) {
        keysDown.put(keyCode, false);
    }

    @Override
    public void step() {
        // step
        room.stepEvent();
        
        keysDown.forEach((key, down) -> {
            if (down) {
                room.keyDownEvent(key);
            }
        });

        // collision
        room.checkCollision();

        // draw
        room.drawEvent();
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }
}
