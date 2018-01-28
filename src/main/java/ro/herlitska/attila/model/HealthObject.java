package ro.herlitska.attila.model;

import java.util.Random;

import ro.herlitska.attila.model.weapon.WeaponObject;
import ro.herlitska.attila.model.weapon.WeaponType;
import ro.herlitska.attila.util.Utils;

public class HealthObject extends GameObject {

	private int healthRegained;
	private String name;
	private HealthType healthType;

	public HealthObject(double x, double y, HealthType healthType) {
		super(x, y, GameSpriteFactory.getHealthSprite(healthType));
		switch (healthType) {
		case HOTDOG:
			healthRegained = 15;
			name = "Hot dog";
			break;
		case MEAT:
			healthRegained = 15;
			name = "Meat";
			break;
		case RATIONS:
			healthRegained = 25;
			name = "Rations";
			break;
		case SURVIVALBAR:
			healthRegained = 10;
			name = "Hot dog";
			break;
		default:
			break;
		}
		this.healthType = healthType;
		setSolid(false);
	}

	public static HealthObject getNewRandomHealthObject(double playerX, double playerY) {
		HealthType randHealthType;

		int healthTypeIndex = (int) Math.floor(HealthType.values().length * Math.random());
		randHealthType = HealthType.values()[healthTypeIndex];

		HealthObject healthObject = new HealthObject(0, 0, randHealthType);
		double newHealthX;
		double newHealthY;
		Random rand = new Random();
		do {
			newHealthX = GameRoom.MARGIN_SIZE + 100
					+ rand.nextInt((int) (GameRoom.ROOM_SIZE - 2 * (GameRoom.MARGIN_SIZE + 100)));
			newHealthY = GameRoom.MARGIN_SIZE + 100
					+ rand.nextInt((int) (GameRoom.ROOM_SIZE - 2 * (GameRoom.MARGIN_SIZE + 100)));
		} while (Utils.dist(playerY, playerY, newHealthX, newHealthY) < 500);
		healthObject.setX(newHealthX);
		healthObject.setY(newHealthY);
		return healthObject;
	}

	public int getHealthRegained() {
		return healthRegained;
	}

	public String getName() {
		return name;
	}

	public HealthType getHealthType() {
		return healthType;
	}

}
