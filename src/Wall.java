import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class Wall extends CollisionItem {
    private final Position pos;
    private final Color color;
    private final MediaPlayer sound;

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
            sound =
                    new MediaPlayer(
                            new Media(
                                    Objects.requireNonNull(
                                                    getClass()
                                                            .getClassLoader()
                                                            .getResource("resources/impact.mp3"))
                                            .toString()));
        } else {
            sound = null;
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
            final double angle = ball.getAngle();

            switch (pos) {
                case TOP:
                    ball.setY(getY() + getHeight());
                    if (angle >= 270) {
                        ball.setAngle(45);
                    } else {
                        ball.setAngle(135);
                    }

                    break;
                case BOTTOM:
                    ball.setY(getY() - ball.getHeight());
                    if (angle >= 90) {
                        ball.setAngle(225);
                    } else {
                        ball.setAngle(315);
                    }

                    setOpacity(1);

                    // Score adjustment on ball hitting bottom wall
                    final BallWorld world = (BallWorld) getWorld();
                    world.getScore().setValue(world.getScore().getValue() - 1);

                    if (sound != null) {
                        sound.seek(Duration.ZERO);
                        sound.play();
                    }
                    break;
                case LEFT:
                    ball.setX(getX() + getWidth());
                    if (angle >= 180) {
                        ball.setAngle(315);
                    } else {
                        ball.setAngle(45);
                    }

                    break;
                case RIGHT:
                    ball.setX(getX() - ball.getWidth());
                    if (angle >= 270) {
                        ball.setAngle(225);
                    } else {
                        ball.setAngle(135);
                    }

                    break;
            }
        }
    }
}
