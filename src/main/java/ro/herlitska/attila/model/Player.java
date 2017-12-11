package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseButton;
import ro.herlitska.attila.model.GameSpriteFactory.PlayerMotion;

public class Player extends GameObject {

	private List<InventoryItem> inventory;

	private int currentItemIndex;

	private String playerName;
	private double health = MAX_PLAYER_HEALTH;
	private final int INVENTORY_SIZE = 4;

	private PlayerMotion motion = PlayerMotion.IDLE;
	// private WeaponType weapon = WeaponType.KNIFE;
	private WeaponType weapon = WeaponType.HANDGUN;

	private double mouseX = 0;
	private double mouseY = 0;

	public static final double MAX_PLAYER_HEALTH = 100;

	public Player(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
		initInventory();

	}

	public Player(double x, double y) {
		super(x, y, GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, WeaponType.HANDGUN));
		getSprite().setAnimationSpeed(2);
		initInventory();
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public PlayerMotion getMotion() { // my addition
		return motion;
	}

	@Override
	public void stepEvent() {
		super.stepEvent();
		if (!getSprite().isRepeatable() && getSprite().animationEnded()) {
			motion = PlayerMotion.IDLE;
			setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, weapon));
		}

		if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof WeaponItem) { // addition
			inventory.get(currentItemIndex).stepEvent();
		}

	}

	@Override
	public void keyDownEvent(GameKeyCode key) {
		if (motion != PlayerMotion.ATTACK && motion != PlayerMotion.MOVE) {
			motion = PlayerMotion.MOVE;
			setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.MOVE, weapon));
		}
		switch (key) {
		case A:
			setX(getX() - 5);
			break;
		case D:
			setX(getX() + 5);
			break;
		case W:
			setY(getY() - 5);
			break;
		case S:
			setY(getY() + 5);
			break;
		case NUM_1:
			currentItemIndex = 0;
			break;
		case NUM_2:
			currentItemIndex = 1;
			break;
		case NUM_3:
			currentItemIndex = 2;
			break;
		case NUM_4:
			currentItemIndex = 3;
			break;
		default:
			break;
		}
		if ((key == GameKeyCode.NUM_1 || key == GameKeyCode.NUM_2 || key == GameKeyCode.NUM_3
				|| key == GameKeyCode.NUM_4) && inventory.get(currentItemIndex) instanceof WeaponItem) {
			setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE,
					((WeaponItem) inventory.get(currentItemIndex)).getType()));
			weapon = ((WeaponItem) inventory.get(currentItemIndex)).getType();
		}
		setAngle(calcAngleBasedOnMouse());
		System.out.println(currentItemIndex);
	}

	@Override
	public void keyReleasedEvent(GameKeyCode key) {
		motion = PlayerMotion.IDLE;
		setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, weapon));
	}

	@Override
	public void collisionEvent(GameObject other) {
		if (other instanceof Zombie && health > 0 && ((Zombie) other).getHealth() > 0) {
			health -= 0.05;
		}

		if (other instanceof WeaponObject) {
			WeaponObject weaponObject = (WeaponObject) other;
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i) == null) {
					inventory.set(i,
							new WeaponItem(weaponObject.getName(), weaponObject.getWeaponType(),
									weaponObject.getDamage(), weaponObject.getDurability(),
									GameSpriteFactory.getInventorySprite(weaponObject.getWeaponType())));
					other.destroy();
					break;
				}
			}

		}

	}

	@Override
	public void drawEvent() {
		getRoom().getView().drawInventory(inventory, currentItemIndex);
		getRoom().getView().drawHealth(health);
		// getRoom().getView().drawText(String.valueOf(getAngle()), getX() - 50,
		// getY() - 50, 20);
	}

	@Override
	public void mouseMovedEvent(double mouseX, double mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		setAngle(calcAngleBasedOnMouse());
	}

	@Override
	public void mouseClickedEvent(MouseButton button, double x, double y) {
		if (button.equals(MouseButton.PRIMARY)) {
			if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof WeaponItem) {
				WeaponItem weaponItem = (WeaponItem) inventory.get(currentItemIndex);
				motion = PlayerMotion.ATTACK;
				setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, weapon));
				if ((weapon == WeaponType.HANDGUN || weapon == WeaponType.RIFLE || weapon == WeaponType.SHOTGUN)
						&& weaponItem.canShoot()) {
					Bullet bullet = new Bullet(getX(), getY(), GameSpriteFactory.getBulletSprite(),
							weaponItem.getDamage());
					getRoom().createObject(bullet);
					weaponItem.resetDelayCounter();
					bullet.setDirection(getAngle());
					bullet.setAngle(getAngle());
					bullet.setRoom(getRoom());

				}
			}
		}
		System.out.println("player mouse clicked event");

	}

	private double calcAngleBasedOnMouse() {
		double dx = mouseX - getX();
		// Minus to correct for coord re-mapping
		double dy = -(mouseY - getY());

		double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;

		return Math.toDegrees(inRads);
	}

	private void initInventory() {
		inventory = new ArrayList<>(INVENTORY_SIZE);
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			inventory.add(null);
		}
	}

}
