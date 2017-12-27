package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.Damagable;
import ro.herlitska.attila.model.GameSpriteFactory;

public class MeleeWeaponItem extends WeaponItem {

    private boolean hitting = false;
    private double attackRange;

    public MeleeWeaponItem(WeaponProperties properties) {
        super(properties);
        switch (getProperties().getWeaponType()) {
        case KNIFE:
            attackRange = GameSpriteFactory.PLAYER_RADIUS;
            ;
            break;
        default:
            break;
        }
    }

    @Override
    public void stepEvent() {
        super.stepEvent();
        hitting = false;
    }

    public boolean hit() {
        if (canAttack()) {
            hitting = true;
            resetAttackDelayCounter();
            return true;
        } else {
            return false;
        }
    }

    public void damageEnemy(Damagable enemy) {
        if (hitting) {
            decreaseDurability();
            enemy.damage(getProperties().getDamage());
        }
    }

    public double getAttackRange() {
        return attackRange;
    }
}
