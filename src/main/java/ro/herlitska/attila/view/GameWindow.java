package ro.herlitska.attila.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.model.HealthItem;
import ro.herlitska.attila.model.InventoryItem;
import ro.herlitska.attila.model.Player;
import ro.herlitska.attila.model.weapon.WeaponItem;

public class GameWindow implements GameView {

	public static final int MIN_DEPTH = -100;
	public static final int MAX_DEPTH = 100;

	private abstract class Drawable {
		public final double x;
		public final double y;

		public Drawable(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public abstract int getDepth();

	}

	private class SpriteToDraw extends Drawable {
		public final GameSprite sprite;
		public final double angle;

		public SpriteToDraw(GameSprite sprite, double x, double y, double angle) {
			super(x, y);
			this.sprite = sprite;
			this.angle = angle;
		}

		public SpriteToDraw(GameSprite sprite, double x, double y) {
			super(x, y);
			this.sprite = sprite;
			this.angle = 0;
		}

		@Override
		public int getDepth() {
			return sprite.getDepth();
		}
	}

	private class TextToDraw extends Drawable {
		public final String text;
		public final double fontSize;

		public TextToDraw(String text, double x, double y, double fontSize) {
			super(x, y);
			this.text = text;
			this.fontSize = fontSize;
		}

		@Override
		public int getDepth() {
			return MIN_DEPTH;
		}
	}

	private class RectangleToDraw extends Drawable {
		public final double height;
		public final double width;
		public final double opacity;
		public final int depth;
		public final int rgbRed;
		public final int rgbGreen;
		public final int rgbBlue;

		public RectangleToDraw(double x, double y, double height, double width, double opacity, int depth, int rgbRed,
				int rgbGreen, int rgbBlue) {
			super(x, y);
			this.height = height;
			this.width = width;
			this.opacity = opacity;
			this.depth = depth;
			this.rgbRed = rgbRed;
			this.rgbGreen = rgbGreen;
			this.rgbBlue = rgbBlue;
		}

		@Override
		public int getDepth() {
			return depth;
		}
	}

	private double playerHealth;
	private Group rootPane;
	private Canvas canvas;
	private GraphicsContext gc;
	private AnimationTimer gameLoop;
	private Scene scene;

	// private List<SpriteToDraw> spritesToDraw;
	private List<Drawable> drawables;

	public GameWindow(GameEventHandler eventHandler) {
		rootPane = new Group();
		canvas = new Canvas(1024, 768);
		rootPane.getChildren().add(canvas);
		scene = new Scene(rootPane);

		gc = canvas.getGraphicsContext2D();

		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				eventHandler.step();
			}
		};

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				eventHandler.keyPressed(GameKeyCode.A);
				break;
			case S:
				eventHandler.keyPressed(GameKeyCode.S);
				break;
			case W:
				eventHandler.keyPressed(GameKeyCode.W);
				break;
			case D:
				eventHandler.keyPressed(GameKeyCode.D);
				break;
			case DIGIT1:
				eventHandler.keyPressed(GameKeyCode.NUM_1);
				break;
			case DIGIT2:
				eventHandler.keyPressed(GameKeyCode.NUM_2);
				break;
			case DIGIT3:
				eventHandler.keyPressed(GameKeyCode.NUM_3);
				break;
			case DIGIT4:
				eventHandler.keyPressed(GameKeyCode.NUM_4);
				break;

			default:
				break;
			}
		});

		scene.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case A:
				eventHandler.keyReleased(GameKeyCode.A);
				break;
			case S:
				eventHandler.keyReleased(GameKeyCode.S);
				break;
			case W:
				eventHandler.keyReleased(GameKeyCode.W);
				break;
			case D:
				eventHandler.keyReleased(GameKeyCode.D);
				break;
			case DIGIT1:
				eventHandler.keyReleased(GameKeyCode.NUM_1);
				break;
			case DIGIT2:
				eventHandler.keyReleased(GameKeyCode.NUM_2);
				break;
			case DIGIT3:
				eventHandler.keyReleased(GameKeyCode.NUM_3);
				break;
			case DIGIT4:
				eventHandler.keyReleased(GameKeyCode.NUM_4);
				break;
			default:
				break;
			}
		});

		scene.setOnMouseMoved(e -> eventHandler.mouseMoved(e.getSceneX(), e.getSceneY()));

		// canvas.setOnMouseClicked(e -> System.out.println("Mouse clicked"));

		scene.setOnMousePressed(e -> {
			eventHandler.mouseClicked(e.getButton(), e.getSceneX(), e.getSceneY());
			System.out.println(e.getButton());
		});

	}

	@Override
	public void preDrawEvent(GameSprite backgroundSprite, int roomSize) {
		drawables = new ArrayList<>();

		for (int i = 0; i < roomSize / backgroundSprite.getSize(); i++) {
			for (int j = 0; j < roomSize / backgroundSprite.getSize(); j++) {
				drawRotatedScaledImage(backgroundSprite.getImage(), 0,
						backgroundSprite.getSize() / 2 + i * backgroundSprite.getSize(),
						backgroundSprite.getSize() / 2 + j * backgroundSprite.getSize(), 1);
			}
		}
		GameSprite wallSprite = GameSpriteFactory.getWallSprite();
		drawRotatedScaledImage(wallSprite.getImage(), 0, 0, 0, 1);
		drawRotatedScaledImage(wallSprite.getImage(), 0, 0, roomSize, 1);
		drawRotatedScaledImage(wallSprite.getImage(), 0, roomSize, 0, 1);
		drawRotatedScaledImage(wallSprite.getImage(), 0, roomSize, roomSize, 1);

		for (int i = 1; i < roomSize / wallSprite.getSize() - 1; i++) {
			drawRotatedScaledImage(wallSprite.getImage(), 0, i * wallSprite.getSize(), 0, 1);
			drawRotatedScaledImage(wallSprite.getImage(), 0, 0, i * wallSprite.getSize(), 1);
			drawRotatedScaledImage(wallSprite.getImage(), 0, i * wallSprite.getSize(), roomSize, 1);
			drawRotatedScaledImage(wallSprite.getImage(), 0, roomSize, i * wallSprite.getSize(), 1);
		}

	}

	@Override
	public void postDrawEvent() {

		Collections.sort(drawables, (d1, d2) -> d2.getDepth() - d1.getDepth());
		for (Drawable drawable : drawables) {
			if (drawable instanceof SpriteToDraw) {
				SpriteToDraw sprite = (SpriteToDraw) drawable;
				drawRotatedScaledImage(sprite.sprite.getImage(), sprite.angle, sprite.x, sprite.y,
						sprite.sprite.getScale());
			} else if (drawable instanceof TextToDraw) {
				TextToDraw text = (TextToDraw) drawable;
				gc.setFill(Color.WHITE);
				gc.setFont(new Font(null, text.fontSize));
				gc.fillText(text.text, text.x, text.y);
			} else if (drawable instanceof RectangleToDraw) {
				RectangleToDraw rectangle = (RectangleToDraw) drawable;
				gc.setFill(Color.rgb(rectangle.rgbRed, rectangle.rgbGreen, rectangle.rgbBlue, rectangle.opacity));
				gc.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
		}

	}

	@Override
	public void drawObjectSprites(java.util.List<GameObject> objects) {
		for (GameObject object : objects) {
			if (object.isVisible()) {
				drawables.add(new SpriteToDraw(object.getSprite(), object.getX(), object.getY(), object.getAngle()));

			}
		}
	}

	@Override
	public void draw(GameSprite sprite, double x, double y) {
		drawables.add(new SpriteToDraw(sprite, x, y));
	}

	public Group getRootPane() {
		return rootPane;
	}

	public void starloop() {
		gameLoop.start();
	}

	public void endLoop() {
		gameLoop.stop();
	}

	public void drawRect(double x, double y) {
		gc.setFill(Color.RED);
		gc.fillRect(x, y, 50, 50);
	}

	@Override
	public void drawRect(double x, double y, double w, double h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawText(String text, double x, double y, double fontSize) {
		drawables.add(new TextToDraw(text, x, y, fontSize));
	}

	@Override
	public void drawInventory(List<InventoryItem> inventory, int selectedIndex) {

		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null) {
				drawables.add(new SpriteToDraw(inventory.get(i).getSprite(), 110 + i * 150, 650));
				StringBuilder itemDescrBuilder = new StringBuilder();
				itemDescrBuilder.append(inventory.get(i).getName() + " ");
				if (inventory.get(i) instanceof WeaponItem) {
					WeaponItem weaponItem = (WeaponItem) inventory.get(i);
					itemDescrBuilder.append("\nDurability: " + weaponItem.getRemainingDurability() + "/"
							+ weaponItem.getProperties().getDurability());
				} else if (inventory.get(i) instanceof HealthItem) {
					HealthItem healthItem = (HealthItem) inventory.get(i);
					itemDescrBuilder.append("\nHeals: " + healthItem.getHealthRegained());
				}
				drawables.add(new TextToDraw(itemDescrBuilder.toString(), 46 + i * 150, 550, 16));
			}
		}

		for (int i = 0; i < inventory.size(); i++) {
			drawables.add(new RectangleToDraw(46 + i * 150, 586, 128, 128, selectedIndex == i ? 0.8 : 0.4,
					MIN_DEPTH + 1, 255, 255, 255));
		}
	}

	@Override
	public void drawHealth(double health) {
		playerHealth = health;

		drawables.add(new RectangleToDraw(610, 20, 40, 380, 0.2, MIN_DEPTH + 1, 255, 255, 255));
		drawables
				.add(new RectangleToDraw(610, 20, 40, 380 * (playerHealth / Player.MAX_PLAYER_HEALTH), 0.8, MIN_DEPTH,
						(int) (playerHealth >= Player.MAX_PLAYER_HEALTH / 2 ? 255
								* (1 - ((playerHealth - Player.MAX_PLAYER_HEALTH / 2) / (Player.MAX_PLAYER_HEALTH / 2)))
								: 255),
						(int) (playerHealth >= Player.MAX_PLAYER_HEALTH / 2 ? 255
								: 255 * (playerHealth / (Player.MAX_PLAYER_HEALTH / 2))),
						0));

		drawables.add(new TextToDraw(String.valueOf((int) playerHealth), 570, 50, 20));
	}

	public Scene getScene() {
		return scene;
	}

	/**
	 * Sets the transform for the GraphicsContext to rotate around a pivot
	 * point.
	 *
	 * @param gc
	 *            the graphics context the transform to applied to.
	 * @param angle
	 *            the angle of rotation.
	 * @param px
	 *            the x pivot co-ordinate for the rotation (in canvas
	 *            co-ordinates).
	 * @param py
	 *            the y pivot co-ordinate for the rotation (in canvas
	 *            co-ordinates).
	 */
	private void rotate(double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	/**
	 * Draws an image on a graphics context.
	 *
	 * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the
	 * point: (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
	 *
	 * @param gc
	 *            the graphics context the image is to be drawn on.
	 * @param angle
	 *            the angle of rotation.
	 * @param tlpx
	 *            the top left x co-ordinate where the image will be plotted (in
	 *            canvas co-ordinates).
	 * @param tlpy
	 *            the top left y co-ordinate where the image will be plotted (in
	 *            canvas co-ordinates).
	 */
	private void drawRotatedScaledImage(Image image, double angle, double tlpx, double tlpy, double ratio) {
		gc.save(); // saves the current state on stack, including the current
					// transform
		rotate(angle, tlpx, tlpy);
		gc.drawImage(image, tlpx - image.getWidth() * ratio / 2, tlpy - image.getHeight() * ratio / 2,
				image.getWidth() * ratio, image.getHeight() * ratio);

		gc.restore(); // back to original state (before rotation)
	}

}
