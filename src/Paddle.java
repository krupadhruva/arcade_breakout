import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor{

    public Paddle(){
        String path = getClass().getClassLoader().getResource("resources/paddle.png").toString();
        Image img = new Image(path);
        this.setImage(img);
    }

    @Override
    public void act(long now) {
        final double delta = 5.0;
        if (getWorld().isKeyDown(KeyCode.LEFT)) {
            move(-delta, 0.0);
        } else if (getWorld().isKeyDown(KeyCode.RIGHT)) {
            move(delta, 0.0);
        }
    }
}
