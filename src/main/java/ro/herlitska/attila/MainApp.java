package ro.herlitska.attila;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.herlitska.attila.controller.GameController;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.GameSpriteFactory;
import ro.herlitska.attila.model.GameSpriteFactory.WeaponType;
import ro.herlitska.attila.model.Player;
import ro.herlitska.attila.model.WeaponObject;
import ro.herlitska.attila.model.Zombie;
import ro.herlitska.attila.view.GameWindow;

public class MainApp extends Application {

	private Stage primaryStage;

	private GameController ctr;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		Player player = new Player(500, 500);
		WeaponObject weapon = new WeaponObject(100, 100, 1, 10, "kitchen_knife_by_ashmo.png",
				GameSpriteFactory.getWeaponSprite(WeaponType.KNIFE));
		WeaponObject weapon2 = new WeaponObject(150, 50, 1, 10, "kitchen_knife_by_ashmo.png",
				GameSpriteFactory.getWeaponSprite(WeaponType.KNIFE));

		WeaponObject weapon3 = new WeaponObject(236, 140, 1, 10, "Baseball Bat3",
				GameSpriteFactory.getWeaponSprite(WeaponType.KNIFE));

		WeaponObject weapon4 = new WeaponObject(600, 230, 1, 10, "Baseball Bat4",
				GameSpriteFactory.getWeaponSprite(WeaponType.KNIFE));

		WeaponObject weapon5 = new WeaponObject(750, 550, 1, 10, "Baseball Bat5",
				GameSpriteFactory.getWeaponSprite(WeaponType.KNIFE));

		Zombie zombie = new Zombie(1000, 100);
		Zombie zombie2 = new Zombie(20, 300);

		ctr = new GameController();
		GameWindow view = new GameWindow(ctr);

		player.setPlayerName("JOszef");

		GameRoom room = new GameRoom(
				new ArrayList<>(Arrays.asList(player, weapon, weapon2, weapon3, weapon4, weapon5, zombie, zombie2)),
				view);

		player.setRoom(room);
		weapon.setRoom(room);
		weapon.setSolid(false);
		weapon2.setSolid(false);
		weapon3.setSolid(false);
		weapon4.setSolid(false);
		weapon5.setSolid(false);
		weapon2.setRoom(room);
		weapon3.setRoom(room);
		weapon4.setRoom(room);
		weapon5.setRoom(room);
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
