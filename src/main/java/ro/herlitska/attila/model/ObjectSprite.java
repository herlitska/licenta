package ro.herlitska.attila.model;

import java.util.List;

import javafx.scene.image.Image;

public class ObjectSprite {

    List<Image> images;
    private int animationSpeed;
    private int step = 0;
    private int currentImage = 0;

    public ObjectSprite(String imageUrl) {
        images.add(new Image(imageUrl));
    }

    public ObjectSprite(List<String> imageUrls) {
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

}
