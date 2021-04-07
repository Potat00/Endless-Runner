package mapBloks;

import endlessRunner.*;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;



public class HoleMapBlock extends MapBlock {
    @Override
    void generateBlock() {
        Box d = new Box(100,100,300);
        Box e = new Box(100,100,300);
        d.setTranslateX(-200);
        e.setTranslateX(200);
        d.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));
        e.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));

        getModel().getChildren().addAll(d,e);

        Material material = Resources.getInstance().getMaterial("src/tex/road2.jpg");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Box box = new Box(100,10,100);
                box.setTranslateY(5);
                box.setTranslateX((i * 100) - 100);
                box.setTranslateZ((j*100) -100);
                box.setMaterial(material);
                if(j!=1){
                    getModel().getChildren().addAll(box);
                }else{

                }

            }
        }
        setObstacle(Main.Side.MIDDLE, ObstacleType.HOLE,100,200);
        setObstacle(Main.Side.LEFT, ObstacleType.HOLE,100,200);
        setObstacle(Main.Side.RIGHT, ObstacleType.HOLE,100,200);
    }
}
