package ro.herlitska.attila.model.weapon;

import java.util.Random;

import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.util.Utils;

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
	
	public static WeaponObject getNewRandomWeaponObject(double playerX, double playerY) {
		WeaponType randWeaponType;
		do {
			int randWeaponTypeIndex = (int) Math.floor(WeaponType.values().length * Math.random());
			randWeaponType = WeaponType.values()[randWeaponTypeIndex];
		} while (randWeaponType == WeaponType.FLASHLIGHT);
		
		WeaponObject weaponObject = new WeaponObject(0, 0, randWeaponType);
		double newWeaponX;
		double newWeaponY;
		Random rand = new Random();
		do {
			newWeaponX = GameRoom.MARGIN_SIZE + 100
					+ rand.nextInt((int) (GameRoom.ROOM_SIZE - 2 * (GameRoom.MARGIN_SIZE + 100)));
			newWeaponY = GameRoom.MARGIN_SIZE + 100
					+ rand.nextInt((int) (GameRoom.ROOM_SIZE - 2 * (GameRoom.MARGIN_SIZE + 100)));
		} while (Utils.dist(playerY, playerY, newWeaponX, newWeaponY) < 500);
		weaponObject.setX(newWeaponX);
		weaponObject.setY(newWeaponY);
		return weaponObject;
	}

}
