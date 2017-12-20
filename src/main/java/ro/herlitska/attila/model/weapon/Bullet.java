package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;

public class Bullet extends GameObject {

    double damage;
    
    public Bullet(double x, double y, GameSprite sprite, double damage) {
        super(x, y, sprite);
        this.setSpeed(50);
        getSprite().setAnimationSpeed(2);
    }

    @Override
    public void stepEvent() {
        super.stepEvent();
        move();
    }

    @Override
    public void drawEvent() {
        // getRoom().getView().drawText(String.valueOf(getDirection()), getX() -
        // 50, getY() - 50, 20);
    }

}
