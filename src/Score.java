import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Score extends Text {
    // Attributes
    private int value;

    // Constructors
    public Score() {
        value = 0;
        setFont(Font.font(14));
        setFill(Color.DARKBLUE);
        setTextAlignment(TextAlignment.CENTER);
        updateDisplay();
    }

    // Methods
    public void updateDisplay() {
        this.setText(String.format("Score: %d", value));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        value = val;
        updateDisplay();
    }
}
