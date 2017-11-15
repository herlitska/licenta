package ro.herlitska.attila.model;

import ro.herlitska.attila.model.GameSpriteFactory.ZombieMotion;
import ro.herlitska.attila.util.Utils;

public class Zombie extends GameObject {

	private double health;
	private ZombieMotion motion;

	public Zombie(double x, double y) {
		super(x, y, GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
		this.motion = ZombieMotion.RUN;
		getSprite().setAnimationSpeed(1);
		this.setSpeed(6);
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
		setAngle(angle);
		setDirection(angle);
		if(!nextPosCollision()){
			move();
		}
//		move();
//		System.out.println("motion is" + " " + motion + " " + "DIstance is " + Utils.dist(getX(), getY(), playerX, playerY));
		if (Utils.dist(getX(), getY(), playerX, playerY) < 120 && !motion.equals(ZombieMotion.ATTACK)) {
			motion = ZombieMotion.ATTACK;
			setSprite(GameSpriteFactory.getZombieSprite(ZombieMotion.ATTACK));
		} else if (Utils.dist(getX(), getY(), playerX, playerY) > 120 && motion.equals(ZombieMotion.ATTACK)) {
			motion = ZombieMotion.RUN;
			setSprite(GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
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
