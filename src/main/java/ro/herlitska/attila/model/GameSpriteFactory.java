package ro.herlitska.attila.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSpriteFactory {

	public enum PlayerMotion {
		IDLE, MOVE, ATTACK
	}

	public enum PlayerWeapon {
		KNIFE
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

	private final static String PLAYER_SPRITE_PATH = "/Top_Down_Survivor/&wpn/&mtn/survivor-&mtn_&wpn_&num.png";

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
			return sprite;
		}
	}
}
