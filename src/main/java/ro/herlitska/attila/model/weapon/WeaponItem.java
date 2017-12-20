package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.model.InventoryItem;

public class WeaponItem extends InventoryItem {

	private WeaponProperties properties;
	
	private int remainingDurability;
	private int attackDelayCounter = 0;	
	
	public static WeaponItem createWeaponItem(WeaponProperties properties) {
		WeaponItem weaponItem;
		if (properties.getWeaponType() == WeaponType.HANDGUN) {
			weaponItem = new MeleeWeaponItem(properties);
		} else {
			weaponItem = new BallisticWeaponItem(properties);
		}
		weaponItem.remainingDurability = properties.getDurability();
		return weaponItem;
	}
	
	protected WeaponItem(WeaponProperties properties) {
		super(properties.getName(), GameSpriteFactory.getInventoryWeaponSprite(properties.getWeaponType()));
		this.properties = properties;
	}

	public WeaponProperties getProperties() {
		return properties;
	}

	public int getRemainingDurability() {
		return remainingDurability;
	}
	
	protected void decreaseDurability() {
		remainingDurability--;
	}
	
	protected void resetAttackDelayCounter() {
		attackDelayCounter = 0;
	}

	@Override
	public void stepEvent() {
		attackDelayCounter++;
	}

	public boolean canAttack() {
		return attackDelayCounter >= properties.getAttackDelay();
	}
	
}
