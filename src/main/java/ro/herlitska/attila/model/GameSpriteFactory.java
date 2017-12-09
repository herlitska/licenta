package ro.herlitska.attila.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameSpriteFactory {

	public enum PlayerMotion {
		IDLE, MOVE, ATTACK
	}

	public enum PlayerWeapon {
		KNIFE, HANDGUN, RIFLE, SHOTGUN
	}

	public enum ZombieMotion {
		ATTACK, ATTACK01, ATTACK02, ATTACK03, IDLE, RUN, WALK, DEATH, DEATH01, DEATH02
	}

	public enum WeaponType {
		KNIFE, HANDGUN, RIFLE, SHOTGUN
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

	private static Map<WeaponType, GameSprite> weaponSprites = new HashMap<>();

	private static Map<WeaponType, GameSprite> inventorySprites = new HashMap<>();

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
			if (motion == ZombieMotion.DEATH) { // my addition
				sprite.setRepeatable(false);
			}
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

	public static GameSprite getWeaponSprite(WeaponType weaponType) {
		if (weaponSprites.containsKey(weaponType)) {
			return weaponSprites.get(weaponType);
		} else {
			GameSprite sprite = null;
			switch (weaponType) {
			case KNIFE:
				sprite = new GameSprite(Arrays.asList("/kitchen_knife_by_ashmo_glow.png"));
				break;
			case HANDGUN:
				sprite = new GameSprite(Arrays.asList("/smith_and_wesson_41_by_ashmo_glow.png"));
				break;
			case RIFLE:
				sprite = new GameSprite(Arrays.asList("/stg_44_or_mp43_by_ashmo_glow.png"));
				break;
			case SHOTGUN:
				sprite = new GameSprite(Arrays.asList("/mag7_by_ashmo_glow.png"));
				break;

			default:
				break;
			}
			sprite.setScale(0.4);
			sprite.setDepth(100);
			weaponSprites.put(weaponType, sprite);
			return sprite;
		}

	}

	public static GameSprite getInventorySprite(WeaponType weaponType) {
		if (inventorySprites.containsKey(weaponType)) {
			return inventorySprites.get(weaponType);
		} else {
			GameSprite sprite = null;
			switch (weaponType) {
			case KNIFE:
				sprite = new GameSprite(Arrays.asList("/kitchen_knife_by_ashmo_ silhouette.png"));
				break;
			case HANDGUN:
				sprite = new GameSprite(Arrays.asList("/smith_and_wesson_41_by_ashmo_silhouette.png"));
				break;
			case RIFLE:
				sprite = new GameSprite(Arrays.asList("/stg_44_or_mp43_by_ashmo_silhouette.png"));
				break;
			case SHOTGUN:
				sprite = new GameSprite(Arrays.asList("/mag7_by_ashmo_silhouette.png"));
				break;

			default:
				break;
			}
			sprite.setScale(0.6);
			sprite.setDepth(100);
			inventorySprites.put(weaponType, sprite);
			return sprite;
		}

	}

	public static GameSprite getBulletSprite() { // my addition
		GameSprite sprite = null;
		sprite = new GameSprite(Arrays.asList("/Bullet_12x3.png"));
		return sprite;
	}

}
