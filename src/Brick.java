import javafx.scene.image.Image;

public class Brick extends Actor {

	public Brick() {
		String path = getClass().getClassLoader().getResource("resources/brick.png").toString();
        Image img = new Image(path);
        this.setImage(img);
	}
	
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}

}
