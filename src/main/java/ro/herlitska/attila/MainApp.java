package ro.herlitska.attila;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.herlitska.attila.controller.GameController;
import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.model.HealthObject;
import ro.herlitska.attila.model.HealthType;
import ro.herlitska.attila.model.Player;

import ro.herlitska.attila.model.Zombie;
import ro.herlitska.attila.model.weapon.WeaponObject;
import ro.herlitska.attila.model.weapon.WeaponType;
import ro.herlitska.attila.view.GameWindow;

public class MainApp extends Application {

	private Stage primaryStage;

	private GameController ctr;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		List<GameObject> objects = new ArrayList<>();

		ctr = new GameController();
		GameWindow view = new GameWindow(ctr, 1024, 768);

		GameRoom room = new GameRoom(objects, view);

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
			// Platform.exit();
		});
		primaryStage.setHeight(768);
		primaryStage.setWidth(1024);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
