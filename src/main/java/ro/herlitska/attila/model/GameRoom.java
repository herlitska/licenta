package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.input.MouseButton;
import ro.herlitska.attila.model.persistence.Highscore;
import ro.herlitska.attila.model.weapon.WeaponObject;
import ro.herlitska.attila.model.weapon.WeaponType;
import ro.herlitska.attila.util.Utils;
import ro.herlitska.attila.view.GameView;

public class GameRoom {

	private List<GameObject> objects;
	private List<GameObject> objectsToCreate = new ArrayList<>();
	private List<GameObject> objectsToDestroy = new ArrayList<>();

	private GameView view;

	public enum GamePhase {
		MAIN_MENU, GAME, GAME_OVER
	}

	private GamePhase gamePhase = GamePhase.MAIN_MENU;

	public static final int ROOM_SIZE = 1024;

	public static final double MARGIN_SIZE = 90;

	private GameObject player;

	private AtomicInteger secondsPassed = new AtomicInteger(0);
	private Timer gameTimer;

	private List<Highscore> highscores = new ArrayList<>();

	public GameRoom(List<GameObject> objects, GameView view) {
		this.objects = objects;
		this.view = view;

		initMainMenu();
	}

	public void initMainMenu() {
		view.setGamePhase(gamePhase);
	}

	public void startGame() {
		highscores.clear();
		gamePhase = GamePhase.GAME;
		view.setGamePhase(GamePhase.GAME);

		secondsPassed.set(0);

		Zombie.resetKillCount();

		Player player = new Player(500, 500);

		player.setPlayerName("JOszef");

		objects.add(player);
		objects.add(new WeaponObject(100, 100, WeaponType.KNIFE));
		objects.add(new WeaponObject(150, 50, WeaponType.KNIFE));
		objects.add(new WeaponObject(236, 140, WeaponType.HANDGUN));
		objects.add(new WeaponObject(600, 230, WeaponType.RIFLE));
		objects.add(new WeaponObject(750, 550, WeaponType.SHOTGUN));

		objects.add(new HealthObject(200, 500, 5, "survivalbar",
				GameSpriteFactory.getHealthSprite(HealthType.SURVIVALBAR), HealthType.SURVIVALBAR));

		objects.add(new HealthObject(255, 132, 15, "hotdog", GameSpriteFactory.getHealthSprite(HealthType.HOTDOG),
				HealthType.HOTDOG));

		objects.add(new Zombie(800, 200));
		objects.add(new Zombie(600, 300));
		objects.add(new Zombie(700, 300));
		objects.add(new Zombie(300, 300));
		objects.add(new Zombie(400, 300));
		objects.add(new Zombie(500, 300));

		objects.forEach(object -> object.setRoom(this));

		for (GameObject object : objects) {
			if (object instanceof Player) {
				this.player = object;
				break;
			}
		}

		gameTimer = new Timer(true);
		gameTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				secondsPassed.incrementAndGet();
			}
		}, 0, 1000);
	}

	public void initGameOver() {
		gamePhase = GamePhase.GAME_OVER;
		view.setGamePhase(gamePhase);

		highscores.add(new Highscore(0, "Sanyi", "01:00", 20));
		highscores.add(new Highscore(0, "Jóska", "02:00", 18));
		highscores.add(new Highscore(0, "Gyula", "01:30", 18));
		highscores.add(new Highscore(0, "Gazsi", "01:50", 17));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));
		highscores.add(new Highscore(0, "Bélus", "01:01", 16));

		Collections.sort(highscores, (h1, h2) -> {
			Highscore highscore1 = (Highscore) h1;
			Highscore highscore2 = (Highscore) h2;
			if (highscore1.getZombiesKilled() != highscore2.getZombiesKilled()) {
				return highscore2.getZombiesKilled() - highscore1.getZombiesKilled();
			} else {
				return Highscore.compareTime(highscore2, highscore1);
			}
		});

		objectsToDestroy.addAll(objects);

		gameTimer.cancel();
	}

	public void createObject(GameObject object) {
		objectsToCreate.add(object);
	}

	public void destroyObject(GameObject object) {
		objectsToDestroy.add(object);
	}

	public GameView getView() {
		return view;
	}

	public double getPlayerX() {
		if (player != null) {
			return player.getX();
		} else {
			return -1;
		}
	}

	public double getPlayerY() {
		if (player != null) {
			return player.getY();
		} else {
			return -1;
		}
	}

	public void stepEvent() {
		GameSpriteFactory.stepEventAllSprites();
		objects.forEach(GameObject::stepEvent);
	}

	public void endOfStepEvent() {
		objectsToCreate.forEach(o -> o.setRoom(this));
		objects.addAll(objectsToCreate);
		objectsToCreate.clear();

		objects.removeAll(objectsToDestroy);
		objectsToDestroy.clear();

		objects.forEach(GameObject::endOfStepEvent);
	}

	public void drawEvent() {
		if (gamePhase != GamePhase.GAME) {
			view.preDrawEvent(GameSpriteFactory.getBackgroundSprite(), ROOM_SIZE, ROOM_SIZE / 2, ROOM_SIZE / 2);
		} else {
			view.preDrawEvent(GameSpriteFactory.getBackgroundSprite(), ROOM_SIZE, getPlayerX(), getPlayerY());
		}

		if (gamePhase == GamePhase.GAME) {
			view.drawObjectSprites(objects);
			view.drawTime(secondsPassed.get());
			view.drawZombieKillCount(Zombie.getZombiesKilled());
			objects.forEach(GameObject::drawEvent);
		} else if (gamePhase == GamePhase.MAIN_MENU) {
			view.drawMainMenu();
		} else if (gamePhase == GamePhase.GAME_OVER) {
			view.drawGameOverMenu(highscores);
		}
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

	public void mouseClickedEvent(MouseButton button, double mouseX, double mouseY) {
		objects.forEach(object -> object.mouseClickedEvent(button, mouseX, mouseY));
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

	public void checkCollision() {
		for (int i = 0; i < objects.size() - 1; i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				GameObject first = objects.get(i);
				GameObject second = objects.get(j);
				if (inCollision(first, first.getX(), first.getY(), second, false)) {
					first.collisionEvent(second);
					second.collisionEvent(first);
				}
			}
		}
	}

	/**
	 * Returns <code>true</code> if <code>object</code> placed at (
	 * <code>x</code>, <code>y</code>) would be in collision with another
	 * object.
	 * 
	 * @param object
	 * @param x
	 *            x coordinate of <code>object</code> where collision is checked
	 * @param y
	 *            y coordinate of <code>object</code> where collision is checked
	 * @return
	 */
	public boolean inCollision(GameObject object, double x, double y, boolean onlySolid) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject other = objects.get(i);

			if (inCollision(object, x, y, other, onlySolid)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if <code>object</code> placed at (
	 * <code>x</code>, <code>y</code>) would be in collision with
	 * <code>other</code>.
	 * 
	 * @param object
	 * @param x
	 *            x coordinate of <code>object</code> where collision is checked
	 * @param y
	 *            y coordinate of <code>object</code> where collision is checked
	 * @param other
	 * @return
	 */
	private boolean inCollision(GameObject object, double x, double y, GameObject other, boolean onlySolid) {

		if (Utils.dist(other.getX(), other.getY(), x,
				y) < object.getSprite().getBoundingCircleRadius() * object.getSprite().getScale()
						+ other.getSprite().getBoundingCircleRadius() * other.getSprite().getScale()
				&& !object.equals(other) && (!onlySolid || (object.isSolid() && other.isSolid()))) {
			if (object instanceof Player || other instanceof Player) {
				System.out.println(
						"COLLISION :" + " " + object.getClass().getName() + " & " + other.getClass().getName());
			}
			return true;
		} else
			return false;

	}

	public void checkAttackRange() {
		for (GameObject attacker : objects) {
			if (attacker instanceof DamageInflicter && ((DamageInflicter) attacker).getAttackRange() != -1) {
				for (GameObject damagable : objects) {
					if (damagable instanceof Damagable) {
						if (Utils.dist(attacker.getX(), attacker.getY(), damagable.getX(),
								damagable.getY()) < ((DamageInflicter) attacker).getAttackRange()
										+ damagable.getSprite().getBoundingCircleRadius()
								&& !attacker.equals(damagable)) {
							((DamageInflicter) attacker).inAttackRangeEvent((Damagable) damagable);
						}
					}
				}
			}
		}
	}

	public boolean isInRoom(double x, double y, GameObject object) {
		return x > MARGIN_SIZE && x < ROOM_SIZE - MARGIN_SIZE && y > MARGIN_SIZE && y < ROOM_SIZE - MARGIN_SIZE;
	}

	public boolean isInRoom(GameObject object) {
		return isInRoom(object.getX(), object.getY(), object);
	}

	public int getSecondsPassed() {
		return secondsPassed.get();
	}
}
