import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Actor extends ImageView {
    // Methods
    public void move(double dx, double dy) {
        this.setX(getX() + dx);
        this.setY(getY() + dy);
    }

    public World getWorld() {
        return (World) this.getParent();
    }

    public double getWidth() {
        return this.getBoundsInParent().getWidth();
    }

    public double getHeight() {
        return this.getBoundsInParent().getHeight();
    }

    public <A extends Actor> Collection<A> getIntersectingObjects(java.lang.Class<A> cls) {
        final Set<A> list = new HashSet<>();

        for (Node obj : this.getWorld().getChildren()) {
            // Skip intersecting with ourselves
            if (equals(obj)) {
                continue;
            }

            if (cls.isInstance(obj)) {
                final A object = cls.cast(obj);
                if (object.intersects(this.getBoundsInParent())) {
                    list.add(object);
                }
            }
        }

        return list;
    }

    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        for (Node obj : this.getWorld().getChildren()) {
            // Skip intersecting with ourselves
            if (equals(obj)) {
                continue;
            }

            if (cls.isInstance(obj)) {
                final A object = cls.cast(obj);
                if (object.intersects(this.getBoundsInParent())) {
                    return object;
                }
            }
        }

        return null;
    }

    public abstract void act(long now);
}
