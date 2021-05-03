import javafx.scene.image.Image;

public class Paddle extends Actor{

    public Paddle(){
        String path = getClass().getClassLoader().getResource("resources/paddle.png").toString();
        Image img = new Image(path);
        this.setImage(img);
    }

    @Override
    public void act(long now) {

    }
}
