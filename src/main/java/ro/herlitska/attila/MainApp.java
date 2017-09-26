package ro.herlitska.attila;

import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.herlitska.attila.controller.GameController;
import ro.herlitska.attila.model.GameKeyCode;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.ObjectSprite;
import ro.herlitska.attila.model.Player;
import ro.herlitska.attila.view.GameView;
import ro.herlitska.attila.view.GameWindow;

public class MainApp extends Application {

    private Stage primaryStage;

    private GameController ctr;
    
   

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;       
        
        Player player = new Player(500, 500, new ObjectSprite(Arrays.asList("asd.jpg")));
        ctr = new GameController();
        GameWindow view = new GameWindow(ctr);
        
        GameRoom room = new GameRoom(Arrays.asList(player), view);
        
        player.setRoom(room);
        ctr.setRoom(room);
       
    	
        
        showGameWindow(view);
        view.starloop();    
    }

    public void showGameWindow(GameWindow gameWindow) {
        
        primaryStage.setScene(gameWindow.getScene());
        primaryStage.setTitle("Zombie");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            gameWindow.endLoop();
//            Platform.exit();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
