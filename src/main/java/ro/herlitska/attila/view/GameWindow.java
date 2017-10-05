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
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.InventoryItem;

public class GameWindow implements GameView {

	private class SpriteToDraw {
		public final GameSprite sprite;
		public final double x;
		public final double y;

		public SpriteToDraw(GameSprite sprite, double x, double y) {
			this.sprite = sprite;
			this.x = x;
			this.y = y;
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
		spritesToDraw.forEach(sprite -> gc.drawImage(sprite.sprite.getImage(), sprite.x, sprite.y));

	}

	@Override
	public void drawObjectSprites(java.util.List<GameObject> objects) {
		for (GameObject object : objects) {
			if (object.isVisible()) {
				spritesToDraw.add(new SpriteToDraw(object.getSprite(), object.getX(), object.getY()));
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

}
