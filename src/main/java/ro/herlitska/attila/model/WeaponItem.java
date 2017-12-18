package ro.herlitska.attila.model;

public class WeaponItem extends InventoryItem {

	private WeaponProperties properties;
	
	private int remainingDurability;
	private int attackDelayCounter = 0;	
	
	public static WeaponItem createWeaponItem(WeaponProperties properties) {
		WeaponItem weaponItem;
		if (properties.getWeaponType() == WeaponType.HANDGUN) {
			weaponItem = new MeleeWeaponItem(properties);
		} else {
			weaponItem = new BallisticWeaponItem(properties);
		}
		weaponItem.remainingDurability = properties.getDurability();
		return weaponItem;
	}
	
	protected WeaponItem(WeaponProperties properties) {
		super(properties.getName(), GameSpriteFactory.getInventoryWeaponSprite(properties.getWeaponType()));
		this.properties = properties;
	}

	public WeaponProperties getProperties() {
		return properties;
	}

	@Override
	public void stepEvent() {
		attackDelayCounter++;
	}

	public boolean canAttack() {
		return attackDelayCounter >= properties.getAttackDelay();
	}

	public void attack(double x, double y, double angle, GameRoom room) {
		if (canAttack()) {
			
		}
		if (ballisticWeapon && canAttack()) {
			attackDelayCounter = 0;			
			Bullet bullet = new Bullet(x, y, GameSpriteFactory.getBulletSprite(), damage);
			bullet.setDirection(angle);
			bullet.setAngle(angle);
			bullet.setRoom(room);
			room.createObject(bullet);
		}
	}
}
