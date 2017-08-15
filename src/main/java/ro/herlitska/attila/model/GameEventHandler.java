package ro.herlitska.attila.model;

import javafx.scene.input.KeyEvent;

public interface GameEventHandler {

    public void onKeyPressed(KeyEvent e);
    
    public void onKeyReleased(KeyEvent e);
}
