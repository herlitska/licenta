package ro.herlitska.attila.model;

import ro.herlitska.attila.model.GameSpriteFactory.ZombieMotion;

public class Zombie extends GameObject {

	private double health;
	private ZombieMotion motion;

	public Zombie(double x, double y) {
		super(x, y, GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
		getSprite().setAnimationSpeed(1);
		this.setSpeed(6);
	}

	public Zombie(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public void stepEvent() {
		super.stepEvent();
		double angle = calcAngleBasedOnPlayerPos();
		setAngle(angle);
		setDirection(angle);
		move();
	}

	private double calcAngleBasedOnPlayerPos() {
		double dx = getRoom().getPlayerX() - getX();
		// Minus to correct for coord re-mapping
		double dy = -(getRoom().getPlayerY() - getY());

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
