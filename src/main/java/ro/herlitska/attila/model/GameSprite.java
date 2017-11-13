package ro.herlitska.attila.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameSprite {

	List<Image> images = new ArrayList<>();
	private int animationSpeed = 0;
	private int step = 0;
	private int currentImage = 0;
	private int depth = 0;
	private double scale = 1;

	public GameSprite(String imageUrl) {
		images.add(new Image(imageUrl));
	}

	public GameSprite(List<String> imageUrls) {
		System.out.println(imageUrls == null);
		imageUrls.forEach(url -> images.add(new Image(url)));
	}

	public void stepEvent() {
		if (step == animationSpeed) {
			currentImage = (currentImage + 1) % (images.size());
		}
		step = (step + 1) % (animationSpeed + 1);
	}

	public Image getImage() {
		return images.get(currentImage);
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public double getSize() {
		return Math.max(images.get(currentImage).getWidth() * scale, images.get(currentImage).getHeight() * scale);
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public void setScale(double ratio) {
		this.scale = ratio;
	}

	public double getScale() {
		return scale;
	}

	public void setImage(int i) {
		currentImage = i;
	}

}
