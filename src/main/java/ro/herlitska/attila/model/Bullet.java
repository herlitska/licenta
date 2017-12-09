package ro.herlitska.attila.model;

import java.awt.Canvas;

public class Bullet extends GameObject {

	private boolean visible;

	public Bullet(double x, double y, GameSprite sprite, boolean visible) {
		super(x, y, sprite);
		this.visible = visible;
		this.setSpeed(3);
		getSprite().setAnimationSpeed(2);
	}

	@Override
	public void stepEvent() {
		super.stepEvent();

		double playerX = getRoom().getPlayerX();
		double playerY = getRoom().getPlayerY();
		double angle = calculateAngleBasedOnPlayerPos(playerX, playerY);
		setAngle(angle);
		setDirection(angle);
		if (isVisible() && !nextPosCollision()) {
			move();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void outOfBounds() {
		if (getX() > 1024 && getY() < 768) {
			visible = false;
		}
	}

	private double calculateAngleBasedOnPlayerPos(double playerX, double playerY) {
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
