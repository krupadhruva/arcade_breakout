
public class BallWorld extends World {

	private Score score;

	public BallWorld(){
		score = new Score();
		score.setValue(0);
		score.setX(100);
		score.setY(100);
		getChildren().add(score);
	}

	public Score getScore(){
		return score;
	}

	@Override
	public void act(long now) {
		

	}



}
