package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.Damagable;
import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.model.Player;
import ro.herlitska.attila.model.GameSpriteFactory.PlayerMotion;

public class MeleeWeaponItem extends WeaponItem {

	private boolean hitting = false;
	private double attackRange;

	public MeleeWeaponItem(WeaponProperties properties) {
		super(properties);
		switch (getProperties().getWeaponType()) {
		case KNIFE:
			attackRange = GameSpriteFactory.PLAYER_RADIUS + GameSpriteFactory.ZOMBIE_RADIUS + 25;
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

	public void hit(Player weaponOwner) {
		weaponOwner.setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, getProperties().getWeaponType()));
		hitting = true;
		resetAttackDelayCounter();
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
