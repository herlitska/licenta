package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseButton;
import ro.herlitska.attila.model.GameSpriteFactory.PlayerMotion;
import ro.herlitska.attila.model.weapon.BallisticWeaponItem;
import ro.herlitska.attila.model.weapon.MeleeWeaponItem;
import ro.herlitska.attila.model.weapon.WeaponItem;
import ro.herlitska.attila.model.weapon.WeaponObject;
import ro.herlitska.attila.model.weapon.WeaponType;

public class Player extends GameObject implements Damagable, DamageInflicter {

    private List<InventoryItem> inventory;

    private int currentItemIndex;

    private String playerName;
    private double health = MAX_PLAYER_HEALTH;
    private final int INVENTORY_SIZE = 4;

    private PlayerMotion motion = PlayerMotion.IDLE;
    // private WeaponType weapon = WeaponType.KNIFE;
    private WeaponType weapon = WeaponType.HANDGUN;

    private double mouseX = 0;
    private double mouseY = 0;

    private List<Damagable> damagablesInRange = new ArrayList<>();

    public static final double MAX_PLAYER_HEALTH = 100;

    public Player(double x, double y, GameSprite sprite) {
        super(x, y, sprite);
        initInventory();

    }

    public Player(double x, double y) {
        super(x, y, GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE, WeaponType.HANDGUN));
        getSprite().setAnimationSpeed(2);
        initInventory();
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

        if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof WeaponItem) { // addition
            inventory.get(currentItemIndex).stepEvent();
        }

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof WeaponItem
                    && ((WeaponItem) inventory.get(i)).getRemainingDurability() == 0) {
                removeFromInventory(i);
            }
        }
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
            currentItemIndex = 0;
            break;
        case NUM_2:
            currentItemIndex = 1;
            break;
        case NUM_3:
            currentItemIndex = 2;
            break;
        case NUM_4:
            currentItemIndex = 3;
            break;
        default:
            break;
        }
        if ((key == GameKeyCode.NUM_1 || key == GameKeyCode.NUM_2 || key == GameKeyCode.NUM_3
                || key == GameKeyCode.NUM_4) && inventory.get(currentItemIndex) instanceof WeaponItem) {
            setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.IDLE,
                    ((WeaponItem) inventory.get(currentItemIndex)).getProperties().getWeaponType()));
            weapon = ((WeaponItem) inventory.get(currentItemIndex)).getProperties().getWeaponType();
        }
        setAngle(calcAngleBasedOnMouse());
        System.out.println(currentItemIndex);
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
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i) == null) {
                    inventory.set(i, WeaponItem.createWeaponItem(weaponObject.getProperties()));
                    other.destroy();
                    break;
                }
            }

        }

        if (other instanceof HealthObject) {
            HealthObject healthObject = (HealthObject) other;
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i) == null) {
                    inventory.set(i,
                            new HealthItem(healthObject.getName(), healthObject.getHealthRegained(),
                                    healthObject.getHealthType(),
                                    GameSpriteFactory.getInventoryHealthSprite(healthObject.getHealthType())));
                    other.destroy();
                    break;
                }
            }

        }

    }

    @Override
    public void inAttackRangeEvent(Damagable damagable) {
        damagablesInRange.add(damagable);
    }

    @Override
    public void drawEvent() {
        getRoom().getView().drawInventory(inventory, currentItemIndex);
        getRoom().getView().drawHealth(health);
        // getRoom().getView().drawText(String.valueOf(getAngle()), getX() - 50,
        // getY() - 50, 20);

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
            if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof MeleeWeaponItem) {
                MeleeWeaponItem weaponItem = (MeleeWeaponItem) inventory.get(currentItemIndex);
                if (weaponItem.hit()) {
                    motion = PlayerMotion.ATTACK;
                    setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, weapon));
                    for (Damagable damagable : damagablesInRange) {
                        weaponItem.damageEnemy(damagable);
                    }
                }
            }

            if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof BallisticWeaponItem) {
                BallisticWeaponItem weaponItem = (BallisticWeaponItem) inventory.get(currentItemIndex);
                if (weaponItem.fireBullet(this)) {
                    motion = PlayerMotion.ATTACK;
                    setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, weapon));
                }
            }

            if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof HealthItem) { // addition
                HealthItem healthItem = (HealthItem) inventory.get(currentItemIndex);
                setSprite(GameSpriteFactory.getPlayerSprite(PlayerMotion.ATTACK, WeaponType.KNIFE));
                if (health < MAX_PLAYER_HEALTH) {
                    health = Math.min(MAX_PLAYER_HEALTH, health + healthItem.getHealthRegained());
                    removeFromInventory(currentItemIndex);
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
        if (!inventory.isEmpty() && inventory.get(currentItemIndex) instanceof MeleeWeaponItem) {
            return ((MeleeWeaponItem) inventory.get(currentItemIndex)).getAttackRange();
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

    private void initInventory() {
        inventory = new ArrayList<>(INVENTORY_SIZE);
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            inventory.add(null);
        }
    }

    private void removeFromInventory(int index) {
        inventory.remove(currentItemIndex);
        inventory.add(null);
    }
}
