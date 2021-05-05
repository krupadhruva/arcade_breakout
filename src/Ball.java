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
		
		// Wall collision
		if (this.getX() + this.getWidth() >= this.getWorld().getWidth()
				|| this.getX() <= 0) {
			dx = dx * -1;
		}
		else if (this.getY() + this.getHeight() >= this.getWorld().getHeight()
				|| this.getY() <= 0) {
			dy = dy * -1;
		}

		// Paddle collision
		if(this.getOneIntersectingObject(Paddle.class) == null){

		}
		else if (this.getOneIntersectingObject(Paddle.class).getClass().getTypeName().equals(Paddle.class.getTypeName())){
			dy = dy * -1;
		}
		
		// Brick collision
		if(this.getOneIntersectingObject(Brick.class) == null){

		}
		else if (this.getOneIntersectingObject(Brick.class).getClass().getTypeName().equals(Brick.class.getTypeName())) {
			
			Brick brick = this.getOneIntersectingObject(Brick.class);
			
			if (this.getX() + this.getWidth()/2 >= brick.getX()
					&& this.getX() + this.getWidth()/2 <= brick.getX() + brick.getWidth()) {
				dy = dy * -1;
			}
			else if (this.getY() + this.getHeight()/2 >= brick.getY()
					&& this.getY() + this.getHeight()/2 <= brick.getY() + brick.getHeight()) {
				dx = dx * -1;
			}
			else {
				dx = dx * -1;
				dy = dy * -1;
			}
			
		}
			
	}

}
