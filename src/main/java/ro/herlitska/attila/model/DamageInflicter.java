package ro.herlitska.attila.model;

public interface DamageInflicter {

	public double getAttackRange();
	
	public void inAttackRangeEvent(Damagable damagable);
	
}
