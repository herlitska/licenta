package ro.herlitska.attila.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import ro.herlitska.attila.model.GameEventHandler;

public class GameWindow implements GameView {

    private Group rootPane;
    private Canvas canvas;
    private Button button;
    private GraphicsContext gc;

    public GameWindow(GameEventHandler eventHandler) {
        rootPane = new Group();
        button = new Button("Bátön");
        canvas = new Canvas(1024, 768);
        rootPane.getChildren().add(button);

        gc = canvas.getGraphicsContext2D();

        // handle canvas events
        // TODO design debt
        canvas.setOnKeyPressed(e -> eventHandler.onKeyPressed(e));
        canvas.setOnKeyReleased(e -> eventHandler.onKeyReleased(e));
    }

    @Override
    public void drawEvent() {        
            button.setText("changed");
//            System.out.println(canvas.getWidth() + " x " + canvas.getHeight());
//            gc.fillText("text", 200, 300);
//            gc.setFill(Color.GREEN);
//            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void draw(Image image, double x, double y) {
        gc.drawImage(image, x, y);
    }

    public Group getRootPane() {
        return rootPane;
    }

}
