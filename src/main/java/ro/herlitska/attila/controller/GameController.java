package ro.herlitska.attila.controller;

import java.util.HashMap;
import java.util.Map;
import ro.herlitska.attila.model.GameRoom;
import ro.herlitska.attila.model.GameEventHandler;
import ro.herlitska.attila.model.GameKeyCode;

public class GameController implements GameEventHandler {

	private GameRoom room;

	private boolean mouseMoved = false;

	private double mouseX;
	private double mouseY;

	private Map<GameKeyCode, Boolean> keysDown = new HashMap<>();

	private Map<GameKeyCode, Boolean> keysReleased = new HashMap<>();

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
	public void mouseMoved(double mouseX, double mouseY) {
		mouseMoved = true;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
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
		
		// step
		room.stepEvent();

		// collision
		room.checkCollision();

		// draw
		room.drawEvent();

		if (mouseMoved) {
			room.mouseMovedEvent(mouseX, mouseY);
			mouseMoved = false;
		}
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}
}
