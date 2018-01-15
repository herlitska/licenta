package ro.herlitska.attila.model;

public abstract class GameButton {

	private boolean mouseOnButton = false;
	private String text;
	private double x;
	private double y;
	private double width;
	private double height;

	public GameButton(String text, double x, double y, double width, double height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void mouseInside() {
		if (!mouseOnButton) {
			mouseOnButton = true;
		}
	}

	public void mouseOutside() {
		if (mouseOnButton) {
			mouseOnButton = false;
		}
	}

	abstract public void mousePressed();

	public boolean isMouseOnButton() {
		return mouseOnButton;
	}

	public String getText() {
		return text;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}
