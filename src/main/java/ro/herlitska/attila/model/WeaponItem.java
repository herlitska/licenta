package ro.herlitska.attila.model;

public class WeaponItem extends InventoryItem {

    private int damage;
    private int durability;
    private WeaponType type;

    public WeaponItem(String name, WeaponType weaponType, int damage, int durability, GameSprite sprite) {
        super(name, sprite);
        this.type = weaponType;
        this.damage = damage;
        this.durability = durability;
    }

    public int getDamage() {
        return damage;
    }

    public int getDurability() {
        return durability;
    }

    public WeaponType getType() {
        return type;
    }
}
