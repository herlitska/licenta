package ro.herlitska.attila.model;

public class Player extends GameObject {
    
    public Player(double x, double y, ObjectSprite sprite) {
        super(x, y, sprite);
    }    
    
    @Override
    public void keyDownEvent(GameKeyCode key) {
        switch (key) {
        case A:
            setX(getX() - 5);
            getRoom().getView().drawEvent();
            break;
        case D:
            setX(getX() + 5);
            break;
        case W:
            setY(getY() - 5);
            break;
        case S:
            setY(getY() + 5);
            break;
        default:
            break;
        }
    }   
    
}
