package ro.herlitska.attila.model;

import javafx.scene.input.MouseButton;

public interface GameEventHandler {

	public void keyPressed(GameKeyCode e);

	public void keyReleased(GameKeyCode keyCode);

	public void mouseMoved(double mouseX, double mouseY);

	public void mouseClicked(MouseButton button, double x, double y); // my addition

	public void step();
}
