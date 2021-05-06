import javafx.scene.image.Image;

public class Ball extends Actor {

	// Attributes
	private double dx;
	private double dy;

	// Used to prevent altering magnitude on continuous repeat collisions
	private boolean magnitudeAltered;

	// Required to restore magnitude to prevent gain/dampening effect
	final private double origDx;
	final private double origDy;

	// Comparing doubles with user defined precision
	static double round(double val, double precision) {
		double factor = Math.pow(10.0, precision);
		return (double)((int)(val * factor))/factor;
	}

	// Helper method to find direction of ball
	static double getSign(double val) {
		return val < 0.0 ? -1.0 : 1.0;
	}

	// Constructor
	public Ball(double dx, double dy) {
		origDx = dx;
		origDy = dy;
		magnitudeAltered = false;

		String path = getClass().getClassLoader().getResource("resources/ball.png").toString();
		Image img = new Image(path);
		this.setImage(img);
		
		this.dx = dx;
		this.dy = dy;
		
	}
	
	
	@Override
	public void act(long now) {
		
		this.move(dx, dy);

		//score adjustment
		if((this.getY() + this.getHeight()) >= this.getWorld().getHeight()){
			int value = ((BallWorld)getWorld()).getScore().getValue() - 1000;
			((BallWorld)getWorld()).getScore().setValue(value);
		}

		// Wall collision: Restore velocity along X & Y axis to prevent gain
		// Done by flipping direction by restoring magnitude
		if ((this.getX() + this.getWidth()) >= this.getWorld().getWidth()
				|| (this.getX() - this.getWidth()) <= 0) {
			magnitudeAltered = false;
			dx = -1.0 * origDx * getSign(dx);
		}

		if ((this.getY() + this.getHeight()) >= this.getWorld().getHeight()
				|| (this.getY() - this.getHeight()) <= 0) {
			magnitudeAltered = false;
			dy = -1.0 * origDy * getSign(dy);
		}

		// Paddle collision: Alter the X & Y velocity to give a sharp bounce
		// Done by flipping direction if needed and magnitude by a factor
		final Paddle paddle = getOneIntersectingObject(Paddle.class);
		if (paddle != null){
			final double fromLeftEdge = round(getX() - (paddle.getX() - paddle.getWidth()/2.0), 2);

			// Between 1/3rd and 2/3rd (for others, we alter magnitude)
			dy = -dy;

			if (fromLeftEdge < paddle.getWidth()/3.0) {
				// 1/3rd and lower region
				if (!magnitudeAltered) {
					magnitudeAltered = true;
					dy = 0.8 * dy;
					dx = 1.2 * dx;
				}
			} else if (fromLeftEdge >= paddle.getWidth() * 2.0/3.0) {
				// 2/3rd and greater region
				if (!magnitudeAltered) {
					magnitudeAltered = true;
					dy = 0.8 * dy;
					dx = 1.2 * dx;
				}
			}
		}
		
		// Brick collision
		final Brick brick = this.getOneIntersectingObject(Brick.class);
		if (brick != null){
			//set score after hitting brick
			int value = ((BallWorld)getWorld()).getScore().getValue() + 100;
			((BallWorld)getWorld()).getScore().setValue(value);


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

			brick.getWorld().remove(brick);
		}


	}
}
