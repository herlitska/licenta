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
		ATTACK, ATTACK01, ATTACK02, ATTACK03, IDLE, RUN, WALK, DEATH, DEATH01, DEATH02
	}

	private static final double ZOMBIE_RADIUS = 25;
	private static final double PLAYER_RADIUS = 78;

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

	private static GameSprite healthSprite;

	private final static String PLAYER_SPRITE_PATH = "/Top_Down_Survivor/&wpn/&mtn/survivor-&mtn_&wpn_&num.png";

	private static final String ZOMBIE_SPRITE_PATH = "/zombie_01/&mtn/&mtn_&num.png";

	private static final String HEALTH_SPRITE_PATH = "/OLD/VIDA_&num.png";

	public static GameSprite getPlayerSprite(PlayerMotion motion, PlayerWeapon weapon) {
		PlayerSpriteSearchKey key = new PlayerSpriteSearchKey(motion, weapon);
		if (playerSprites.containsKey(key)) {
			GameSprite sprite = playerSprites.get(key);
			if (motion == PlayerMotion.ATTACK) {
				sprite.restartAnimation();
			}
			return sprite;

		} else {
			int i = 0;

			String playerSpritePath = PLAYER_SPRITE_PATH.replace("&wpn", weapon.name().toLowerCase()).replace("&mtn",
					motion.name().toLowerCase());
			URL imageUrl = GameSpriteFactory.class.getResource(playerSpritePath.replace("&num", String.valueOf(i)));
			System.out.println(imageUrl.toString());
			List<String> imageUrls = new ArrayList<>();
			while (imageUrl != null) {
				imageUrls.add(imageUrl.toString());
				imageUrl = GameSpriteFactory.class.getResource(playerSpritePath.replace("&num", String.valueOf(++i)));
			}
			GameSprite sprite = new GameSprite(imageUrls, PLAYER_RADIUS);
			playerSprites.put(key, sprite);
			sprite.setScale(0.3);
			if (motion == PlayerMotion.ATTACK) {
				sprite.setRepeatable(false);
			}
			return sprite;
		}
	}

	public static GameSprite getZombieSprite(ZombieMotion motion) {
		ZombieMotion chosenMotion;
		Random rand = new Random();

		if (motion.equals(ZombieMotion.ATTACK)) {
			chosenMotion = ZombieMotion.valueOf("ATTACK0" + String.valueOf(rand.nextInt(2) + 1));
		} else if (motion.equals(ZombieMotion.DEATH)) {
			chosenMotion = ZombieMotion.valueOf("DEATH0" + String.valueOf(rand.nextInt(1) + 1));
		} else {
			chosenMotion = motion;
		}

		if (zombieSprites.containsKey(chosenMotion)) {
			return zombieSprites.get(chosenMotion);
		} else {
			int i = 0;

			String zombieSpritePath = ZOMBIE_SPRITE_PATH;

			zombieSpritePath = zombieSpritePath.replace("&mtn", chosenMotion.name().toLowerCase());

			URL imageUrl = GameSpriteFactory.class
					.getResource(zombieSpritePath.replace("&num", String.format("%04d", i)));

			List<String> imageUrls = new ArrayList<>();
			while (imageUrl != null) {
				imageUrls.add(imageUrl.toString());
				imageUrl = GameSpriteFactory.class
						.getResource(zombieSpritePath.replace("&num", String.format("%04d", ++i)));

			}
			GameSprite sprite = new GameSprite(imageUrls, ZOMBIE_RADIUS);
			zombieSprites.put(chosenMotion, sprite);
			return sprite;
		}

	}

	public static GameSprite getHealthSprite() {
		if (healthSprite == null) {
			int i = 0;

			URL imageUrl = GameSpriteFactory.class.getResource(HEALTH_SPRITE_PATH.replace("&num", String.valueOf(i)));
			List<String> imageUrls = new ArrayList<>();
			while (imageUrl != null) {

				imageUrls.add(imageUrl.toString());
				imageUrl = GameSpriteFactory.class.getResource(HEALTH_SPRITE_PATH.replace("&num", String.valueOf(++i)));

			}
			GameSprite sprite = new GameSprite(imageUrls);

			return sprite;
		} else {
			return healthSprite;
		}

	}
}
