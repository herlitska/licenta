package ro.herlitska.attila.model;

import ro.herlitska.attila.model.GameSpriteFactory.PlayerMotion;
import ro.herlitska.attila.model.GameSpriteFactory.ZombieMotion;
import ro.herlitska.attila.model.weapon.Bullet;
import ro.herlitska.attila.util.Utils;

public class Zombie extends GameObject implements Damagable, DamageInflicter {

	private double health = 5;

	private ZombieMotion motion;

	private boolean inCollisionWithPlayer = false;

	public Zombie(double x, double y) {
		super(x, y, GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
		this.motion = ZombieMotion.RUN;
		getSprite().setAnimationSpeed(1);
		this.setSpeed(5);
	}

	public Zombie(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public void stepEvent() {
		super.stepEvent();

		if (health > 0) {
			double playerX = getRoom().getPlayerX();
			double playerY = getRoom().getPlayerY();
			double angle = calcAngleBasedOnPlayerPos(playerX, playerY);
			setAngle(angle);
			setDirection(angle);

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

		} else {
			motion = ZombieMotion.DEATH;
			setSprite(GameSpriteFactory.getZombieSprite(ZombieMotion.DEATH));
			setSolid(false);
		}
		// System.out.println(health);
		// System.out.println(player.getMotion().toString());
		inCollisionWithPlayer = false;

	}

	@Override
	public void collisionEvent(GameObject other) {
		if (other instanceof Player) {
			inCollisionWithPlayer = true;
			if (health > 0 && ((Player) other).getMotion().equals(PlayerMotion.ATTACK)) {
				health -= 5;
			}
		}

		if (other instanceof Bullet) { // addition
			if (health > 0) {
				health -= 5;

			}

			other.destroy();
		}

	}

	@Override
	public void drawEvent() {
		// getRoom().getView().drawText(String.valueOf(getDirection()), getX() -
		// 50, getY() - 50, 20);
	}
	
	@Override
	public double getAttackRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void damage(double damage) {
		// TODO Auto-generated method stub
		
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

	public double getHealth() {
		return health;
	}

}
