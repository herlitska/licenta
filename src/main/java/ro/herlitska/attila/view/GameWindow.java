package ro.herlitska.attila.view;


import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ro.herlitska.attila.model.GameEventHandler;

public class GameWindow implements GameView {

	private Group rootPane;
	private Canvas canvas;
	private Button button;
	private GraphicsContext gc;
	private AnimationTimer gameLoop;

	public GameWindow(GameEventHandler eventHandler) {
		rootPane = new Group();
		button = new Button(" Jénson Bátön");
		canvas = new Canvas(1024, 768);
		rootPane.getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();

		gameLoop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				System.out.println("step");
				eventHandler.step();
			}
		};

		// handle canvas events
		// TODO design debt
		canvas.setOnKeyPressed(e -> eventHandler.onKeyPressed(e));
		canvas.setOnKeyReleased(e -> eventHandler.onKeyReleased(e));
	}

	@Override
	public void drawEvent() {
		//button.setText("changed");
		 System.out.println(canvas.getWidth() + " x " + canvas.getHeight());
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

}
