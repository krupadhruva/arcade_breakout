import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends CollisionItem {
    private final Position pos;
    private final Color color;

    enum Position {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }

    public Wall(Position pos, double width, double height, Color color) {
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(color);
        setImage(rect.snapshot(null, null));

        this.pos = pos;
        this.color = color;

        if (pos == Position.BOTTOM) {
            setOpacity(0.25);
        }
    }

    @Override
    public void act(long now) {
        // FIXME: Support resizing the walls when window size changes
        // Can we listen to window resize events instead?

        switch (pos) {
            case TOP:
            case LEFT:
                setX(0.0);
                setY(0.0);
                break;
            case BOTTOM:
                setX(0.0);
                setY(getWorld().getHeight() - getHeight());
                break;
            case RIGHT:
                setX(getWorld().getWidth() - getWidth());
                setY(0.0);
                break;
        }

        if (getHeight() > getWidth()) {
            setFitHeight(getWorld().getHeight());
        } else {
            setFitWidth(getWorld().getWidth());
        }
    }

    @Override
    void onCollision(CollisionItem other) {
        if (other instanceof Ball) {
            final Ball ball = (Ball) other;

            switch (pos) {
                case TOP:
                    ball.setY(getY() + getHeight());
                    ball.reverseY();
                    break;
                case BOTTOM:
                    ball.setY(getY() - ball.getHeight());
                    ball.reverseY();
                    setOpacity(1);

                    // Score adjustment on ball hitting bottom wall
                    final BallWorld world = (BallWorld) getWorld();
                    world.getScore().setValue(world.getScore().getValue() - 1);

                    break;
                case LEFT:
                    ball.setX(getX() + getWidth());
                    ball.reverseX();
                    break;
                case RIGHT:
                    ball.setX(getX() - ball.getWidth());
                    ball.reverseX();
                    break;
            }
        }
    }
}
