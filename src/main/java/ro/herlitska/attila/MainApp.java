package ro.herlitska.attila;

import java.util.Arrays;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.herlitska.attila.controller.GameController;
import ro.herlitska.attila.model.ObjectSprite;
import ro.herlitska.attila.model.Player;
import ro.herlitska.attila.view.GameWindow;

public class MainApp extends Application {

    private Stage primaryStage;

    private GameController ctr;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        
        Player player = new Player(500, 500, new ObjectSprite(Arrays.asList("asd.jpg")));
        ctr = new GameController(Arrays.asList(player));
        ctr.startGame();
        
        showGameWindow();
    }

    public void showGameWindow() {
        GameWindow gameWindow = new GameWindow(ctr);
        primaryStage.setScene(new Scene(gameWindow.getRootPane()));
        primaryStage.setTitle("Zombie");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            ctr.endGame();
            Platform.exit();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
