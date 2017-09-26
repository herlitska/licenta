package ro.herlitska.attila.model;

public interface GameEventHandler {

    public void keyPressed(GameKeyCode e);

    public void keyReleased(GameKeyCode keyCode);

    public void step();
}
