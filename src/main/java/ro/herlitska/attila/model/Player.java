package ro.herlitska.attila.model;

public class Player extends GameObject {
    
    public Player(double x, double y, ObjectSprite sprite) {
        super(x, y, sprite);
    }    
    
    @Override
    public void keyDownEvent(GameKeyCode key) {
        switch (key) {
        case A:
            setX(getX() - 2);
            break;
        case D:
            setX(getX() + 2);
           
            break;
        case W:
            setY(getY() - 2);
           
            break;
        case S:
            setY(getY() + 2);
            
            break;
        default:
            break;
        }
    }
      
 
    
    
}
