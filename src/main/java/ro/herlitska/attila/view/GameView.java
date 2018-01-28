package ro.herlitska.attila.view;

import java.util.List;

import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.InventoryItem;
import ro.herlitska.attila.model.GameRoom.GamePhase;
import ro.herlitska.attila.model.persistence.Highscore;

public interface GameView {

	public void preDrawEvent(GameSprite backgroundSprite, int roomSize, double playerX, double playerY);

	public void postDrawEvent();

	public void draw(GameSprite sprite, double x, double y);

	public void drawObjectSprites(List<GameObject> objects);

	public void drawRect(double x, double y, double w, double h);

	public void drawText(String text, double x, double y, double fontSize);

	public void drawInventory(List<InventoryItem> inventory, int selectedIndex);

	public void drawHealth(double health);

	public void drawTime(int secondsPassed);
	
	public void drawZombieKillCount(int killCount);

	public void drawMainMenu();
	
	public void drawGameOverMenu(List<Highscore> highscores, int currentPlayerIndex);
	
	public void drawGameOverMenuDbError();
	
	public void drawErrorMessage(String message);
	
	public void setGamePhase(GamePhase gamePhase);
	
	public double getMouseX();
	
	public double getMouseY();

	public boolean inView(double x, double y);
}
