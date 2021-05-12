import javafx.scene.image.Image;

public class Ball extends CollisionItem {
    // Speed in X and Y direction (pixels to move per invocation)
    private double dx;
    private double dy;

    // Ensure we retain the original speed when we change dx or dy (angle)
    private final double speed;

    public void setAngle(double deg) {
        dx = Math.sin(deg * Math.PI / 180.0) * speed;
        dy = Math.cos(deg * Math.PI / 180.0) * speed;
    }

    public double getAngle() {
        return 180 * Math.atan(dy / dx) / Math.PI;
    }

    // Constructor
    public Ball(double x, double y, double dx, double dy) {
        final String path =
                getClass().getClassLoader().getResource("resources/ball.png").toString();
        setImage(new Image(path));

        setX(x);
        setY(y);

        this.dx = dx;
        this.dy = dy;
        speed = Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0));
    }

    // Used by walls to reverse the direction of ball on collision
    public void reverseX() {
        dx = -dx;
    }

    public void reverseY() {
        dy = -dy;
    }

    @Override
    public void act(long now) {
        this.move(dx, dy);

        for (CollisionItem item : getIntersectingObjects(CollisionItem.class)) {
            item.onCollision(this);
        }
    }
}
