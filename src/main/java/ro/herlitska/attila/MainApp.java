package ro.herlitska.attila;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

		Player player = new Player(500, 500);

		player.setPlayerName("JOszef");

		objects.add(player);
		objects.add(new WeaponObject(100, 100, WeaponType.KNIFE));
		objects.add(new WeaponObject(150, 50, WeaponType.KNIFE));
		objects.add(new WeaponObject(236, 140, WeaponType.HANDGUN));
		objects.add(new WeaponObject(600, 230, WeaponType.RIFLE));
		objects.add(new WeaponObject(750, 550, WeaponType.SHOTGUN));

		objects.add(new HealthObject(200, 500, 5, "survivalbar",
				GameSpriteFactory.getHealthSprite(HealthType.SURVIVALBAR), HealthType.SURVIVALBAR));

		objects.add(new HealthObject(255, 132, 15, "hotdog", GameSpriteFactory.getHealthSprite(HealthType.HOTDOG),
				HealthType.HOTDOG));

		objects.add(new Zombie(1000, 100));
		objects.add(new Zombie(20, 300));

		GameSprite wallSprite = GameSpriteFactory.getWallSprite();

		ctr = new GameController();
		GameWindow view = new GameWindow(ctr);

		GameRoom room = new GameRoom(objects, view);

		objects.forEach(object -> object.setRoom(room));

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
