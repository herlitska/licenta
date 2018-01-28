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
			durability = 15;
			name = "Handgun";
			attackDelay = 40;
			this.weaponType = weaponType;
			break;
		case KNIFE:
			damage = 2;
			durability = 20;
			name = "Kitchen Knife";
			attackDelay = 20;
			this.weaponType = weaponType;
			break;
		case RIFLE:
			damage = 2;
			durability = 20;
			name = "Rifle";
			attackDelay = 10;
			this.weaponType = weaponType;
			break;
		case SHOTGUN:
			damage = 5;
			durability = 5;
			name = "Shotgun";
			attackDelay = 80;
			this.weaponType = weaponType;
			break;
		case FLASHLIGHT:
		    damage = 1;
            durability = 10;
            name = "Flashlight";
            attackDelay = 20;
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
