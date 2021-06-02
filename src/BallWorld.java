import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class BallWorld extends World {
    private final Score score;

    public BallWorld(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public void gameOver() {
        final List<Ball> collisions = getObjects(Ball.class);
        if (!collisions.isEmpty()) {
            remove(collisions.get(0));
        }

        String msg =
                String.format(
                        "** Game Over, you %s **",
                        getScore().getValue() > 0 ? "win :-)" : "loose :-(");
        Text txt = new Text(msg);
        txt.setFill(Color.RED);
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setFont(Font.font("Monaco", 16));

        BorderPane screen = (BorderPane) getParent();
        screen.setBackground(
                new Background(
                        new BackgroundImage(
                                txt.snapshot(null, null),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                null)));
    }

    @Override
    public void act(long now) {}
}
