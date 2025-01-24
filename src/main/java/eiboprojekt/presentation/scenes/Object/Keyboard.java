package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Keyboard extends Objekt {

    public Keyboard() {
        name = "Keyboard";
        try {
            image = new Image(new File("assets/Objects/keyboard.png").toURI().toString());            
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
