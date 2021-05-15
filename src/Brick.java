import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Brick extends CollisionItem {
    // Once collided, stop processing on further collisions since we
    // remove collided item later with exploding animation
    private boolean hasCollided;

    private final Image explodeImg;

    public Brick(double x, double y) {
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
        setX(x);
        setY(y);
    }

    @Override
    void onCollision(CollisionItem other) {
        // This brick has collided and is exploding, nothing much to do...
        if (hasCollided) {
            return;
        }

        hasCollided = true;

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
    }
}
