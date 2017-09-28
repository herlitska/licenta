package ro.herlitska.attila.model;

public class WeaponItem extends InventoryItem {
    
    private int damage;
    private int durability;    

    public WeaponItem(String name, int damage, int durability, GameSprite sprite) {
        super(name, sprite);
        this.damage = damage;
        this.durability = durability;
    }

}
