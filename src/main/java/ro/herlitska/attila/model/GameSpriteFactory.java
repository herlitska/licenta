package ro.herlitska.attila.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameSpriteFactory {

	public enum PlayerMotion {
		IDLE, MOVE, ATTACK
	}

	public enum PlayerWeapon {
		KNIFE
	}

	public enum ZombieMotion {
		ATTACK, IDLE, RUN, WALK, DEATH
	}

	private static class PlayerSpriteSearchKey {
		public final PlayerMotion motion;
		public final PlayerWeapon weapon;

		public PlayerSpriteSearchKey(PlayerMotion motion, PlayerWeapon weapon) {
			super();
			this.motion = motion;
			this.weapon = weapon;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof PlayerSpriteSearchKey) {
				return this.motion == ((PlayerSpriteSearchKey) obj).motion
						&& this.weapon == ((PlayerSpriteSearchKey) obj).weapon;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return 31 * (31 + (motion == null ? 0 : motion.hashCode())) + (weapon == null ? 0 : weapon.hashCode());
		}
	}

	private static Map<PlayerSpriteSearchKey, GameSprite> playerSprites = new HashMap<>();

	private static Map<ZombieMotion, GameSprite> zombieSprites = new HashMap<>();

	private final static String PLAYER_SPRITE_PATH = "/Top_Down_Survivor/&wpn/&mtn/survivor-&mtn_&wpn_&num.png";

	private static final String ZOMBIE_SPRITE_PATH = "/zombie_01/&mtn/&mtn_&num.png";

	public static GameSprite getPlayerSprite(PlayerMotion motion, PlayerWeapon weapon) {
		PlayerSpriteSearchKey key = new PlayerSpriteSearchKey(motion, weapon);
		if (playerSprites.containsKey(key)) {
			return playerSprites.get(key);
		} else {
			int i = 0;
			System.out.println(weapon.name().toLowerCase());
			System.out.println(motion.name().toLowerCase());
			String playerSpritePath = PLAYER_SPRITE_PATH.replace("&wpn", weapon.name().toLowerCase()).replace("&mtn",
					motion.name().toLowerCase());
			URL imageUrl = GameSpriteFactory.class.getResource(playerSpritePath.replace("&num", String.valueOf(i)));
			List<String> imageUrls = new ArrayList<>();
			while (imageUrl != null) {
				imageUrls.add(imageUrl.toString());
				imageUrl = GameSpriteFactory.class.getResource(playerSpritePath.replace("&num", String.valueOf(++i)));
			}
			GameSprite sprite = new GameSprite(imageUrls);
			playerSprites.put(key, sprite);
			sprite.setScale(0.3);
			return sprite;
		}
	}

	public static GameSprite getZombieSprite(ZombieMotion motion) {
		if (zombieSprites.containsKey(motion)) {

			return zombieSprites.get(motion);
		} else {
			int i = 0;
			Random rand = new Random();
			String zombieSpritePath = ZOMBIE_SPRITE_PATH;

			if (motion.equals(ZombieMotion.ATTACK)) {

				zombieSpritePath = zombieSpritePath.replace("&mtn",
						motion.name().toLowerCase() + "0" + String.valueOf(rand.nextInt(2) + 1));
			} else if (motion.equals(ZombieMotion.DEATH)) {
				zombieSpritePath = zombieSpritePath.replace("&mtn",
						motion.name().toLowerCase() + "0" + String.valueOf(rand.nextInt(1) + 1));
			} else {
				zombieSpritePath = zombieSpritePath.replace("&mtn", motion.name().toLowerCase());
			}

			URL imageUrl = GameSpriteFactory.class
					.getResource(zombieSpritePath.replace("&num", String.format("%04d", i)));

			List<String> imageUrls = new ArrayList<>();
			while (imageUrl != null) {

				imageUrls.add(imageUrl.toString());
				imageUrl = GameSpriteFactory.class
						.getResource(zombieSpritePath.replace("&num", String.format("%04d", ++i)));

			}
			GameSprite sprite = new GameSprite(imageUrls);
			zombieSprites.put(motion, sprite);
			return sprite;
		}

	}
}
