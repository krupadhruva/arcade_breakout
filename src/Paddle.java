import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends CollisionItem {
    // Attributes
    private double delta;
    private boolean lazyInitialized;

    // Constructors
    public Paddle(double x, double y) {
        final String path =
                getClass().getClassLoader().getResource("resources/paddle.png").toString();
        setImage(new Image(path));
        setX(x);
        setY(y);

        delta = 5.0;
        lazyInitialized = false;
    }

    // Lazily initialize mouse handler once we know we are attached to game world
    private void lazyInitializeOnce() {
        if (!lazyInitialized) {
            getWorld()
                    .setOnMouseMoved(
                            mouseEvent -> {
                                if (mouseEvent.getX() + getWidth() > getWorld().getWidth()) {
                                    setX(getWorld().getWidth() - getWidth());
                                } else {
                                    setX(mouseEvent.getX());
                                }
                            });
            lazyInitialized = true;
        }
    }

    @Override
    public void act(long now) {
        lazyInitializeOnce();

        boolean keyPressed = false;
        if (getWorld().isKeyDown(KeyCode.LEFT) && getX() > 0.0) {
            delta = -Math.abs(delta);
            keyPressed = true;
        } else if (getWorld().isKeyDown(KeyCode.RIGHT)
                && (getX() + getWidth()) < getWorld().getWidth()) {
            delta = Math.abs(delta);
            keyPressed = true;
        }

        if (keyPressed) {
            double x = getX() + delta;
            if (x >= 0.0 && (x + getWidth()) <= getWorld().getWidth()) {
                setX(x);
            }
        }
    }

    @Override
    void onCollision(CollisionItem other) {
        if (other instanceof Ball) {
            final Ball ball = (Ball) other;
            ball.setAngle(ball.getAngle() + 90);
        }
    }
}
