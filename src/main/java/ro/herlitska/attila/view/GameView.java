package ro.herlitska.attila.view;

import java.util.List;

import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;
import ro.herlitska.attila.model.InventoryItem;

public interface GameView {

	public void preDrawEvent(GameSprite backgroundSprite, int roomSize);

	public void postDrawEvent();

	public void draw(GameSprite sprite, double x, double y);

	public void drawObjectSprites(List<GameObject> objects);

	public void drawRect(double x, double y, double w, double h);

	public void drawText(String text, double x, double y, double fontSize);

	public void drawInventory(List<InventoryItem> inventory, int selectedIndex); // drawing
																					// the
																					// inventory

	public void drawHealth(double health);

}
