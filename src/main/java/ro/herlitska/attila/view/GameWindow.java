package ro.herlitska.attila.view;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import ro.herlitska.attila.model.GameEventHandler;

public class GameWindow implements GameView {

    private AnchorPane rootPane;
    private Canvas canvas;
    private Button button;
    private GraphicsContext gc;
    private int counter = 0;

    public GameWindow(GameEventHandler eventHandler) {
        rootPane = new AnchorPane();
        rootPane.setPrefSize(1024, 768);
        button = new Button("Bátön");
        canvas = new Canvas();
        canvas.setWidth(1024);
        canvas.setHeight(768);
        gc = canvas.getGraphicsContext2D();
        rootPane.getChildren().add(canvas);

        // handle canvas events
        // TODO design debt
        canvas.setOnKeyPressed(e -> eventHandler.onKeyPressed(e));
        canvas.setOnKeyReleased(e -> eventHandler.onKeyReleased(e));
    }

    @Override
    public void drawEvent() {
        System.out.println(Thread.currentThread().getName());
        Platform.runLater(() -> {
            System.out.println(Thread.currentThread().getName());            
            if (counter == 0) {
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
            counter = 1;
        });
    }

    @Override
    public void draw(Image image, double x, double y) {
        gc.drawImage(image, x, y);
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }

}
