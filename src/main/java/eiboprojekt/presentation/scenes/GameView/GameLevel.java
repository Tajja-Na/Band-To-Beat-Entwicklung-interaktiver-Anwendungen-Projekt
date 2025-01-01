package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameLevel extends BorderPane {
    private App app;
    private FeldManager fm;
    private GamePanel gp;
    private KeyHandlern keyHandler;
    //private final MainCharacter player;  < das mit dem player und das er ins level gezeichnet wird kommt noch

    public GameLevel(int width, int height, App app, GamePanel gp) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        this.app = app;
        this.fm = gp.getFM();
        this.gp = gp;
        this.keyHandler = gp.keyHandler;
        drawLevel();
    }

    private void drawLevel() {
        VBox centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);
        //System.out.println("hi ich bin im gamelevel drawlevel die karte");
        //fm.draw(); //hier muss halt schon fm.draw() hin aber da muss irgendwie noch davor passieren das die passende karte geladen wird

        //centerBox.getChildren().addAll(fm.canvas);
        setCenter(centerBox);
    }
}