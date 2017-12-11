package ro.herlitska.attila.model;

public class WeaponItem extends InventoryItem {

	private int damage;
	private int durability;
	private WeaponType type;
	private int shootTime = 0;
	private int delay;

	public WeaponItem(String name, WeaponType weaponType, int damage, int durability, GameSprite sprite) {
		super(name, sprite);
		this.type = weaponType;
		this.damage = damage;
		this.durability = durability;

		switch (weaponType) {
		case HANDGUN:
			delay = 40;
			break;
		case RIFLE:
			delay = 10;
			break;
		case SHOTGUN:
			delay = 120;
			break;
		default:
			delay = 0;
			break;
		}

	}

	public int getDamage() {
		return damage;
	}

	public int getDurability() {
		return durability;
	}

	public WeaponType getType() {
		return type;
	}

	public void resetDelayCounter() {
		shootTime = 0;
	}

	public void stepEvent() {

		shootTime++;

	}

	public boolean canShoot() {
		return shootTime >= delay;
	}
}
