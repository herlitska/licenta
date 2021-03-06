package ro.herlitska.attila.controller;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseButton;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.ScrollDirection;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameController implements GameEventHandler {

	private GameRoom room;

	private boolean mouseMoved = false;
	private boolean mouseClicked = false;
	
	private boolean startButtonPressed = false;
	
	private boolean errorOkPressed = false;
	private boolean playAgainPressed = false;
	
	private boolean scrolled = false;
	private ScrollDirection scrollDirection;

	private MouseButton button;

	private double mouseX;
	private double mouseY;

	private Map<GameKeyCode, Boolean> keysDown = new HashMap<>();

	private Map<GameKeyCode, Boolean> keysReleased = new HashMap<>();
	
	private String characterTyped = "";

	public GameController() {
		for (GameKeyCode keyCode : GameKeyCode.values()) {
			keysDown.put(keyCode, false);
		}
	}

	@Override
	public void keyPressed(GameKeyCode keyCode) {
		keysDown.put(keyCode, true);
	}

	@Override
	public void keyReleased(GameKeyCode keyCode) {
		keysDown.put(keyCode, false);
		keysReleased.put(keyCode, true);
	}
	
	@Override
	public void keyTyped(String character) {
		characterTyped = character;
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		mouseMoved = true;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	@Override
	public void startButtonPressed() {
		startButtonPressed = true;
	}
	
	@Override
	public void playAgainPressed() {
		playAgainPressed = true;
	}
	
	@Override
	public void errorOkPressed() {
		errorOkPressed = true;
	}
	
	@Override
	public void scroll(ScrollDirection direction) {
		scrolled = true;
		scrollDirection = direction;
	}

	@Override
	public void step() {
		keysDown.forEach((key, down) -> {
			if (down) {
				room.keyDownEvent(key);
			}
		});

		keysReleased.forEach((key, released) -> {
			if (released) {
				room.keyReleasedEvent(key);
				keysReleased.put(key, false);
			}
		});
		
		if (!characterTyped.isEmpty()) {
			room.keyTypedEvent(characterTyped);
			characterTyped = "";
		}

		// step
		room.stepEvent();

		// collision
		room.checkCollision();

		room.checkAttackRange();

		// draw
		room.drawEvent();

		if (mouseMoved) {
			room.mouseMovedEvent(mouseX, mouseY);
			mouseMoved = false;
		}

		if (mouseClicked) {
			room.mouseClickedEvent(button, mouseX, mouseY);
			mouseClicked = false;
		}
		
		if (startButtonPressed) {
			room.startGame();
			startButtonPressed = false;
		}
		
		if (errorOkPressed) {
			room.errorOkPressed();
			errorOkPressed = false;
		}
		
		if (playAgainPressed) {
			room.playAgainPressed();
			playAgainPressed = false;
		}
		
		if (scrolled) {
			room.scrollEvent(scrollDirection);
			scrolled = false;
		}

		room.endOfStepEvent();
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}

	@Override
	public void mouseClicked(MouseButton button, double x, double y) { // my
																		// addition
		mouseClicked = true;
		this.button = button;
		this.mouseX = x;
		this.mouseY = y;

	}
}
