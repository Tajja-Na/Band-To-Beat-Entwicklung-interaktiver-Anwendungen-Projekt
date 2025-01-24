package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Gitarre extends Objekt {

    public Gitarre() {
        name = "Gitarre";
        try {
            image = new Image(new File("assets/Objects/gitarre.png").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
