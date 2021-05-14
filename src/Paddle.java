import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends CollisionItem {
    // Attributes
    private double delta;
    private int direction;
    private boolean lazyInitialized;

    // Constructors
    public Paddle(double x, double y) {
        final String path =
                getClass().getClassLoader().getResource("resources/paddle.png").toString();
        setImage(new Image(path));
        setX(x);
        setY(y);

        delta = 5.0;
        direction = 0;
        lazyInitialized = false;
    }

    // Lazily initialize mouse handler once we know we are attached to game world
    private void lazyInitializeOnce() {
        if (!lazyInitialized) {
            getWorld()
                    .setOnMouseMoved(
                            event -> {
                                double dispX = event.getX() - getX();
                                move(dispX, 0.0);
                                direction = Double.compare(dispX, 0.0);
                            });
            lazyInitialized = true;
        }
    }

    @Override
    public void act(long now) {
        lazyInitializeOnce();

        direction = 0;
        if (getWorld().isKeyDown(KeyCode.LEFT)) {
            delta = -Math.abs(delta);
            direction = -1;
        } else if (getWorld().isKeyDown(KeyCode.RIGHT)) {
            delta = Math.abs(delta);
            direction = 1;
        }

        if (direction != 0) {
            move(delta, 0.0);
        }
    }

    @Override
    public void move(double dx, double dy) {
        super.move(dx, dy);

        // Ensure paddle does not go through the wall
        final Wall wall = getOneIntersectingObject(Wall.class);
        if (wall != null) {
            if (wall.getPos() == Wall.Position.LEFT) {
                setX(wall.getWidth());
            } else {
                setX(wall.getX() - getWidth());
            }
        }
    }

    @Override
    void onCollision(CollisionItem other) {
        // For now, we are only interested in ball
        if (other instanceof Ball) {
            onCollisionWithBall((Ball) other);
        }
    }

    private void onCollisionWithBall(Ball ball) {
        final double angle = ball.getAngle();
        final boolean top = (ball.getY() + ball.getHeight() / 2.0) < (getY() + getHeight() / 2.0);

        if (ball.getX() + ball.getWidth() < getX()) {
            // Left face
            if (direction < 0) {
                ball.setAngle(190);
            } else {
                ball.setAngle(angle + 90);
            }
        } else if (ball.getX() >= getX() + getWidth()) {
            // Right face
            if (direction > 0) {
                ball.setAngle(350);
            } else {
                ball.setAngle(angle + 90);
            }
        } else if (ball.getX() < (getX() + getWidth() / 3.0)) {
            // Left part of paddle
            if (top) {
                ball.setAngle(200);
            } else {
                ball.setAngle(160);
            }
        } else if (ball.getX() > (getX() + getWidth() * 2.0 / 3.0)) {
            // Right part of paddle
            if (top) {
                ball.setAngle(340);
            } else {
                ball.setAngle(20);
            }
        } else {
            // Middle part of paddle
            if (top) {
                if (angle > 90) {
                    ball.setAngle(angle + 90);
                } else {
                    ball.setAngle(angle - 90);
                }
            } else {
                if (angle > 270) {
                    ball.setAngle(angle + 90);
                } else {
                    ball.setAngle(angle - 90);
                }
            }
        }
    }
}
