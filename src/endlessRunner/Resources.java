package endlessRunner;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.HashMap;

public class Resources {

    public static Resources instance = null;

    public HashMap<String, Material> materials = new HashMap<>();
    public HashMap<String, ObjModelImporter> models = new HashMap<>();

    ObjModelImporter modelImporter = new ObjModelImporter();

    public static Resources getInstance(){
        if (instance == null)
            instance = new Resources();

        return instance;
    }


    private Resources(){

    }


    /**
     * Nacita materialy a nacitane materialy si uchovava ak by boli pouzite znova.
     * @param filePath Cesta obrazku pre material
     * @return vrati material s nacitanim obrazkom.
     */
    public Material getMaterial(String filePath){
        Material material = materials.get(filePath);
        if(material != null){
            return material;
        }

        PhongMaterial phongMaterial = new PhongMaterial();
        try {
            phongMaterial.setDiffuseMap(new Image(new FileInputStream(filePath)));
        } catch (FileNotFoundException e) {
            System.out.println("file: \"" + filePath + "\" does not exist");
            return null;
        }


        materials.put(filePath, phongMaterial);
        return phongMaterial;

    }

    /**
     *
     * @param path cesta k modelu
     * @return vrati nacitany model
     */
    public Group getModel(String path) {
        modelImporter.read(path);
        models.put(path, modelImporter);
        return new Group(modelImporter.getImport());
    }


}
