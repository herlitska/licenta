package ro.herlitska.attila.model;

import java.util.List;
import java.util.stream.Collectors;

import javassist.expr.Instanceof;
import ro.herlitska.attila.util.Utils;
import ro.herlitska.attila.view.GameView;

public class GameRoom {

	private static final double COLLISION_DIST = 10.0;

	// private GameController ctr;
	private List<GameObject> objects;
	private GameView view;

	private GameObject player;

	public GameRoom(List<GameObject> objects, GameView view) {
		this.objects = objects;
		this.view = view;
		for (GameObject object : objects) {
			if (object instanceof Player) {
				player = object;
			}
		}
	}

	public void destroyObject(GameObject object) {
		objects.remove(object);
	}

	public GameView getView() {
		return view;
	}

	public double getPlayerX() {
		return player.getX();
	}

	public double getPlayerY() {
		return player.getY();
	}

	public void stepEvent() {
		objects.forEach(GameObject::stepEvent);
	}

	public void drawEvent() {
		view.preDrawEvent();

		view.drawObjectSprites(objects);
		objects.forEach(GameObject::drawEvent);

		view.postDrawEvent();
	}

	public void keyPressedEvent(GameKeyCode key) {
		for (GameObject object : objects) {
			object.keyPressedEvent(key);
		}
	}

	public void mouseMovedEvent(double mouseX, double mouseY) {
		objects.forEach(object -> object.mouseMovedEvent(mouseX, mouseY));
	}

	public void checkCollision() {
		for (int i = 0; i < objects.size() - 1; i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				GameObject first = objects.get(i);
				GameObject second = objects.get(j);
				if (Utils.dist(first.getX(), first.getY(), second.getX(),
						second.getY()) < (first.getSprite().getSize() / 2 + second.getSprite().getSize() / 2) - 20) {
					first.collisionEvent(second);
					second.collisionEvent(first);
				}
			}
		}
	}

	public void keyReleasedEvent(GameKeyCode key) {
		for (GameObject object : objects) {
			object.keyReleasedEvent(key);
		}
	}

	public void keyDownEvent(GameKeyCode key) {
		for (GameObject object : objects) {
			object.keyDownEvent(key);
		}
	}

	public void objectDistance() {

		for (int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			if (object instanceof Player) {
				for (int j = 0; j < objects.size(); j++) {

					GameObject object2 = objects.get(j);

					if (object2 instanceof WeaponObject) {
						double dist = Utils.dist(object.getX(), object.getY(), object2.getX(), object2.getY());

					}

				}

			}

		}

	}

	public boolean checkCollision(GameObject object, double x, double y) {
		System.out.println("objects reference" + " " + objects.contains(object));
		for (int i = 0; i < objects.size(); i++) {
			GameObject other = objects.get(i);

			if (Utils.dist(other.getX(), other.getY(), object.getX(),
					object.getY()) < (other.getSprite().getSize() / 2 + object.getSprite().getSize() / 2) - 20
					&& !object.equals(other)) {
				return true;
			}else if(object.equals(other)){
				System.out.println("object equals other");
			}
		}

		return false;
	}

}
