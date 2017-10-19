package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import javafx.scene.image.Image;
import ro.herlitska.attila.util.Utils;

public class Player extends GameObject {

	public enum PlayerMotion {
		IDLE, MOVE, ATTACK
	}

	public enum PlayerWeapon {
		KNIFE
	}

	private List<InventoryItem> inventory = new ArrayList<>();

	private String playerName;

	private PlayerMotion motion;

	private PlayerWeapon weapon;

	public Player(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
	}

	public Player(double x, double y) {
		super(x, y, new GameSprite(Utils.getPlayerSpritePath(PlayerMotion.IDLE, PlayerWeapon.KNIFE)));
		getSprite().setAnimationSpeed(2);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public void keyDownEvent(GameKeyCode key) {
		// TODO technical debt: creating too many sprite instances
		setSprite(new GameSprite(Utils.getPlayerSpritePath(PlayerMotion.MOVE, weapon)));
		switch (key) {
		case A:
			setX(getX() - 2);
			break;
		case D:
			setX(getX() + 2);

			break;
		case W:
			setY(getY() - 2);

			break;
		case S:
			setY(getY() + 2);

			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleasedEvent(GameKeyCode key) {
		setSprite(new GameSprite(Utils.getPlayerSpritePath(PlayerMotion.IDLE, weapon)));
	}

	@Override
	public void collisionEvent(GameObject other) {

		System.out.println("collision event");

		if (other instanceof WeaponObject && inventory.size() < 4) {
			inventory.add(new WeaponItem(((WeaponObject) other).getName(), ((WeaponObject) other).getDamage(),
					((WeaponObject) other).getDurability(), other.getSprite()));
			other.destroy();
		}

	}

	@Override
	public void drawEvent() {
		getRoom().getView().drawinventory(inventory);
	}

	@Override
	public void mouseMovedEvent(double mouseX, double mouseY) {
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

		setAngle(Math.toDegrees(inRads));
	}

}
