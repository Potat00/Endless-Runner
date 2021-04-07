package mapBloks;


import endlessRunner.*;
import javafx.scene.Group;

/**
 * abstraktny blok mapy ktory sluzi na skonstruovanie jednotlivych blokov mapy.
 */
public abstract class MapBlock{
    //[0]-start position   [1]-end position
    private double[] leftObstacle = new double[2];
    private double[] middleObstacle = new double[2];
    private double[] rightObstacle = new double[2];

    private ObstacleType leftObstacleType;
    private ObstacleType middleObstacleType;
    private ObstacleType rightObstacleType;

    private boolean[] leftCoins = new boolean[3];
    private boolean[] middleCoins = new boolean[3];
    private boolean[] rightCoins = new boolean[3];

    private Group coins = new Group();


    /**
     * Zisti ci ma prekazku na danej strane.
     * @param side pre ktoru stranu
     * @return ma blok prekazku na danej strane?
     */
    public boolean hasObstacleAt(Main.Side side){
        switch (side){
            case LEFT:
                return _hasLeftObstacle;
            case MIDDLE:
                return _hasMiddleObstacle;
            case RIGHT:
                return _hasRightObstacle;
        }
        return false;
    }

    private boolean _hasLeftObstacle = false;
    private boolean _hasMiddleObstacle = false;
    private boolean _hasRightObstacle = false;

    private Group model;


    public MapBlock(){
        model = new Group();
        model.getChildren().add(coins);
        generateBlock();
        generateCoins();
    }


    /**
     *  Instancia skupiny objektov bloku mapy.
     * @return vrati instanciu skupiny objektov ktore reprezentuju blok mapy.
     */
    public Group getModel() {
        return model;
    }

    /**
     *  Nastavi Z poziciu bloku.
     * @param z nastavi Z poziciu bloku mapy
     */
    public void setModelZPosition(double z){
        model.setTranslateZ(z);
    }


    /**
     * Typ prekazky.
     */
    public enum ObstacleType {
        JUMPOVER,
        FULLBARICADE,
        HOLE,
        SLIDEUNDER
    }


    abstract void generateBlock();

    /**
     *  Zisti poziciu prekazky.
     * @param side Pre ktoru stranu.
     * @return vrati double[] zacaitku a koncu prekazky na Z pozici. [0]-zaciatok; [1]-koniec
     */
    public double[] getObstacleStartEndPosition(Main.Side side){
        double[] out = new double[2];
        switch (side) {
            case LEFT:
                out[0] = leftObstacle[0] + model.getTranslateZ() -150;
                out[1] = leftObstacle[1] + model.getTranslateZ() -150;
                break;
            case MIDDLE:
                out[0] = middleObstacle[0] + model.getTranslateZ() - 150;
                out[1] = middleObstacle[1] + model.getTranslateZ() - 150;
                break;
            case RIGHT:
                out[0] = rightObstacle[0] + model.getTranslateZ() -150;
                out[1] = rightObstacle[1] + model.getTranslateZ() -150;
                break;
        }
        return out;
    }

    /**
     * generuje peniaze na volnych miestach
     */
    private void generateCoins(){
        switch ((int)(Math.random()*3)){
            case(0):
                if (!hasObstacleAt(Main.Side.LEFT)){
                    for (int i = 0; i < 3; i++) {
                        leftCoins[i] = true;

                    }
                }
                break;
            case(1):
                if (!hasObstacleAt(Main.Side.MIDDLE)){
                    middleCoins[0] = true;
                    middleCoins[1] = true;
                    middleCoins[2] = true;
                }
                break;
            case(2):
                if (!hasObstacleAt(Main.Side.RIGHT)){
                    rightCoins[0] = true;
                    rightCoins[1] = true;
                    rightCoins[2] = true;
                }
                break;
        }
        drawCoins();

    }

    private void drawCoins(){
        coins.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            if (leftCoins[i] == true){
                Group coin = new Group( Resources.getInstance().getModel("src/models/coin.obj"));
                coin.setTranslateX(-100);
                coin.setTranslateZ((i*100)-100);
                coin.setTranslateY(-30);
                coins.getChildren().add(coin);
            }
            if (middleCoins[i] == true){
                Group coin = new Group( Resources.getInstance().getModel("src/models/coin.obj"));
                coin.setTranslateX(0);
                coin.setTranslateZ((i*100)-100);
                coin.setTranslateY(-30);
                coins.getChildren().add(coin);
            }
            if (rightCoins[i] == true){
                Group coin = new Group(Resources.getInstance().getModel("src/models/coin.obj"));
                coin.setTranslateX(100);
                coin.setTranslateZ((i*100)-100);
                coin.setTranslateY(-30);
                coins.getChildren().add(coin);
            }

        }


    }

    /**
     * Zoberie mincu ak sa tam nachadza.
     * @param side Pre ktoru stranu.
     * @return vrati boolean ci na danej strane sa nachadza minca ak ano tak ju zobere a returne true, inak false.
     */
    public boolean getCoinAt(Main.Side side){

        int i = -1;

        double pos = model.getTranslateZ() -50;
        if(Math.abs(pos) <= 50){
            i = 0;
        }
        pos = model.getTranslateZ() + 50;
        if(Math.abs(pos) <= 50){
            i = 1;
        }
        pos = model.getTranslateZ() +150;
        if(Math.abs(pos) <= 50){
            i = 2;
        }

        if(i == - 1){
            return false;
        }
        switch (side) {
            case LEFT:
                if(leftCoins[i]){
                    leftCoins[i] = false;
                    drawCoins();
                    return true;
                }
                break;
            case MIDDLE:
                if(middleCoins[i]){
                    middleCoins[i] = false;
                    drawCoins();
                    return true;
                }
                break;
            case RIGHT:
                if(rightCoins[i]){
                    rightCoins[i] = false;
                    drawCoins();
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Zisti typ prekazky.
     * @param sideToGet pre ktoru stranu ma vratit.
     * @return vrati typ prekazky na danej strane
     */
    public ObstacleType getObstacleType(Main.Side sideToGet){
        switch (sideToGet){
            case LEFT:

                return leftObstacleType;
            case MIDDLE:

                return middleObstacleType;
            case RIGHT:

                return rightObstacleType;
        }
        return null;
    }

    /**
     * Nastaví prekazku a informacie o nej.
     * @param obstacleSide Na ktorej strane sa prekazka nachadza.
     * @param obstacleType Typ prekazky.
     * @param start Zaciatok prekazky.
     * @param end Koniec prekazky.
     */
    public void setObstacle(Main.Side obstacleSide, ObstacleType obstacleType, double start, double end){
        switch (obstacleSide){
            case LEFT:
                _hasLeftObstacle = true;
                leftObstacleType = obstacleType;
                leftObstacle[0] = start;
                leftObstacle[1] = end;
                break;
            case MIDDLE:
                _hasMiddleObstacle = true;
                middleObstacleType = obstacleType;
                middleObstacle[0] = start;
                middleObstacle[1] = end;
                break;
            case RIGHT:
                _hasRightObstacle = true;
                rightObstacleType = obstacleType;
                rightObstacle[0] = start;
                rightObstacle[1] = end;
                break;
        }
    }

}