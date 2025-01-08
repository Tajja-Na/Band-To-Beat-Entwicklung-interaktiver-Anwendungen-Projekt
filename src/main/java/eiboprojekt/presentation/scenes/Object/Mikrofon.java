package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Mikrofon extends Object {

    public Mikrofon() {

        name = "Mikrofon";
        try {

            image = new Image(new File("src/main/java/eiboprojekt/presentation/scenes/Object/assets/micro.png").toURI().toString());            

        } catch (Exception e) {

            e.printStackTrace();
        }
        collision = true;
    }
    
}
