package ro.herlitska.attila.view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameWindow implements GameView {

    private Group rootPane;
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Scene scene;

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
    public void drawEvent() {
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