package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.Damagable;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;

public class Bullet extends GameObject {

    private double damage;
    
    public Bullet(double x, double y, GameSprite sprite, double damage) {
        super(x, y, sprite);
        this.damage = damage;
        this.setSpeed(50);
        getSprite().setAnimationSpeed(2);
    }

    @Override
    public void stepEvent() {
        super.stepEvent();
        move();
    }
    
    @Override
    public void collisionEvent(GameObject other) {
        if (other instanceof Damagable && other.isSolid()) {
            ((Damagable) other).damage(damage);
            destroy();
        }
    }

    @Override
    public void drawEvent() {
        
    }

}
