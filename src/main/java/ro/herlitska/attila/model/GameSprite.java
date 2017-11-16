package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class GameSprite {

	List<Image> images = new ArrayList<>();
	private int animationSpeed = 0;
	private int step = 0;
	private int currentImage = 0;
	private int depth = 0;
	private double scale = 1;
	private double boundingCircleRadius;

	public GameSprite(String imageUrl) {
		images.add(new Image(imageUrl));
	}

	public GameSprite(List<String> imageUrls) {
		imageUrls.forEach(url -> images.add(new Image(url)));
		boundingCircleRadius = calcDefaultBoundingCircleRadius(images.get(0));
	}

	public GameSprite(List<String> imageUrls, double boundingCircleRadius) {
		imageUrls.forEach(url -> images.add(new Image(url)));
		this.boundingCircleRadius = boundingCircleRadius;
	}

	public void stepEvent() {
		if (step == animationSpeed) {
			currentImage = (currentImage + 1) % (images.size());
		}
		step = (step + 1) % (animationSpeed + 1);
	}

	private double calcDefaultBoundingCircleRadius(Image img) {
		return Math.min(img.getWidth(), img.getHeight()) / 2;
	}

	public double getBoundingCircleRadius() {
		return boundingCircleRadius;
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
