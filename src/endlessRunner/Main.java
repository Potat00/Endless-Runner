package endlessRunner;

import javafx.application.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    public static void main(String[] args) { launch(args);}

    private final int WIDTH = 1000;
    private final int HEIGHT = 800;

    /**
     * Kolko krat za sekundu sa ma obraz vykreslovat
     */
    static final int fps = 60;

    private PerspectiveCamera mainCamera;
    private Group mainGroup = new Group();
    private SubScene mainScene = new SubScene(mainGroup, WIDTH, HEIGHT*0.9, true, SceneAntialiasing.BALANCED);
    private Hud mainHud = new Hud(WIDTH, HEIGHT);
    private BorderPane layout = new BorderPane();
    private Scene root = new Scene(layout);
    private Player player;
    private Map map;

    private boolean gameOver = false;
    private double time;
    private int coins;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initGame();
        primaryStage.setScene(root);
        primaryStage.setTitle("Endless Runner");
        primaryStage.show();

    }

    /**
     * inicializuje zakladne komponenty hry
     */
    public void initGame() {
        map = new Map(20);
        player = new Player();
        mainScene.setFill(Color.LIGHTBLUE);
        layout.setCenter(mainScene);
        layout.setBottom(mainHud);
        mainGroup.getChildren().add(player);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000.0/fps), event -> {
            update();
        }));
        animation.setCycleCount( Animation.INDEFINITE );
        animation.play();

        prepareCamera();
        initControls();
    }


    /**
     * Pripravi kameru, nastavi jej potrebne hodnoty.
     */
    private void prepareCamera() {
        mainCamera = new PerspectiveCamera(true);
        mainCamera.setFieldOfView(50);
        mainCamera.setNearClip(0.1);
        mainCamera.setFarClip(2000);
        mainCamera.setTranslateZ(-200);
        mainCamera.setTranslateY(-200);
        mainCamera.setRotationAxis(new Point3D(1,0,0));
        mainCamera.setRotate(-30);
        mainScene.setCamera(mainCamera);
    }

    /**
     * hlavny update hry.
     */
    private void update(){
        time += 1.0/fps;
        player.update();

        if(gameOver){
            return;
        }

        checkPlayerCollision();
        if(map.getCoin(player.getSide())){
            coins++;
        }
        map.update();
        mainHud.setCoins(coins);
        mainHud.setDistance(time*5.0);
        mainGroup.getChildren().clear();
        mainGroup.getChildren().addAll(map.getMapGroup(), player, new AmbientLight(Color.WHEAT));
    }

    /**
     * Koniec hry, zobrazi skore atd.
     */
    private void gameOver(){
        System.out.println("Game Over");
        double distance = time * 5;
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            EndSreen endSreen = new EndSreen(coins, (int)distance);
            layout.setCenter(endSreen);
            layout.setBottom(null);
            endSreen.setOnKeyPressed(e -> {
                if(e.getCode() == KeyCode.SPACE){
                    restart();
                }
            });
        }));
        animation.setCycleCount( 1 );
        animation.play();
        gameOver = true;
    }

    /**
     * Restartuje hru.
     */
    private void restart(){
        map = new Map(20);
        player = new Player();
        layout.setCenter(mainScene);
        layout.setBottom(mainHud);
        time = 0;
        coins = 0;
        gameOver = false;
    }

    /**
     * Zisti ci, a do coho hrac narazil.
     */
    private void checkPlayerCollision(){
        if(map.isObstacelAtZ0(player.getSide())){
            switch (map.getObstacleType(player.getSide())){
                case JUMPOVER:
                    if(!player.isJumping()) {
                        player.wallSquash();
                        gameOver();
                        return;
                    }
                    if(player.getTranslateY() > -50){
                        gameOver();
                        return;
                    }
                    break;
                case FULLBARICADE:
                    player.wallSquash();
                    if(player.isJumping()){
                        player.fall(0.1,0);
                    }
                    gameOver();
                    return;
                case HOLE:
                    if(!player.isJumping()){
                        gameOver();
                        player.fall(5,3);
                    }
                    break;
                case SLIDEUNDER:
                    if(!player.isSquashing()){
                        player.wallSquash();
                        player.fall(0.1,0);
                        gameOver();
                    }
                    break;
            }


        }
    }

    /**
     * Inicializuje ovladanie pre hraca.
     */
    private void initControls(){
        mainScene.setFocusTraversable(true);
        mainScene.setOnKeyPressed(e -> {
            if(gameOver){
                return;
            }
            if(e.getCode() == KeyCode.A){
                player.moveLeft();
            }
            if(e.getCode() == KeyCode.D){
                player.moveRight();
            }
            if(e.getCode() == KeyCode.W){
                player.jump();
            }
            if(e.getCode() == KeyCode.S){
                player.squash();
            }else{
                player.unsquash();
            }

        });
    }


    /**
     * 3 strany trate.
     */
    public enum Side {
        LEFT,
        MIDDLE,
        RIGHT
    }


}
