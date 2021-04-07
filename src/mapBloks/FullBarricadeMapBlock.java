package mapBloks;


import endlessRunner.*;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;

public class FullBarricadeMapBlock extends MapBlock{
    @Override
    void generateBlock() {
        Box a = new Box(100,100,100);
        Box b = new Box(100,100,100);
        b.setTranslateY(-100);


        Box d = new Box(100,100,300);
        Box e = new Box(100,100,300);

        d.setTranslateX(-200);
        e.setTranslateX(200);
        d.setTranslateZ(0);

        Material material = Resources.getInstance().getMaterial("src/tex/Ground_0" +((int)(Math.random()*4)+1) +".jpg");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Box box = new Box(100,100,100);
                box.setTranslateY(50);
                box.setTranslateX((i*100) -100);
                box.setTranslateZ((j*100) -100);
                box.setMaterial(material);
                getModel().getChildren().addAll(box);
            }
        }

        a.setMaterial(Resources.getInstance().getMaterial("src/tex/brick2.jpg"));
        b.setMaterial(Resources.getInstance().getMaterial("src/tex/brick2.jpg"));

        d.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));
        e.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));

        getModel().getChildren().addAll(d,e, a,b);

        switch ((int)(Math.random()*3)){
            case (0):
                setObstacle(Main.Side.LEFT, ObstacleType.FULLBARICADE,100,200);
                a.setTranslateX(-100);
                b.setTranslateX(-100);
                break;
            case(1):
                setObstacle(Main.Side.MIDDLE, ObstacleType.FULLBARICADE,100,200);
                a.setTranslateX(0);
                b.setTranslateX(0);
                break;
            case(2):
                setObstacle(Main.Side.RIGHT, ObstacleType.FULLBARICADE,100,200);
                a.setTranslateX(100);
                b.setTranslateX(100);
                break;
        }


    }
}
