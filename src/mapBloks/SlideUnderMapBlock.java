package mapBloks;

import endlessRunner.*;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;

public class SlideUnderMapBlock extends MapBlock {
    @Override
    void generateBlock() {
        Box barrier1 = new Box(100,70,10);
        Box barrier2 = new Box(100,70,10);
        Box barrier3 = new Box(100,70,10);

        barrier1.setTranslateX(-100);
        barrier2.setTranslateX(0);
        barrier3.setTranslateX(100);

        barrier1.setTranslateY(-80);
        barrier2.setTranslateY(-80);
        barrier3.setTranslateY(-80);

        Box d = new Box(100, 100, 300);
        Box e = new Box(100, 100, 300);

        d.setTranslateX(-200);
        e.setTranslateX(200);
        d.setTranslateZ(0);

        Material material = Resources.getInstance().getMaterial("src/tex/Ground_0" + ((int) (Math.random() * 4) + 1) + ".jpg");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Box box = new Box(100, 100, 100);
                box.setTranslateY(50);
                box.setTranslateX((i * 100) - 100);
                box.setTranslateZ((j * 100) - 100);
                box.setMaterial(material);
                getModel().getChildren().addAll(box);
            }
        }


        barrier1.setMaterial(Resources.getInstance().getMaterial("src/tex/wall1.jpg"));
        barrier2.setMaterial(Resources.getInstance().getMaterial("src/tex/wall1.jpg"));
        barrier3.setMaterial(Resources.getInstance().getMaterial("src/tex/wall1.jpg"));

        d.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));
        e.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));

        getModel().getChildren().addAll(d, e, barrier1, barrier2,barrier3);

        setObstacle(Main.Side.MIDDLE, ObstacleType.SLIDEUNDER, 145, 155);
        setObstacle(Main.Side.LEFT, ObstacleType.SLIDEUNDER, 145, 155);
        setObstacle(Main.Side.RIGHT, ObstacleType.SLIDEUNDER, 145, 155);

    }
}