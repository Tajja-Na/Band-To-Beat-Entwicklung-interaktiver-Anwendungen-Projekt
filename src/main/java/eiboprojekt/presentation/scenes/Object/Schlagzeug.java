package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Schlagzeug extends Object {

        public Schlagzeug() {

        name = "Schlagzeug";
        try {

            image = new Image(new File("src/main/java/eiboprojekt/presentation/scenes/Object/assets/drum.png").toURI().toString());            

        } catch (Exception e) {

            e.printStackTrace();
        }
        collision = true;
    }

}
