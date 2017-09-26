package ro.herlitska.attila.view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameWindow implements GameView {

	private Group rootPane;
	private Canvas canvas;
	private Button button;
	private GraphicsContext gc;
	private AnimationTimer gameLoop;
	private Scene scene;
	private double delta =
	private List<GameKeyCode> multikeyPressed = new ArrayList();
	private BooleanProperty firstKeyPressed = new SimpleBooleanProperty();
	private BooleanProperty secondKeyPressed = new SimpleBooleanProperty();

	public GameWindow(GameEventHandler eventHandler) {
		rootPane = new Group();
		button = new Button(" Jénson Bátön");
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

		// handle canvas events
		// TODO design debt
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				firstKeyPressed.set(true);
				multikeyPressed.add(GameKeyCode.A);
				break;
			case S:
				firstKeyPressed.set(true);
				multikeyPressed.add(GameKeyCode.S);
				break;
			case D:
				firstKeyPressed.set(true);
				multikeyPressed.add(GameKeyCode.D);
				break;
			case W:
				firstKeyPressed.set(true);
				multikeyPressed.add(GameKeyCode.W);
				break;
			default:
				break;
			}
			
			if(firstKeyPressed.get()){
				switch (e.getCode()) {
				case A:
					secondKeyPressed.set(true);
					multikeyPressed.add(GameKeyCode.A);
					break;
				case S:
					secondKeyPressed.set(true);
					multikeyPressed.add(GameKeyCode.S);
					break;
				case D:
					secondKeyPressed.set(true);
					multikeyPressed.add(GameKeyCode.D);
					break;
				case W:
					secondKeyPressed.set(true);
					multikeyPressed.add(GameKeyCode.W);
					break;
				default:
					break;
				}
				
			}

			eventHandler.onKeyPressed(e);
		});

		scene.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case A:
				firstKeyPressed.set(false);
				multikeyPressed.remove(GameKeyCode.A);
				break;
			case S:
				firstKeyPressed.set(false);
				multikeyPressed.remove(GameKeyCode.S);
				break;
			case D:
				firstKeyPressed.set(false);
				multikeyPressed.remove(GameKeyCode.D);
				break;
			case W:
				firstKeyPressed.set(false);
				multikeyPressed.remove(GameKeyCode.W);
				break;
			default:
				break;
			}

			eventHandler.onKeyReleased(e);
		});

		canvas.setOnMouseClicked(e -> System.out.println("Mouse clicked"));
	}

	@Override
	public void drawEvent() {
		// button.setText("changed");

		gc.fillText("text", 200, 300);
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

	}

	@Override
	public void draw(Image image, double x, double y) {
		gc.drawImage(image, x, y);
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

	@Override
	public void drawRect(double x, double y) {
		gc.setFill(Color.RED);
		gc.fillRect(x, y, 50, 50);

	}

	public Scene getScene() {
		return scene;
	}

}
