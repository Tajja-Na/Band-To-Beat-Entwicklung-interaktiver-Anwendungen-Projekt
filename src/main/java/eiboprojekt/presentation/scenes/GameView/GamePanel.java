package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class GamePanel extends BorderPane{
    private App app;
    private final Canvas canvas;

    // Screen settings
    final int originalTileSize = 64; // 64x64 tile
    final int scale = 1; // damit man es halt passend skalieren kann
    
    public final int tileSize = originalTileSize * scale; // hier 128x128
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    
    public final int screenWidth = tileSize * maxScreenCol; // Fensterbreite in Pixel
    public final int screenHeight = tileSize * maxScreenRow; // Fensterhöhe in Pixel
    
    public final int MAX_WELT_COL = 64; // 16*4
    public final int MAX_WELT_ROW = 48; // 12*4 oder *5
    public final int WORLS_WIDTH = tileSize * MAX_WELT_COL;
    public final int WORLS_HEIGHT = tileSize * MAX_WELT_ROW;

    public GamePanel(){
        // Erstelle das Canvas und füge es zum Panel hinzu
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}