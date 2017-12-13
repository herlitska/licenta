package ro.herlitska.attila.model;

public class HealthObject extends GameObject {

	private int healthRegained;
	private String name;
	private HealthType healthType;

	public HealthObject(double x, double y, int healthRegained, String name, GameSprite sprite, HealthType healtType) {
		super(x, y, sprite);
		this.healthRegained = healthRegained;
		this.name = name;
		this.healthType = healtType;

	}

	public int getHealthRegained() {
		return healthRegained;
	}

	public String getName() {
		return name;
	}

	public HealthType getHealthType() {
		return healthType;
	}
	
	

}
