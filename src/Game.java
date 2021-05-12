import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ball Game");
        BorderPane screen = new BorderPane();

        // BallWorld
        BallWorld ballWorld = new BallWorld();
        ballWorld.setPrefWidth(600);
        ballWorld.setPrefHeight(400);
        ballWorld.setOnKeyPressed(keyEvent -> ballWorld.addKeyDown(keyEvent.getCode()));
        ballWorld.setOnKeyReleased(keyEvent -> ballWorld.removeKeyDown(keyEvent.getCode()));

        screen.setCenter(ballWorld);

        // BallWorld Objects
        ballWorld.add(new Ball(300, 200, 6, 6));
        ballWorld.add(new Paddle(300, 350));

        // Add side walls - will make them selectively reactive later
        ballWorld.add(new Wall(Wall.Position.TOP, 100, 5, Color.CADETBLUE));
        ballWorld.add(new Wall(Wall.Position.BOTTOM, 100, 5, Color.CRIMSON));
        ballWorld.add(new Wall(Wall.Position.LEFT, 5, 100, Color.CADETBLUE));
        ballWorld.add(new Wall(Wall.Position.RIGHT, 5, 100, Color.CADETBLUE));

        // test bricks; remove if you need to
        ballWorld.add(new Brick(400, 200));
        ballWorld.add(new Brick(450, 200));
        ballWorld.add(new Brick(500, 200));

        Scene scene = new Scene(screen);
        stage.setScene(scene);
        stage.show();

        ballWorld.start();
        ballWorld.requestFocus();
    }
}
