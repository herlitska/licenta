package ro.herlitska.attila.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class GameObject {

	private double x;
	private double y;
	private double direction;
	private double speed;
	private double angle = 90;

	private GameSprite sprite;
	private boolean visible = true;

	private static AtomicInteger nextObjectId = new AtomicInteger();

	private final int id;

	private GameRoom room;

	public GameObject(double x, double y, GameSprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.id = nextObjectId.incrementAndGet();
	}

	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.id = nextObjectId.incrementAndGet();
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void destroy() {
		room.destroyObject(this);
	}

	public void stepEvent() {
		sprite.stepEvent();
	}

	public void drawEvent() {

	}

	public void collisionEvent(GameObject other) {

	}

	public void keyPressedEvent(GameKeyCode key) {

	}

	public void keyReleasedEvent(GameKeyCode key) {

	}

	public void keyDownEvent(GameKeyCode key) {

	}

	public void mouseMovedEvent(double mouseX, double mouseY) {

	}

	@Override
	public boolean equals(Object arg0) {
		return this.id == ((GameObject) arg0).id;
	};

	// Getters and setters

	public double getX() {
		return x;
	}

	public int getId() {
		return id;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public GameSprite getSprite() {
		return sprite;
	}

	public void setSprite(GameSprite sprite) {
		this.sprite = sprite;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}

	public GameRoom getRoom() {
		return room;
	}

	public void move() {
		x = x + speed * Math.cos(angle);
		y = y + speed * Math.sin(angle);
	}

	public boolean nextPosCollision() {
		return room.checkCollision(this, x + speed * Math.cos(angle), y + speed * Math.sin(angle));
	}

}
