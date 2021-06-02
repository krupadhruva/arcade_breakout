import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Game extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    // Set the image path as background in pane
    private void setBackground(BorderPane pane, String path) {
        pane.setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(
                                        Objects.requireNonNull(
                                                        getClass()
                                                                .getClassLoader()
                                                                .getResource(path))
                                                .toString()),
                                null,
                                null,
                                BackgroundPosition.CENTER,
                                null)));
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ball Game");

        // Setup a pane with light background image
        final BorderPane screen = new BorderPane();
        setBackground(screen, "resources/light_background.png");

        // Score card
        Score score = new Score();

        // BallWorld
        BallWorld ballWorld = new BallWorld(score);
        ballWorld.setPrefWidth(600);
        ballWorld.setPrefHeight(400);
        ballWorld.setOnKeyPressed(keyEvent -> ballWorld.addKeyDown(keyEvent.getCode()));
        ballWorld.setOnKeyReleased(keyEvent -> ballWorld.removeKeyDown(keyEvent.getCode()));

        screen.setCenter(ballWorld);
        screen.setTop(score);

        // BallWorld Objects
        ballWorld.add(new Ball(300, 100, 6, 6, 3));
        ballWorld.add(new Paddle(300, 350));

        // Add side walls - will make them selectively reactive later

        ballWorld.add(new Wall(Wall.Position.TOP, 100, 5, Color.CADETBLUE));
        ballWorld.add(new Wall(Wall.Position.LEFT, 5, 100, Color.CADETBLUE));
        ballWorld.add(new Wall(Wall.Position.RIGHT, 5, 100, Color.CADETBLUE));
        ballWorld.add(new BottomWall(Wall.Position.BOTTOM, 100, 5, Color.CRIMSON));

        // TODO: Create different/random patterns
        for (int row = 1; row < 5; ++row) {
            ballWorld.add(new Brick(100, (row * 50), 25, 10));
            ballWorld.add(new Brick(300, (row * 50), 25, 10));
            ballWorld.add(new Brick(500, (row * 50), 25, 10));
        }

        Scene scene = new Scene(screen);
        stage.setScene(scene);
        stage.show();

        ballWorld.start();
        ballWorld.requestFocus();
    }
}
