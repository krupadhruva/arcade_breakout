import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class World extends Pane {

    // Attributes
    private AnimationTimer timer;
    private final HashSet<KeyCode> keyDown;

    // Constructor
    public World() {
        keyDown = new HashSet<>();
        timer =
                new AnimationTimer() {

                    @Override
                    public void handle(long now) {
                        act(now);

                        // Do not allow mutating the list when we are traversing
                        // By calling toArray(), we get a copy of all elements in the list
                        // and we are iterating on the copy instead of the list. This will
                        // allow removing nodes in call to act() when we traverse the list
                        for (Object actor : getChildren().toArray()) {
                            if (actor instanceof Actor) {
                                ((Actor) actor).act(now);
                            }
                        }
                    }
                };
    }

    // Methods
    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void add(Actor actor) {
        this.getChildren().add((Node) actor);
    }

    public void remove(Actor actor) {
        this.getChildren().remove((Node) actor);
    }

    public void addKeyDown(KeyCode key) {
        keyDown.add(key);
    }

    public void removeKeyDown(KeyCode key) {
        keyDown.remove(key);
    }

    public boolean isKeyDown(KeyCode key) {
        return keyDown.contains(key);
    }

    public boolean isNoKeyDown() {
        if (keyDown.isEmpty()) return true;
        else return false;
    }

    public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls) {

        ArrayList<A> list = new ArrayList<A>();

        for (Node obj : this.getChildren()) {
            A object = (A) obj;
            if (object.getClass().getTypeName().equals(cls.getTypeName())) {
                list.add(object);
            }
        }

        return list;
    }

    public abstract void act(long now);
}
