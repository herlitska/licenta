package ro.herlitska.attila.model;

public class WeaponObject extends GameObject {
	
	private String name;
	private int damage ;
	private int durability;
	
	
	public WeaponObject(double x, double y, int damage, int durability, String name, ObjectSprite sprite) {
		super(x, y, sprite);
		this.damage = damage;
		this.durability = durability;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public int getDamage() {
		return damage;
	}


	public int getDurability() {
		return durability;
	}
	
	
	
	

}
