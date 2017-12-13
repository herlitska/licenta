package ro.herlitska.attila.model;

public class HealthItem extends InventoryItem {

	private int healthRegained;
	private HealthType healthType;

	public HealthItem(String name, int healthRegained, HealthType healthType, GameSprite sprite) {
		super(name, sprite);
		this.healthType = healthType;
		this.healthRegained = healthRegained;

	}

	public int getHealthRegained() {
		return healthRegained;
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public HealthType getType() {
		return healthType;
	}

}
