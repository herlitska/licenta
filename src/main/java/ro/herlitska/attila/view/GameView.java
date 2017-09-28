package ro.herlitska.attila.view;

import java.util.List;

import ro.herlitska.attila.model.GameObject;
import ro.herlitska.attila.model.GameSprite;

public interface GameView {

    public void preDrawEvent();
    
    public void postDrawEvent();

    public void draw(GameSprite sprite, double x, double y);
    
    public void drawObjectSprites(List<GameObject> objects);
   
	public void drawRect(double x, double y, double w, double h);
}
