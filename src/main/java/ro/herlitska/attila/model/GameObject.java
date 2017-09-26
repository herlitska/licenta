package ro.herlitska.attila.model;

public abstract class GameObject {

    private double x;
    private double y;
    private double direction;
    private double speed;

    private ObjectSprite sprite;
    private boolean visible = true;

    private GameRoom room;

    public GameObject(double x, double y, ObjectSprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void stepEvent() {
        sprite.stepEvent();
    }

    public void drawEvent() {
        if (visible) {
//            room.getView().draw(sprite.getImage(), x, y);
        	room.getView().drawRect(x, y);
        	
        }
    }
    
    
    public void collisionEvent(GameObject other) {

    }
    
    public void keyPressedEvent(GameKeyCode key) {
        
    }
    
    public void keyReleasedEvent(GameKeyCode key) {
        
    }
    
    public void keyDownEvent(GameKeyCode key) {
        
    }

    // Getters and setters

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ObjectSprite getSprite() {
        return sprite;
    }

    public void setSprite(ObjectSprite sprite) {
        this.sprite = sprite;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }
    
    public GameRoom getRoom() {
        return room;
    }
    
}
