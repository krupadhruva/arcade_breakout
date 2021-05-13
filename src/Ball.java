import javafx.scene.image.Image;

public class Ball extends CollisionItem {
    // Angle of travel
    private double deg;

    // Ensure we retain the original speed when we change angle (ratio of dy/dx)
    private final double speed;

    public void setAngle(double deg) {
        this.deg = (360 + deg) % 360;
    }

    // Returns angle in degree (0 to 360)
    public double getAngle() {
        return deg;
    }

    // Constructor
    public Ball(double x, double y, double dx, double dy) {
        final String path =
                getClass().getClassLoader().getResource("resources/ball.png").toString();
        setImage(new Image(path));

        setX(x);
        setY(y);

        speed = Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0));
        setAngle(315);
    }

    @Override
    public void act(long now) {
        double dy = Math.round(Math.sin(deg * Math.PI / 180.0) * speed);
        double dx = Math.round(Math.cos(deg * Math.PI / 180.0) * speed);

        this.move(dx, dy);

        for (CollisionItem item : getIntersectingObjects(CollisionItem.class)) {
            item.onCollision(this);
        }
    }
}
