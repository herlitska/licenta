package ro.herlitska.attila.model;

import ro.herlitska.attila.model.GameSpriteFactory.ZombieMotion;
import ro.herlitska.attila.util.Utils;

public class Zombie extends GameObject {

	private double health;
	private ZombieMotion motion;

	private boolean inCollisionWithPlayer = false;

	public Zombie(double x, double y) {
		super(x, y, GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
		this.motion = ZombieMotion.RUN;
		getSprite().setAnimationSpeed(1);
		this.setSpeed(3);
	}

	public Zombie(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public void stepEvent() {
		super.stepEvent();

		double playerX = getRoom().getPlayerX();
		double playerY = getRoom().getPlayerY();
		double angle = calcAngleBasedOnPlayerPos(playerX, playerY);
		if (Math.abs(getAngle() - angle) > 1) {
			setAngle(angle);
			setDirection(angle);
		}
		if (!nextPosCollision()) {
			move();
		}

		if (inCollisionWithPlayer && !motion.equals(ZombieMotion.ATTACK)) {
			motion = ZombieMotion.ATTACK;
			setSprite(GameSpriteFactory.getZombieSprite(ZombieMotion.ATTACK));
		} else if (!inCollisionWithPlayer && motion.equals(ZombieMotion.ATTACK)) {
			motion = ZombieMotion.RUN;
			setSprite(GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
		}

		inCollisionWithPlayer = false;
	}

	@Override
	public void collisionEvent(GameObject other) {
		if (other instanceof Player) {
			inCollisionWithPlayer = true;
		}
	}

	private double calcAngleBasedOnPlayerPos(double playerX, double playerY) {
		double dx = playerX - getX();
		// Minus to correct for coord re-mapping
		double dy = -(playerY - getY());

		double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;

		return Math.toDegrees(inRads);
	}

}
