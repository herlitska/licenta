package ro.herlitska.attila.model.weapon;

import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSpriteFactory;

public class WeaponObject extends GameObject {

	private WeaponProperties properties;

	public WeaponObject(double x, double y, WeaponType weaponType) {
		super(x, y, GameSpriteFactory.getWeaponSprite(weaponType));
		properties = new WeaponProperties(weaponType);
		setSolid(false);
	}

	public WeaponProperties getProperties() {
		return properties;
	}

}
