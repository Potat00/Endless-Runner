package endlessRunner;

import javafx.scene.Group;
import mapBloks.*;

import java.util.ArrayList;
import java.util.List;

public class Map{
    private int sizeInMapBlocks;
    private List<MapBlock> mapBlocks;
    private Group mapGroup;

    Map(int sizeInMapBlocks){
        this.mapGroup = new Group();
        this.sizeInMapBlocks = sizeInMapBlocks;
        this.mapBlocks = new ArrayList<>();

        init();
    }

    /**
     * Inicializuje mapu
     */
    private void init(){
        mapBlocks.clear();
        for (int i = 0; i < sizeInMapBlocks; i++) {
            MapBlock block = getRandomBlock();
            if(i < 10){
                block = new EmptyMapBlock();
            }
            block.setModelZPosition(i*300);
            mapBlocks.add(block);
        }
    }

    /**
     * zisti ci je prekazka na pozici z = 0.
     * (kedze hrac je na pozici 0 a nepohybuje sa.)
     * @return Je prekazka na pozici z = 0?
     */
    public boolean isObstacelAtZ0(Main.Side side){
        for (int i = 0; i < 2; i++) {
            if(!mapBlocks.get(i).hasObstacleAt(side)){
                continue;
            }
            if ((mapBlocks.get(i).getObstacleStartEndPosition(side)[0] <= 0) && (mapBlocks.get(i).getObstacleStartEndPosition(side)[1] >= 0 )){
                return true;
            }
        }
        return false;
    }

    /**
     * Zisti typ prekazky.
     * @param side pre ktoru stranu ma vratit typ prekazky
     * @return vrati aky typ prekazky je na danej strane ktora prave prechadza bodom z=0 (kedze sa hrac nepohybuje)
     */
    public MapBlock.ObstacleType getObstacleType(Main.Side side){
        for (int i = 0; i < 2; i++) {
            if(Math.abs(mapBlocks.get(i).getModel().getTranslateZ()) < 150){
                return mapBlocks.get(i).getObstacleType(side);
            }
        }
        return null;
    }

    /**
     *  skupina objektov reprezentujuca mapu.
     * @return vrati skupinu objektov ktora reprezentuje celu mapu
     */
    public Group getMapGroup(){
        return mapGroup;
    }

    /**
     * update pre pohyb a naciatanie novych blokov mapy
     */
    public void update(){
        mapGroup.getChildren().clear();
        for (int i = 0; i < mapBlocks.size(); i++) {
            mapBlocks.get(i).setModelZPosition(mapBlocks.get(i).getModel().getTranslateZ()-500*(1.0/ Main.fps));
            mapGroup.getChildren().add(mapBlocks.get(i).getModel());
            mapGroup.setTranslateZ(0);
        }

        if(mapBlocks.get(0).getModel().getTranslateZ() < -300){
            addNewMapBlock();
        }
    }

    /**
     *  Zoberie mincu na danej strane ak sa tam nachadza.
     * @param side Pre ktoru stranu.
     * @return vrati boolean ci na danej strane sa nachadza minca ak ano tak ju zobere a returne true. inak false
     */
    public boolean getCoin(Main.Side side){
        for (int i = 0; i < 4; i++) {
            if(mapBlocks.get(i).getCoinAt(side)){
                return true;
            }
        }
        return false;
    }


    private MapBlock getRandomBlock(){
        //(int)(Math.random()*10)
        switch ((int)(Math.random()*7)){
            case(0):
                return new EmptyMapBlock();
            case(1):
                return new HoleMapBlock();
            case(2):
                return new ObstacleMapBlock();
            case(3):
                return new FullBarricadeMapBlock();
            case(4):
                return new SlideUnderMapBlock();
            case(5):
                return new HoleMapBlock2();
        }
        return new EmptyMapBlock();
    }

    private void addNewMapBlock(){
        MapBlock mapBlock = getRandomBlock();
        mapBlock.setModelZPosition(mapBlocks.get(mapBlocks.size()-1).getModel().getTranslateZ()+300);
        mapBlocks.add(mapBlock);
        mapBlocks.remove(0);

    }
}
