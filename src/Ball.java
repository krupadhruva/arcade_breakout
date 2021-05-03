import javafx.scene.image.Image;

public class Ball extends Actor {

	// Attributes
	private double dx;
	private double dy;
	
	
	// Constructor
	public Ball(double dx, double dy) {
		
		String path = getClass().getClassLoader().getResource("resources/ball.png").toString();
		Image img = new Image(path);
		this.setImage(img);
		
		this.dx = dx;
		this.dy = dy;
		
	}
	
	
	@Override
	public void act(long now) {
		
		this.move(dx, dy);
		
		if (this.getX() + this.getWidth() >= this.getWorld().getWidth()
				|| this.getX() <= 0) {
			dx = dx * -1;
		}
		else if (this.getY() + this.getHeight() >= this.getWorld().getHeight()
				|| this.getY() <= 0) {
			dy = dy * -1;
		}

		if(this.getOneIntersectingObject(Paddle.class).equals(Paddle.class)){
			dy = dy * -1;
		}
			
	}

}
