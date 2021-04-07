package mapBloks;

import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import endlessRunner.*;

public class EmptyMapBlock extends MapBlock {

    public EmptyMapBlock(){

    }

    @Override
    void generateBlock() {
        Box a = new Box(300,100,300);

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

        a.setMaterial(Resources.getInstance().getMaterial("src/tex/Ground_0" +((int)(Math.random()*4)+1) +".jpg"));

        d.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));
        e.setMaterial(Resources.getInstance().getMaterial("src/tex/convey1.jpg"));

        getModel().getChildren().addAll(d,e);
    }

}
