package endlessRunner;

import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class EndSreen extends VBox {
    EndSreen(int coins, double distance){
        Text text = new Text();
        text.setText("FUCK U BITCH U LOST");
        text.setFont(new Font(60));
        text.setFill(Color.RED);

        Text text1 = new Text();
        text1.setText("Distance: " + distance + "m");
        text1.setFont(new Font(40));

        Text text2 = new Text();
        text2.setText("Coins collected: " + coins);
        text2.setFont(new Font(40));

        Text text3 = new Text();
        text3.setText("Press SPACE to restart");
        text3.setFont(new Font(20));
        text3.setFill(Color.gray(0.5));

        this.setAlignment(Pos.CENTER);
        setFocusTraversable(true);
        this.getChildren().addAll(text,text1,text2, text3);

    }
}
