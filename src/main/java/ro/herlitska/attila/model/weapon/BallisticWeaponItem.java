package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.model.Player;

public class BallisticWeaponItem extends WeaponItem {

    public BallisticWeaponItem(WeaponProperties properties) {
        super(properties);
    }

    public boolean fireBullet(Player weaponOwner) {
        if (canAttack()) {
            resetAttackDelayCounter();
            decreaseDurability();
            Bullet bullet = new Bullet(weaponOwner.getX(), weaponOwner.getY(), GameSpriteFactory.getBulletSprite(),
                    getProperties().getDamage());
            bullet.setDirection(weaponOwner.getAngle());
            bullet.setAngle(weaponOwner.getAngle());
            bullet.setRoom(weaponOwner.getRoom());
            weaponOwner.getRoom().createObject(bullet);
        }
        
        return canAttack();
    }
}
