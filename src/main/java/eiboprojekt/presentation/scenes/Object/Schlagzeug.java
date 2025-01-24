package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Schlagzeug extends Objekt {
        public Schlagzeug() {
        name = "Schlagzeug";
        try {
            image = new Image(new File("assets/Objects/drum.png").toURI().toString());            
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
