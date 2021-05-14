import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;

public class BottomWall extends Wall {
    private final MediaPlayer sound;

    public BottomWall(Position pos, double width, double height, Color color) {
        super(pos, width, height, color);

        setOpacity(0.5);
        sound =
                new MediaPlayer(
                        new Media(
                                Objects.requireNonNull(
                                                getClass()
                                                        .getClassLoader()
                                                        .getResource("resources/impact.mp3"))
                                        .toString()));
    }

    @Override
    public void act(long now) {
        setX(0.0);
        setY(getWorld().getHeight() - getHeight());

        if (getHeight() > getWidth()) {
            setFitHeight(getWorld().getHeight());
        } else {
            setFitWidth(getWorld().getWidth());
        }
    }

    @Override
    void onCollision(CollisionItem other) {
        if (!(other instanceof Ball)) {
            return;
        }

        final Ball ball = (Ball) other;
        final double angle = ball.getAngle();

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

        // FIXME: This stutters the first time till it loads and initializes player
        sound.seek(Duration.ZERO);
        sound.play();
    }
}
