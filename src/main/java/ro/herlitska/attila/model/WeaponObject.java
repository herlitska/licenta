package ro.herlitska.attila.model;

import ro.herlitska.attila.model.GameSpriteFactory.WeaponType;

public class WeaponObject extends GameObject {

	private String name;
	private int damage;
	private int durability;
	private WeaponType weaponType;

	public WeaponObject(double x, double y, int damage, int durability, String name, GameSprite sprite,
			WeaponType weaponType) {
		super(x, y, sprite);
		this.damage = damage;
		this.durability = durability;
		this.name = name;
		this.weaponType = weaponType;
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

}
