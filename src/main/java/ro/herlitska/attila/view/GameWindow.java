package ro.herlitska.attila.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import ro.herlitska.attila.controller.GameController;

public class GameWindow {

    private AnchorPane rootPane;
    private Canvas canvas;
    private GraphicsContext gc;

    private GameController ctr;

    public GameWindow(GameController ctr) {
        rootPane = new AnchorPane();
        rootPane.setPrefSize(1024, 768);
        canvas = new Canvas();
        canvas.setWidth(1024);
        canvas.setHeight(768);
        gc = canvas.getGraphicsContext2D();
        rootPane.getChildren().add(canvas);
        
        this.ctr = ctr;
        
        // handle canvas events
        canvas.setOnKeyPressed(e -> ctr.onKeyPressed(e));
        canvas.setOnKeyReleased(e -> ctr.onKeyReleased(e));
    }
    
    public void drawEvent() {
        // draw background
    }
    
    public void draw(Image image, double x, double y) {
        gc.drawImage(image, x, y);
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }

}
