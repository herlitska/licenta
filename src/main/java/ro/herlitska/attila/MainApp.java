package ro.herlitska.attila;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.herlitska.attila.controller.GameController;
import ro.herlitska.attila.model.GameRoom;
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

		Player player = new Player(500, 500);
		WeaponObject weapon = new WeaponObject(100, 100, WeaponType.KNIFE);
		WeaponObject weapon2 = new WeaponObject(150, 50, WeaponType.KNIFE);
		WeaponObject weapon3 = new WeaponObject(236, 140, WeaponType.HANDGUN);
		WeaponObject weapon4 = new WeaponObject(600, 230, WeaponType.RIFLE);
		WeaponObject weapon5 = new WeaponObject(750, 550, WeaponType.SHOTGUN);

		HealthObject health = new HealthObject(200, 500, 5, "survivalbar",
				GameSpriteFactory.getHealthSprite(HealthType.SURVIVALBAR), HealthType.SURVIVALBAR);

		HealthObject health2 = new HealthObject(255, 132, 15, "hotdog",
				GameSpriteFactory.getHealthSprite(HealthType.HOTDOG), HealthType.HOTDOG);

		Zombie zombie = new Zombie(1000, 100);
		Zombie zombie2 = new Zombie(20, 300);

		ctr = new GameController();
		GameWindow view = new GameWindow(ctr);

		player.setPlayerName("JOszef");

		GameRoom room = new GameRoom(new ArrayList<>(
				Arrays.asList(player, weapon, weapon2, weapon3, weapon4, weapon5, health, health2, zombie, zombie2)),
				view);

		player.setRoom(room);
		weapon.setRoom(room);

		weapon.setSolid(false);
		weapon2.setSolid(false);
		weapon3.setSolid(false);
		weapon4.setSolid(false);
		weapon5.setSolid(false);
		health.setSolid(false);
		health2.setSolid(false);
		weapon2.setRoom(room);
		weapon3.setRoom(room);
		weapon4.setRoom(room);
		weapon5.setRoom(room);
		health.setRoom(room);
		health2.setRoom(room);
		zombie.setRoom(room);
		zombie2.setRoom(room);

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
