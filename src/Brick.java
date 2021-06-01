import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Brick extends CollisionItem {
    // Once collided, stop processing on further collisions since we
    // remove collided item later with exploding animation
    private boolean hasCollided;

    private final Image explodeImg;

    public Brick(double x, double y, double width, double height) {
        hasCollided = false;
        explodeImg =
                new Image(
                        getClass()
                                .getClassLoader()
                                .getResource("resources/explode.png")
                                .toString());

        setImage(
                new Image(
                        getClass().getClassLoader().getResource("resources/brick.png").toString()));

        // Fix the brick size so that we can replace with any image
        setFitWidth(width);
        setFitHeight(height);

        setX(x);
        setY(y);
    }

    @Override
    void onCollision(CollisionItem other) {
        // This brick has collided and is exploding, nothing much to do...
        if (hasCollided || !(other instanceof Ball)) {
            return;
        }

        hasCollided = true;

        // Rebound the ball on collision with brick
        final Ball ball = (Ball) other;
        ball.setAngle(ball.getAngle() + 90.0);

        final Timeline tl =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(100),
                                event -> {
                                    setFitWidth(0.8 * getWidth());
                                    setFitHeight(0.8 * getHeight());
                                }));
        tl.setCycleCount(10);
        tl.setOnFinished(e -> getWorld().remove(this));
        tl.play();

        double origW = getWidth(), origH = getHeight();
        setImage(explodeImg);
        setFitWidth(1.5 * origW);
        setFitHeight(1.5 * origH);

        // End game when all bricks have exploded
        if (getWorld().getObjects(Brick.class).size() <= 1) {
            getWorld().remove(ball);
            Text txt = new Text("** Game Over **");
            txt.setFill(Color.RED);
            txt.setTextAlignment(TextAlignment.CENTER);
            txt.setFont(Font.font("Monaco", 16));

            BorderPane screen = (BorderPane) getWorld().getParent();
            screen.setBackground(
                    new Background(
                            new BackgroundImage(
                                    txt.snapshot(null, null),
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,
                                    null)));
        }
    }
}
