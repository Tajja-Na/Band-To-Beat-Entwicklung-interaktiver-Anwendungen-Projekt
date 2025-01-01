package eiboprojekt.presentation.scenes.Level;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Level extends BorderPane{ //noch in Arbeit

    private App app;
    private FeldManager fm;

    public Level(int width, int height, App app, FeldManager fm) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        this.app = app;
        this.fm = fm;
        drawLevel();
    } 

    private void drawLevel() {
        VBox centerBox = new VBox(); 
        centerBox.setAlignment(Pos.CENTER);

        //fm.draw();

        //centerBox.getChildren().addAll(fm.canvas);
        setCenter(centerBox);
    }
}