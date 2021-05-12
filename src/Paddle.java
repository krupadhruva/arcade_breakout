import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor {

    // Attributes
    private double delta;
    private int moveDirection;

    // Constructors
    public Paddle() {
        String path = getClass().getClassLoader().getResource("resources/paddle.png").toString();
        Image img = new Image(path);
        this.setImage(img);

        delta = 5.0;
        moveDirection = 0;
    }

    // Methods
    public int getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(int d) {
        moveDirection = d;
    }

    @Override
    public void act(long now) {

        if (getWorld().isKeyDown(KeyCode.LEFT) && getX() > 0.0) {
            moveDirection = -1;
            move(-delta, 0.0);
        } else if (getWorld().isKeyDown(KeyCode.RIGHT)
                && (getX() + getWidth()) < getWorld().getWidth()) {
            moveDirection = 1;
            move(delta, 0.0);
        }
    }
}
