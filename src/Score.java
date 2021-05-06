import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text {
    //Attributes
    private int value;

    //Constructors
    public Score(){
        value = 0;
        this.setFont(Font.font(14));
        updateDisplay();
    }

    //Methods
    public void updateDisplay(){
        this.setText(String.valueOf(value));
    }

    public int getValue(){
        return value;
    }

    public void setValue(int val){
        value = val;
        updateDisplay();
    }
}
