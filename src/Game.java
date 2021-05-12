import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Game extends Application {

    Paddle paddle;
    BallWorld ballWorld;
    double mouseX;
    double mouseY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Ball Game");

        MyAnimationTimer animationTimer = new MyAnimationTimer();
        animationTimer.start();

        BorderPane screen = new BorderPane();

        // BallWorld Objects
        paddle = new Paddle();
        paddle.setX(300);
        paddle.setY(350);

        Ball ball = new Ball(6, 6);
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
        ballWorld = new BallWorld();
        ballWorld.setPrefWidth(600);
        ballWorld.setPrefHeight(400);
        ballWorld.setOnKeyPressed(keyEvent -> ballWorld.addKeyDown(keyEvent.getCode()));
        ballWorld.setOnKeyReleased(
                keyEvent -> {
                    ballWorld.removeKeyDown(keyEvent.getCode());
                    paddle.setMoveDirection(0);
                });
        ballWorld.setOnMouseMoved(
                mouseEvent -> {
                    mouseX = mouseEvent.getX();
                    mouseY = mouseEvent.getY();

                    if (mouseEvent.getX() - paddle.getWidth() / 2.0 > paddle.getX()) {
                        paddle.setMoveDirection(1);
                    } else if (mouseEvent.getX() - paddle.getWidth() / 2.0 < paddle.getX()) {
                        paddle.setMoveDirection(-1);
                    }
                    paddle.setX(mouseEvent.getX() - paddle.getWidth() / 2.0);
                });

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

    // Every millisecond (1e6 nanoseconds), test if the mouse
    // position changed. If it didn't, set paddle's move direction
    // to 0.
    private class MyAnimationTimer extends AnimationTimer {

        long oldTime = 0;
        double mX = 0;
        double mY = 0;

        @Override
        public void handle(long now) {
            if (now - oldTime >= 1e6) {

                if (ballWorld.isNoKeyDown()) {
                    if (mouseX == mX && mouseY == mY) {
                        paddle.setMoveDirection(0);
                    }
                    mX = mouseX;
                    mY = mouseY;
                }
                oldTime = now;
            }
        }
    }
}
