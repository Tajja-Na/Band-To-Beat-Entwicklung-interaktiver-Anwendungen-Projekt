package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Gitarre extends Object {

    public Gitarre() {

        name = "Gitarre";
        try {

            image = new Image(new File("src/main/java/eiboprojekt/presentation/scenes/Object/assets/gitarre.png").toURI().toString());            

        } catch (Exception e) {

            e.printStackTrace();
        }
        collision = true;
    }
    
}
