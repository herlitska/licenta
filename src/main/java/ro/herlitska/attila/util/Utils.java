package ro.herlitska.attila.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.Player.PlayerMotion;
import ro.herlitska.attila.model.Player.PlayerWeapon;

public class Utils {

	private final static String PLAYER_FOLDER_PATH = "/Top_Down_Survivor/&wpn/&mtn";

	public static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public static List<String> getPlayerSpritePath(PlayerMotion motion, PlayerWeapon weapon) {
		String path = PLAYER_FOLDER_PATH.replace("&wpn", weapon.name().toLowerCase()).replace("&mtn",
				motion.name().toLowerCase());
		System.out.println(path);
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> spritePaths = new ArrayList<>();
		for (int i = 0; i < listOfFiles.length; i++) {
			spritePaths.add(path + "/" + listOfFiles[i].getName());

		}
		return spritePaths;
	}
}
