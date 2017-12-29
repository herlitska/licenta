package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseButton;
import ro.herlitska.attila.model.GameSpriteFactory.PlayerMotion;
import ro.herlitska.attila.model.weapon.BallisticWeaponItem;
import ro.herlitska.attila.model.weapon.MeleeWeaponItem;
import ro.herlitska.attila.model.weapon.WeaponItem;
import ro.herlitska.attila.model.weapon.WeaponObject;
import ro.herlitska.attila.model.weapon.WeaponProperties;
import ro.herlitska.attila.model.weapon.WeaponType;

public class Player extends GameObject implements Damagable, DamageInflicter {

    private class PlayerInventory {
        List<InventoryItem> inventory;
        int currentItemIndex = 0;

        public PlayerInventory() {
            inventory = new ArrayList<>(INVENTORY_SIZE);
            inventory.add(WeaponItem.createWeaponItem(new WeaponProperties(WeaponType.KNIFE)));
            for (int i = 1; i < INVENTORY_SIZE; i++) {
                inventory.add(null);
            }
            switchToItem(currentItemIndex);
        }

        public InventoryItem getCurrentItem() {
            if (inventory.get(currentItemIndex) == null) {
                return defaultWeapon;
            } else {
                return inventory.get(currentItemIndex);
            }
        }

        public boolean addItem(InventoryItem item) {
            boolean inventoryNotFull = false;
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i) == null) {
                    inventory.set(i, item);
                    inventoryNotFull = true;
                    if (i == currentItemIndex) {
                        switchToItem(i);
                    }
                    break;
                }
            }
            return inventoryNotFull;
        }

        public void removeItem(int index) {
            inventory.remove(currentItemIndex);
            inventory.add(null);
            switchToItem(currentItemIndex);
        }

        public void switchToItem(int index) {
            currentItemIndex = index;
            if (inventory.get(currentItemIndex) == null) {
                setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE,
                        defaultWeapon.getProperties().getWeaponType()));
                weapon = defaultWeapon.getProperties().getWeaponType();
            } else if (inventory.get(currentItemIndex) instanceof WeaponItem) {
                setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE,
                        ((WeaponItem) inventory.get(currentItemIndex)).getProperties().getWeaponType()));
                weapon = ((WeaponItem) inventory.get(currentItemIndex)).getProperties().getWeaponType();
            }
        }
        
        public void removeItemsWithZeroDurability() {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i) instanceof WeaponItem
                        && ((WeaponItem) inventory.get(i)).getRemainingDurability() == 0) {
                    removeItem(i);
                }
            }
        }

        public void draw() {
            getRoom().getView().drawInventory(inventory, currentItemIndex);
        }
    }

    private PlayerInventory inventory = new PlayerInventory();
    private static final WeaponType DEFAULT_WEAPON = WeaponType.FLASHLIGHT;
    WeaponItem defaultWeapon = WeaponItem.createWeaponItem(new WeaponProperties(DEFAULT_WEAPON));

    private String playerName;
    private double health = MAX_PLAYER_HEALTH;
    private final int INVENTORY_SIZE = 4;

    private PlayerMotion motion = PlayerMotion.IDLE;
    private WeaponType weapon = WeaponType.KNIFE;

    private double mouseX = 0;
    private double mouseY = 0;

    private List<Damagable> damagablesInRange = new ArrayList<>();

    public static final double MAX_PLAYER_HEALTH = 100;

    public Player(double x, double y, GameSprite sprite) {
        super(x, y, sprite);
    }

    public Player(double x, double y) {
        super(x, y, GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, DEFAULT_WEAPON));
        getSprite().setAnimationSpeed(2);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public PlayerMotion getMotion() { // my addition
        return motion;
    }

    @Override
    public void stepEvent() {
        super.stepEvent();
        if (!getSprite().isRepeatable() && getSprite().animationEnded()) {
            motion = PlayerMotion.IDLE;
            setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, weapon));
        }

        if (inventory.getCurrentItem() instanceof WeaponItem) {
            inventory.getCurrentItem().stepEvent();
        }

        inventory.removeItemsWithZeroDurability();
    }

    @Override
    public void endOfStepEvent() {
        super.endOfStepEvent();
        damagablesInRange = new ArrayList<>();
    }

    @Override
    public void keyDownEvent(GameKeyCode key) {
        if (motion != PlayerMotion.ATTACK && motion != PlayerMotion.MOVE) {
            motion = PlayerMotion.MOVE;
            setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.MOVE, weapon));
        }
        switch (key) {
        case A:
            setX(getX() - 5);
            break;
        case D:
            setX(getX() + 5);
            break;
        case W:
            setY(getY() - 5);
            break;
        case S:
            setY(getY() + 5);
            break;
        case NUM_1:
            inventory.switchToItem(0);
            break;
        case NUM_2:
            inventory.switchToItem(1);
            break;
        case NUM_3:
            inventory.switchToItem(2);
            break;
        case NUM_4:
            inventory.switchToItem(3);
            break;
        default:
            break;
        }

        setAngle(calcAngleBasedOnMouse());
    }

    @Override
    public void keyReleasedEvent(GameKeyCode key) {
        motion = PlayerMotion.IDLE;
        setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, weapon));
    }

    @Override
    public void collisionEvent(GameObject other) {
        if (other instanceof WeaponObject) {
            WeaponObject weaponObject = (WeaponObject) other;
            if (inventory.addItem(WeaponItem.createWeaponItem(weaponObject.getProperties()))) {
                other.destroy();
            }
        }

        if (other instanceof HealthObject) {
            HealthObject healthObject = (HealthObject) other;
            if (inventory.addItem(new HealthItem(healthObject.getName(), healthObject.getHealthRegained(),
                                    healthObject.getHealthType(),
                                    GameSpriteFactory.getInventoryHealthSprite(healthObject.getHealthType())))) {
                other.destroy();
            }
        }
    }

    @Override
    public void inAttackRangeEvent(Damagable damagable) {
        damagablesInRange.add(damagable);
    }

    @Override
    public void drawEvent() {
        inventory.draw();
        getRoom().getView().drawHealth(health);
    }

    @Override
    public void mouseMovedEvent(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        setAngle(calcAngleBasedOnMouse());
    }

    @Override
    public void mouseClickedEvent(MouseButton button, double x, double y) {
        if (button.equals(MouseButton.PRIMARY)) {
            if (inventory.getCurrentItem() instanceof MeleeWeaponItem) {
                MeleeWeaponItem weaponItem = (MeleeWeaponItem) inventory.getCurrentItem();
                if (weaponItem.hit()) {
                    motion = PlayerMotion.ATTACK;
                    setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, weapon));
                    for (Damagable damagable : damagablesInRange) {
                        weaponItem.damageEnemy(damagable);
                    }
                }
            }

            if (inventory.getCurrentItem() instanceof BallisticWeaponItem) {
                BallisticWeaponItem weaponItem = (BallisticWeaponItem) inventory.getCurrentItem();
                if (weaponItem.fireBullet(this)) {
                    motion = PlayerMotion.ATTACK;
                    setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, weapon));
                }
            }

            if (inventory.getCurrentItem() instanceof HealthItem) { // addition
                HealthItem healthItem = (HealthItem) inventory.getCurrentItem();
                setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, WeaponType.KNIFE));
                if (health < MAX_PLAYER_HEALTH) {
                    health = Math.min(MAX_PLAYER_HEALTH, health + healthItem.getHealthRegained());
                    inventory.removeItem(inventory.currentItemIndex);
                }
            }
        }
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void damage(double damage) {
        if (health > 0) {
            health = Math.max(0, health - damage);
        }
    }

    @Override
    public double getAttackRange() {
        if (inventory.getCurrentItem() instanceof MeleeWeaponItem) {
            return ((MeleeWeaponItem) inventory.getCurrentItem()).getAttackRange();
        } else {
            return -1;
        }
    }

    private double calcAngleBasedOnMouse() {
        double dx = mouseX - getX();
        // Minus to correct for coord re-mapping
        double dy = -(mouseY - getY());

        double inRads = Math.atan2(dy, dx);

        // We need to map to coord system when 0 degree is at 3 O'clock, 270 at
        // 12 O'clock
        if (inRads < 0)
            inRads = Math.abs(inRads);
        else
            inRads = 2 * Math.PI - inRads;

        return Math.toDegrees(inRads);
    }

}
