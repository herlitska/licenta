package ro.herlitska.attila.model;

import ro.herlitska.attila.model.GameSpriteFactory.ZombieMotion;

public class Zombie extends GameObject implements Damagable, DamageInflicter {

	private static final double ATTACK_RANGE = GameSpriteFactory.ZOMBIE_RADIUS;
	private static final double ATTACK_DAMAGE = 1;
	private static final int ATTACK_DELAY = 40;

	private double health = 5;

	private int attackDelayCounter = 0;

	private ZombieMotion motion;

	private boolean playerInAttackRange = false;

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

			if (!nextPosCollision() && getRoom().isInRoom(getNextX(), getNextY(), this)) {
				move();
			}

			if (playerInAttackRange && !motion.equals(ZombieMotion.ATTACK)) {
				motion = ZombieMotion.ATTACK;
				setSprite(GameSpriteFactory.getZombieSprite(ZombieMotion.ATTACK));
			} else if (!playerInAttackRange && motion.equals(ZombieMotion.ATTACK)) {
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
		playerInAttackRange = false;

		attackDelayCounter++;

	}

	@Override
	public void collisionEvent(GameObject other) {

	}

	@Override
	public void inAttackRangeEvent(Damagable other) {
		if (other instanceof Player) {
			playerInAttackRange = true;
			if (health > 0 && attackDelayCounter > ATTACK_DELAY) {
				other.damage(ATTACK_DAMAGE);
				attackDelayCounter = 0;
			}
		}
	}

	@Override
	public void drawEvent() {

	}

	@Override
	public double getAttackRange() {
		return ATTACK_RANGE;
	}

	@Override
	public void damage(double damage) {
		if (health > 0) {
			health = Math.max(0, health - damage);
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

	@Override
	public double getHealth() {
		return health;
	}

}
