package ro.herlitska.attila.model;

import javafx.scene.input.MouseButton;

public interface GameEventHandler {

	public void keyPressed(GameKeyCode e);

	public void keyReleased(GameKeyCode keyCode);
	
	public void keyTyped(String character);

	public void mouseMoved(double mouseX, double mouseY);

	public void mouseClicked(MouseButton button, double x, double y); // my addition
	
	public void startButtonPressed();
	
	public void playAgainPressed();
	
	public void errorOkPressed();
	
	public void scroll(ScrollDirection direction);

	public void step();
}
