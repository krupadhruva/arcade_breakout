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
		
		// BallWorld Objects
		Paddle paddle = new Paddle();
		paddle.setX(300);
		paddle.setY(350);
		
		Ball ball = new Ball(3, 3);
		ball.setX(300);
		ball.setY(200);
		
		// test bricks; remove if you need to
		Brick brick1 = new Brick();
		brick1.setX(400);
		brick1.setY(200);
		Brick brick2 = new Brick();
		brick2.setX(450);
		brick2.setY(200);
		Brick brick3 = new Brick();
		brick3.setX(500);
		brick3.setY(200);
		
		// BallWorld
		BallWorld ballWorld = new BallWorld();
		ballWorld.setPrefWidth(600);
		ballWorld.setPrefHeight(400);
		ballWorld.setOnMouseMoved(mouseEvent -> paddle.setX(mouseEvent.getX() - paddle.getWidth()/2.0));
		ballWorld.setOnKeyPressed(keyEvent -> ballWorld.addKeyDown(keyEvent.getCode()));
		ballWorld.setOnKeyReleased(keyEvent -> ballWorld.removeKeyDown(keyEvent.getCode()));

		screen.setCenter(ballWorld);
		
		ballWorld.add(ball);
		ballWorld.add(paddle);
		ballWorld.add(brick1);
		ballWorld.add(brick2);
		ballWorld.add(brick3);
		ballWorld.start();

		Scene scene = new Scene(screen);
		stage.setScene(scene);
		stage.show();

		ballWorld.requestFocus();
	}

}
