package ro.herlitska.attila.model.weapon;

public class WeaponProperties {

	private String name;
	private int damage;
	private int durability;
	private int attackDelay;
	private WeaponType weaponType;

	public WeaponProperties(WeaponType weaponType) {
		switch (weaponType) {
		case HANDGUN:
			damage = 1;
			durability = 10;
			name = "Handgun";
			attackDelay = 40;
			this.weaponType = weaponType;
			break;
		case KNIFE:
			damage = 1;
			durability = 10;
			name = "Kitchen Knife";
			attackDelay = 10;
			this.weaponType = weaponType;
			break;
		case RIFLE:
			damage = 1;
			durability = 10;
			name = "Rifle";
			attackDelay = 10;
			this.weaponType = weaponType;
			break;
		case SHOTGUN:
			damage = 1;
			durability = 10;
			name = "Shotgun";
			attackDelay = 120;
			this.weaponType = weaponType;
			break;
		default:
			break;
		}
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public int getDurability() {
		return durability;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public int getAttackDelay() {
		return attackDelay;
	}

}
