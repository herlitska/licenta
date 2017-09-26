package ro.herlitska.attila.view;

import javafx.scene.image.Image;

public interface GameView {

    public void drawEvent();

    public void draw(Image image, double x, double y);
    
   
	public void drawRect(double x, double y);
}
