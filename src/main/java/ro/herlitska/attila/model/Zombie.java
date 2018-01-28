package ro.herlitska.attila.model;

import java.util.Random;

import ro.herlitska.attila.model.GameSpriteFactory.ZombieMotion;

public class Zombie extends GameObject implements Damagable, DamageInflicter {

	private static final double ATTACK_RANGE = GameSpriteFactory.ZOMBIE_RADIUS;
	// private static final double ATTACK_DAMAGE = 1;
	private static final double MAX_ATTACK_DAMAGE = 5;
	private static final double MAX_SPEED = 5;

	private static final int ATTACK_DELAY = 40;

	private static int zombieCount = 0;
	private static final int MAX_ZOMBIE_COUNT = 50;
	private static final int MAX_ZOMBIE_SPAWN_TRIES = 100;

	private static int zombiesKilled = 0;

	private double health = 5;
	private final double attackDamage;

	private int attackDelayCounter = 0;

	private ZombieMotion motion;

	private boolean playerInAttackRange = false;

	private boolean newZombiesSpawned = false;

	public Zombie(double x, double y, double attackDamage, double speed) {
		super(x, y, GameSpriteFactory.getZombieSprite(ZombieMotion.RUN));
		this.motion = ZombieMotion.RUN;
		this.attackDamage = attackDamage;
		setSpeed(speed);
		getSprite().setAnimationSpeed(1);
		zombieCount++;
	}

	public Zombie(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
		this.attackDamage = 1.0;
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
			if (!newZombiesSpawned) {
				zombiesKilled++;
				zombieCount--;
				if (zombieCount < MAX_ZOMBIE_COUNT) {
					newZombiesSpawned = true;
					spawnZombies();
				}
			}

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
				other.damage(attackDamage);
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

		return Math.toDegrees(inRads) + 0.01 * dx;
	}

	@Override
	public double getHealth() {
		return health;
	}

	private void spawnZombies() {
		int secondsPassed = getRoom().getSecondsPassed();
		int count = secondsPassed / 60 + 1;
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			double newZombieAttackDamage = Math.min(0.5 + 0.05 * rand.nextDouble() * secondsPassed, MAX_ATTACK_DAMAGE);
			double newZombieSpeed = Math.min(1 + 0.05 * rand.nextDouble() * secondsPassed, MAX_SPEED);
			Zombie newZombie = new Zombie(0, 0, newZombieAttackDamage, newZombieSpeed);
			double zombieX;
			double zombieY;
			int tries = 0;
			do {
				zombieX = GameRoom.MARGIN_SIZE + 100
						+ rand.nextInt((int) (GameRoom.ROOM_SIZE - 2 * (GameRoom.MARGIN_SIZE + 100)));
				zombieY = GameRoom.MARGIN_SIZE + 100
						+ rand.nextInt((int) (GameRoom.ROOM_SIZE - 2 * (GameRoom.MARGIN_SIZE + 100)));
				tries++;
			} while ((getRoom().inCollision(newZombie, zombieX, zombieY, true)
					|| getRoom().getView().inView(zombieX, zombieY)) && tries < MAX_ZOMBIE_SPAWN_TRIES);
			if (tries < MAX_ZOMBIE_SPAWN_TRIES) {
				newZombie.setX(zombieX);
				newZombie.setY(zombieY);
				getRoom().createObject(newZombie);
			}
		}
	}

	public static int getZombiesKilled() {
		return zombiesKilled;
	}

	public static void resetKillCount() {
		Zombie.zombiesKilled = 0;
	}

}
