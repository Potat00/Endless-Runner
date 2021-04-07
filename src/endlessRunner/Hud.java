package endlessRunner;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Hud extends HBox {
    private int coins;
    private double distance;
    private Circle coinImage;
    private Text coinsText;
    private Text distanceText;
    private Image backroundImage;


    Hud(double WIDTH, double HEIGHT){

        this.coinsText = new Text(0, 0, "" + this.coins);
        this.coinsText.setFont(new Font(HEIGHT/10));
        this.coinsText.setFill(Color.WHEAT);
        this.coinsText.setStroke(Color.WHITE);
        this.coinsText.setStrokeWidth(3);
        this.coinsText.setTextAlignment(TextAlignment.CENTER);

        this.distanceText = new Text();
        this.distanceText.setFont(new Font(HEIGHT/10));
        this.distanceText.setFill(Color.WHEAT);
        this.distanceText.setTextAlignment(TextAlignment.CENTER);


        this.coinImage = new Circle(HEIGHT/5);
        this.coinImage.setRadius(HEIGHT/10);

        try {
            this.coinImage.setFill(new ImagePattern(new Image(new FileInputStream("src/tex/coin1.jpg"))));
            this.backroundImage = new Image(new FileInputStream("src/tex/hudBackground.jpg"), WIDTH, HEIGHT/5, false, false);
        } catch (FileNotFoundException e) {
            System.out.println("File Not found");
        }

        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);
        this.setBackground(new Background(
                new BackgroundImage(backroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(coinImage, coinsText);

        getChildren().addAll(stackPane, distanceText);
    }

    /**
     * Nastavi hodnotu penazi, ktora sa ma zobrazit
     */
    public void setCoins(int coins) {
        this.coins = coins;
        this.coinsText.setText("" + this.coins);
    }

    /**
     * Nastavvi hodnotu vzdialenost, ktora sa ma zobrazit
     */
    public void setDistance(double distance){
        this.distance = distance;
        this.distanceText.setText("Distance: "+ (int)this.distance + " m");
    }


}
