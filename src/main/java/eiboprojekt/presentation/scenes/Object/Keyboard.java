package eiboprojekt.presentation.scenes.Object;

import java.io.File;

import javafx.scene.image.Image;

public class Keyboard extends Object {

    public Keyboard() {

        name = "Keyboard";
        try {

            image = new Image(new File("src/main/java/eiboprojekt/presentation/scenes/Object/assets/keyboard.png").toURI().toString());            

        } catch (Exception e) {

            e.printStackTrace();
            
        }
        collision = true;
    }
    
}
