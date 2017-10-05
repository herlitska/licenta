package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import javafx.scene.image.Image;

public class Player extends GameObject {

	public List<InventoryItem> inventory = new ArrayList<>();

	public Player(double x, double y, GameSprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public void keyDownEvent(GameKeyCode key) {
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

}
