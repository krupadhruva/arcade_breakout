import javafx.scene.image.Image;

public class Brick extends CollisionItem {

    public Brick(double x, double y) {
        final String path =
                getClass().getClassLoader().getResource("resources/brick.png").toString();
        setImage(new Image(path));

        setX(x);
        setY(y);
    }

    @Override
    void onCollision(CollisionItem other) {
        getWorld().remove(this);
    }
}
