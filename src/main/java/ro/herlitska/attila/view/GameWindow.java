package ro.herlitska.attila.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.InventoryItem;
import ro.herlitska.attila.model.Player;

public class GameWindow implements GameView {

	private class SpriteToDraw {
		public final GameSprite sprite;
		public final double x;
		public final double y;
		public final double angle;

		public SpriteToDraw(GameSprite sprite, double x, double y, double angle) {
			this.sprite = sprite;
			this.x = x;
			this.y = y;
			this.angle = angle;
		}

		public SpriteToDraw(GameSprite sprite, double x, double y) {
			super();
			this.sprite = sprite;
			this.x = x;
			this.y = y;
			this.angle = 0;
		}
	}

	private Group rootPane;
	private Canvas canvas;
	private GraphicsContext gc;
	private AnimationTimer gameLoop;
	private Scene scene;

	private List<SpriteToDraw> spritesToDraw;

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
			default:
				break;
			}
		});

		canvas.setOnMouseClicked(e -> System.out.println("Mouse clicked"));
	}

	@Override
	public void preDrawEvent() {
		spritesToDraw = new ArrayList<>();

		gc.fillText("text", 200, 300);
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (int i = 0; i < 4; i++) {
			GameSprite invBackround = new GameSprite(Arrays.asList("/inventory_background.png"));
			invBackround.setDepth(-99);
			spritesToDraw.add(new SpriteToDraw(invBackround, 50 + i * 150, 500));
		}

	}

	@Override
	public void postDrawEvent() {
		Collections.sort(spritesToDraw, (sprite1, sprite2) -> sprite2.sprite.getDepth() - sprite1.sprite.getDepth());
		spritesToDraw.forEach(sprite -> drawRotatedImage(sprite.sprite.getImage(), sprite.angle, sprite.x, sprite.y));

	}

	@Override
	public void drawObjectSprites(java.util.List<GameObject> objects) {
		for (GameObject object : objects) {
			if (object.isVisible()) {
				if (object instanceof Player) {
					spritesToDraw.add(new SpriteToDraw(object.getSprite(), object.getX(), object.getY(),
							((Player) object).getAngle()));

				} else {
					spritesToDraw.add(new SpriteToDraw(object.getSprite(), object.getX(), object.getY()));
				}
			}
		}
	}

	@Override
	public void draw(GameSprite sprite, double x, double y) {
		spritesToDraw.add(new SpriteToDraw(sprite, x, y));
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
	public void drawinventory(List<InventoryItem> inventory) {

		for (int i = 0; i < inventory.size(); i++) {
			spritesToDraw.add(new SpriteToDraw(inventory.get(i).getSprite(), 50 + i * 150, 500));
		}

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
	private void drawRotatedImage(Image image, double angle, double tlpx, double tlpy) {
		gc.save(); // saves the current state on stack, including the current
					// transform
		rotate(angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore(); // back to original state (before rotation)
	}

}
