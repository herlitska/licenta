package ro.herlitska.attila;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.herlitska.attila.controller.GameController;
import ro.herlitska.attila.view.GameWindow;

public class MainApp extends Application {

    private Stage primaryStage;

    private GameController ctr;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showGameWindow();
    }

    public void showGameWindow() {
        GameWindow gameWindow = new GameWindow(ctr);
        primaryStage.setScene(new Scene(gameWindow.getRootPane()));
        primaryStage.setTitle("Zombie");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
