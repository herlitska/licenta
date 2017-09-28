package ro.herlitska.attila.model;

public class WeaponItem implements InventoryItem{
	
	private String name;
	private int damage ;
	private int durability;
	private ObjectSprite sprite;
	
	public WeaponItem(String name, int damage, int durability, ObjectSprite sprite) {
		
		this.name = name;
		this.damage = damage;
		this.durability = durability;
		this.sprite = sprite;
	}
	
	

}
