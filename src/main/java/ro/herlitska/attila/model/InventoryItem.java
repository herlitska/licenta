package ro.herlitska.attila.model;

public abstract class InventoryItem {

    private String name;
    private GameSprite sprite;

    public InventoryItem(String name, GameSprite sprite) {
        this.name = name;
        this.sprite = sprite;
        sprite.setDepth(-100); // the inventory item sprite is always on top
    }

    public String getName() {
        return name;
    }

    public GameSprite getSprite() {
        return sprite;
    }
    
    public abstract void stepEvent();
}
