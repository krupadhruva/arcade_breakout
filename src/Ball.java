import javafx.scene.image.Image;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Ball extends CollisionItem {
    // Ball ends up repeatedly colliding with paddle/wall depending on angle of collision
    // Keeping track of previous collisions and skipping them prevents ball getting stuck
    private final Set<CollisionItem> previousCollisions;

    private final long idlePenaltyInNanos;

    // You lose points for not striking a brick!
    private long previousPenaltyTime;

    // Angle of travel
    private double deg;

    // Ensure we retain the original speed when we change angle (ratio of dy/dx)
    private final double speed;

    public void resetPenaltyTimeout() {
        previousPenaltyTime = System.nanoTime();
    }

    public void setAngle(double deg) {
        this.deg = (360 + deg) % 360;
    }

    // Returns angle in degree (0 to 360)
    public double getAngle() {
        return deg;
    }

    // Constructor
    public Ball(double x, double y, double dx, double dy, long idlePenaltyInSecs) {
        final String path =
                getClass().getClassLoader().getResource("resources/ball.png").toString();
        setImage(new Image(path));

        setX(x);
        setY(y);

        speed = Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0));
        setAngle(315);
        previousCollisions = new HashSet<>();
        previousPenaltyTime = System.nanoTime();
        idlePenaltyInNanos = idlePenaltyInSecs * 1000_000_000;
    }

    @Override
    public void act(long now) {
        double dx = Math.round(Math.cos(deg * Math.PI / 180.0) * speed);
        double dy = Math.round(Math.sin(deg * Math.PI / 180.0) * speed);

        this.move(dx, dy);

        final Collection<CollisionItem> collisions = getIntersectingObjects(CollisionItem.class);
        previousCollisions.retainAll(collisions);

        for (CollisionItem item : collisions) {
            if (!previousCollisions.contains(item)) {
                previousCollisions.add(item);
                item.onCollision(this);
            }
        }

        if (collisions.isEmpty() && ((now - previousPenaltyTime) > idlePenaltyInNanos)) {
            final BallWorld world = (BallWorld) getWorld();
            world.getScore().setValue(world.getScore().getValue() - 5);
            previousPenaltyTime = now;
        }
    }
}
