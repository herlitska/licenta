package ro.herlitska.attila.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseButton;

import ro.herlitska.attila.util.Utils;
import ro.herlitska.attila.view.GameView;

public class GameRoom {

    private List<GameObject> objects;
    private List<GameObject> objectsToCreate = new ArrayList<>();
    private GameView view;

    private GameObject player;

    public GameRoom(List<GameObject> objects, GameView view) {
        this.objects = objects;
        this.view = view;
        for (GameObject object : objects) {
            if (object instanceof Player) {
                player = object;
            }
        }
    }

    public void createObject(GameObject object) {
        objectsToCreate.add(object);
    }

    public void destroyObject(GameObject object) {
        objects.remove(object);
    }

    public GameView getView() {
        return view;
    }

    public double getPlayerX() {
        return player.getX();
    }

    public double getPlayerY() {
        return player.getY();
    }

    public void stepEvent() {
        objects.forEach(GameObject::stepEvent);
    }
    
    public void endOfStepEvent(){
        objects.addAll(objectsToCreate);
        objectsToCreate.clear();
    }

    public void drawEvent() {
        view.preDrawEvent();

        view.drawObjectSprites(objects);
        objects.forEach(GameObject::drawEvent);

        view.postDrawEvent();
    }

    public void keyPressedEvent(GameKeyCode key) {
        for (GameObject object : objects) {
            object.keyPressedEvent(key);
        }
    }

    public void mouseMovedEvent(double mouseX, double mouseY) {
        objects.forEach(object -> object.mouseMovedEvent(mouseX, mouseY));
    }

    public void mouseClickedEvent(MouseButton button, double x, double y) {
        System.out.println("Game Room mouse clicked");
        System.out.println(button);
        objects.forEach(object -> object.mouseClickedEvent(button,x,y));
    }

    public void keyReleasedEvent(GameKeyCode key) {
        for (GameObject object : objects) {
            object.keyReleasedEvent(key);
        }
    }

    public void keyDownEvent(GameKeyCode key) {
        for (GameObject object : objects) {
            object.keyDownEvent(key);
        }
    }

    public void checkCollision() {
        for (int i = 0; i < objects.size() - 1; i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject first = objects.get(i);
                GameObject second = objects.get(j);
                if (inCollision(first, first.getX(), first.getY(), second, false)) {
                    first.collisionEvent(second);
                    second.collisionEvent(first);
                }
            }
        }
    }

    /**
     * Returns <code>true</code> if <code>object</code> placed at (
     * <code>x</code>, <code>y</code>) would be in collision with another
     * object.
     * 
     * @param object
     * @param x
     *            x coordinate of <code>object</code> where collision is checked
     * @param y
     *            y coordinate of <code>object</code> where collision is checked
     * @return
     */
    public boolean inCollision(GameObject object, double x, double y, boolean onlySolid) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject other = objects.get(i);

            if (inCollision(object, x, y, other, onlySolid)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if <code>object</code> placed at (
     * <code>x</code>, <code>y</code>) would be in collision with
     * <code>other</code>.
     * 
     * @param object
     * @param x
     *            x coordinate of <code>object</code> where collision is checked
     * @param y
     *            y coordinate of <code>object</code> where collision is checked
     * @param other
     * @return
     */
    private boolean inCollision(GameObject object, double x, double y, GameObject other, boolean onlySolid) {
        return Utils.dist(other.getX(), other.getY(), x,
                y) < object.getSprite().getBoundingCircleRadius() * object.getSprite().getScale()
                        + other.getSprite().getBoundingCircleRadius() * other.getSprite().getScale()
                && !object.equals(other) && (!onlySolid || (object.isSolid() && other.isSolid()));
    }

}
