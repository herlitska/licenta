package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import ro.herlitska.attila.model.GameSpriteFactory.PlayerMotion;
import ro.herlitska.attila.model.GameSpriteFactory.PlayerWeapon;
import ro.herlitska.attila.util.Utils;

public class Player extends GameObject {

	private List<InventoryItem> inventory = new ArrayList<>();

	private String playerName;
	private double health = 100;

	private PlayerMotion motion = PlayerMotion.IDLE;
	private PlayerWeapon weapon = PlayerWeapon.KNIFE;

	private double mouseX = 0;
	private double mouseY = 0;

	public Player(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
	}

	public Player(double x, double y) {
		super(x, y, GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, PlayerWeapon.KNIFE));
		getSprite().setAnimationSpeed(2);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public void stepEvent() {
		super.stepEvent();
		if (!getSprite().isRepeatable() && getSprite().animationEnded()) {
			motion = PlayerMotion.IDLE;
			setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, weapon));
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
		default:
			break;
		}
		setAngle(calcAngleBasedOnMouse());
	}

	@Override
	public void keyReleasedEvent(GameKeyCode key) {
		motion = PlayerMotion.IDLE;
		setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, weapon));
	}

	@Override
	public void collisionEvent(GameObject other) {

		if (other instanceof Zombie && health > 0) {
			health -= 0.05;
		}

		if (other instanceof WeaponObject && inventory.size() < 4) {
			inventory.add(new WeaponItem(((WeaponObject) other).getName(), ((WeaponObject) other).getDamage(),
					((WeaponObject) other).getDurability(), other.getSprite()));
			other.destroy();
		}

	}

	@Override
	public void drawEvent() {
		getRoom().getView().drawinventory(inventory);
		getRoom().getView().drawHealth(health);
	}

	@Override
	public void mouseMovedEvent(double mouseX, double mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		setAngle(calcAngleBasedOnMouse());
	}

	@Override
	public void mouseClickedEvent(MouseButton button) { // my addition
		if (button.equals(MouseButton.PRIMARY)) {
			motion = PlayerMotion.ATTACK;
			setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, weapon));
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

}
