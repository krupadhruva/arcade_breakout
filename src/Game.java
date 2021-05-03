import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

		Paddle paddle = new Paddle();
		paddle.setX(300);
		paddle.setY(350);

		BallWorld ballWorld = new BallWorld();
		ballWorld.setPrefWidth(600);
		ballWorld.setPrefHeight(400);
		ballWorld.setOnMouseMoved(mouseEvent -> paddle.setX(mouseEvent.getX() - paddle.getWidth()/2.0));

		ballWorld.setOnKeyPressed(keyEvent -> ballWorld.addKeyDown(keyEvent.getCode()));

		ballWorld.setOnKeyReleased(keyEvent -> ballWorld.removeKeyDown(keyEvent.getCode()));

		screen.setCenter(ballWorld);

		Ball ball = new Ball(5, 5);
		ball.setX(300);
		ball.setY(200);

		ballWorld.add(ball);
		ballWorld.add(paddle);
		ballWorld.start();

		Scene scene = new Scene(screen);
		stage.setScene(scene);
		stage.show();

		ballWorld.requestFocus();
	}

}
