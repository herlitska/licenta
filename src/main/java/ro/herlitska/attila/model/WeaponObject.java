package ro.herlitska.attila.model;

public class WeaponObject extends GameObject {

	private WeaponProperties properties;

	public WeaponObject(double x, double y, WeaponType weaponType) {
		super(x, y, GameSpriteFactory.getWeaponSprite(weaponType));
		properties = new WeaponProperties(weaponType);
	}

	public WeaponProperties getProperties() {
		return properties;
	}

}
