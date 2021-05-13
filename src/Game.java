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

        // BallWorld
        BallWorld ballWorld = new BallWorld();
        ballWorld.setPrefWidth(600);
        ballWorld.setPrefHeight(400);
        ballWorld.setOnKeyPressed(keyEvent -> ballWorld.addKeyDown(keyEvent.getCode()));
        ballWorld.setOnKeyReleased(keyEvent -> ballWorld.removeKeyDown(keyEvent.getCode()));

        screen.setCenter(ballWorld);

        // BallWorld Objects
        ballWorld.add(new Ball(300, 200, 7, 7));
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
