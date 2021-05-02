import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Game extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Ball Game");
		
		BorderPane screen = new BorderPane();
		
		BallWorld ballWorld = new BallWorld();
		ballWorld.setPrefWidth(600);
		ballWorld.setPrefHeight(400);
		
		screen.setCenter(ballWorld);
		
		
		Ball ball = new Ball(5, 5);
		ball.setX(300);
		ball.setY(200);
		
		ballWorld.add(ball);
		
		ballWorld.start();
		
		
		Scene scene = new Scene(screen);
		stage.setScene(scene);
		stage.show();
	}

}
