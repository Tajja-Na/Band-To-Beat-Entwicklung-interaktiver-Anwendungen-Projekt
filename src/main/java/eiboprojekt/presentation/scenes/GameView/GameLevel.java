package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameLevel extends BorderPane {
    // Screen settings
    final int originalTileSize = 64; // 64x64 tile
    final int scale = 1; // damit man es halt passend skalieren kann

    public final int tileSize = originalTileSize * scale; // hier 128x128
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // Fensterbreite in Pixel
    public final int screenHeight = tileSize * maxScreenRow; // Fensterhöhe in Pixel

    public int MAX_LEVEL_COL = 32; // 16*2
    public int MAX_LEVEL_ROW = 12; // 12*1
    public final int WORLS_WIDTH = tileSize * MAX_LEVEL_COL;
    public final int WORLS_HEIGHT = tileSize * MAX_LEVEL_ROW;

    private Canvas canvas;
    Text gameOverText;
    Button retryButton;
    Button backToMapButton;
    VBox gameOverScreen;
    HBox buttons;
    VBox centerBox;

    public GameLevel() {
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);

        //Style für die Buttons später:
        getStylesheets().add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());

        gameOverScreen = new VBox(40);
        gameOverScreen.setAlignment(Pos.CENTER);
        gameOverScreen.setPrefSize(screenWidth, screenHeight);

        gameOverScreen.setStyle("-fx-background-image: url(\"file:assets/background/background-test.jpg\");" + "-fx-background-size: cover;");

        gameOverText = new Text("Oh no, Game Over!");
        gameOverText.setFont(new Font(80));
        gameOverText.setFill(Color.RED);

        retryButton = new Button("Versuch's nochmal");
        retryButton.getStyleClass().add("button");
        backToMapButton = new Button("Zurück zur Map");
        backToMapButton.getStyleClass().add("button");

        buttons = new HBox(16);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(retryButton, backToMapButton);

        gameOverScreen.getChildren().addAll(gameOverText, buttons);

        this.setCenter(gameOverScreen);

        centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(getCanvas());
        setCenter(centerBox);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}