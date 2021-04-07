package endlessRunner;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Utils {

    Utils(){

    }

    /**
     * Linearna interpolacia medzi dvomi bodmi
     * @param from zaciatocna hodnota
     * @param to koncova hodnota
     * @param f 0 = zaciatocnej hodnote; 1 = koncovej hodnote;
     * @return
     */
    public static double lerp(double from, double to, double f)
    {
        return (from * (1.0 - f)) + (to * f);
    }


    /**
     *
     * @param path Cesta k modelu
     * @return vrati nacitany 3D model
     */
    public static Group loadModel(String path) {
        ObjModelImporter modelImporter = new ObjModelImporter();
        modelImporter.read(path);
        return new Group(modelImporter.getImport());
    }
}
