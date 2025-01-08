package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.MainCharacterLevel;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameLevel extends BorderPane {
    private int MAX_LEVEL_COL = 32; //16*2
    private int MAX_LEVEL_ROW = 12; //12*1
    private App app;
    private FeldManager fm;
    private GamePanel gp;
    private KeyHandlern keyHandler;
    private Canvas canvas;
    private final MainCharacterLevel player;  

    public Canvas getCanvas() {
        return canvas;
    }

    public GameLevel(int width, int height, App app, GamePanel gp) {
        

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        this.app = app;
        this.gp = gp;
        this.fm = gp.getFM();

        player = new MainCharacterLevel(gp, keyHandler, this);

        this.keyHandler = gp.keyHandler;

        this.canvas = new Canvas(gp.tileSize * 16, gp.tileSize * 12);
        drawLevel();
    }

    //update methode fehlt

    private void drawLevel(){
        VBox centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);
        System.out.println("hi ich bin im gamelevel drawlevel die karte");
        
        fm.ladeKarte("assets/Karte/levelbase1.txt", MAX_LEVEL_COL, MAX_LEVEL_ROW);
        fm.drawLevel(canvas.getGraphicsContext2D(), MAX_LEVEL_COL, MAX_LEVEL_ROW); //hier muss halt schon fm.draw() hin aber da muss irgendwie noch davor passieren das die passende karte geladen wird

        player.draw(canvas.getGraphicsContext2D(), gp.tileSize);

        centerBox.getChildren().addAll(canvas);
        setCenter(centerBox);
    }
}