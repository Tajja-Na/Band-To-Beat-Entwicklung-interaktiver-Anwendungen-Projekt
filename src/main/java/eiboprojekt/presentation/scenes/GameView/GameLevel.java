package eiboprojekt.presentation.scenes.GameView;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

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
    
    Text winHLGigi;
    Text winTextGigi;
    private VBox winScreenGigi;
    Button backToMapButtonWinGigi;

    Text winHLRyu;
    Text winTextRyu;
    Button backToMapButtonRyu;
    private VBox winScreenRyu;
    Button backToMapButtonWinRyu;

    VBox gameOverScreen;
    Text gameOverText;
    Button retryButton;
    Button backToMapButton;
    HBox buttons;
    VBox centerBox;

    public boolean running;

    public GameLevel() {
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);

        //Style für die Buttons später:
        getStylesheets().add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());

        //GAME OVER SCREEN:
        gameOverScreen = new VBox(40);
        gameOverScreen.setAlignment(Pos.CENTER);
        gameOverScreen.setPrefSize(screenWidth, screenHeight);

        gameOverScreen.setStyle("-fx-background-image: url(\"file:assets/background/background-test.jpg\");" + "-fx-background-size: cover;");

        gameOverText = new Text("Oh no, Game Over!");
        gameOverText.setFont(new Font(80));
        gameOverText.setFont(Font.font("System", FontWeight.BOLD, 100));
        gameOverText.setFill(Color.BLACK);

        retryButton = new Button("Versuch's nochmal");
        retryButton.getStyleClass().add("button");

        backToMapButton = new Button("Zurück zur Map");
        backToMapButton.getStyleClass().add("button");

        buttons = new HBox(16);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(retryButton, backToMapButton);

        gameOverScreen.getChildren().addAll(gameOverText, buttons);

        //WIN SCREEN Gigi:
        winScreenGigi = new VBox(40);
        winScreenGigi.setAlignment(Pos.CENTER);
        winScreenGigi.setPrefSize(screenWidth, screenHeight);

        winScreenGigi.setStyle("-fx-background-image: url(\"file:assets/background/background-test.jpg\");" + "-fx-background-size: cover;");

        winHLGigi = new Text("Congratulations! \nYou wooooon!");
        winHLGigi.setFont(new Font(80));
        winHLGigi.setFill(Color.BLUEVIOLET);
        winHLGigi.setTextAlignment(TextAlignment.CENTER);

        backToMapButtonWinGigi = new Button("Zurück zur Map");
        backToMapButtonWinGigi.getStyleClass().add("button");

        winTextGigi = new Text("Du hast Gigi davon überzeugt Teil deiner Band zu sein! Amazing! Und jetzt geht's direkt weiter, du brauchst noch einen Drummer.");
        winTextGigi.setFont(new Font(20));
        winTextGigi.setFill(Color.BLACK);
        TextFlow textFlowGigi = new TextFlow(winTextGigi);
        textFlowGigi.setPrefWidth(screenWidth - 160);
        textFlowGigi.setPadding(new Insets(0, 80, 0, 80));
        textFlowGigi.setTextAlignment(TextAlignment.CENTER);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        VBox.setVgrow(spacer2, Priority.ALWAYS);

        HBox imageBoxGigi = new HBox(10);
        imageBoxGigi.setAlignment(Pos.BOTTOM_CENTER);
        imageBoxGigi.setPadding(new Insets(0, 0, 64, 0));

        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(new Image(new File("assets/LevelWin/gigiLevel" + i + ".png").toURI().toString()));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(100);

            imageBoxGigi.getChildren().add(imageView);
        }

        winScreenGigi.getChildren().addAll(spacer1, winHLGigi, textFlowGigi, backToMapButtonWinGigi, spacer2, imageBoxGigi);

        //WIN SCREEN Ryu:
        winScreenRyu = new VBox(40);
        winScreenRyu.setAlignment(Pos.CENTER);
        winScreenRyu.setPrefSize(screenWidth, screenHeight);

        winScreenRyu.setStyle("-fx-background-image: url(\"file:assets/background/background-test.jpg\");" + "-fx-background-size: cover;");

        winHLRyu = new Text("Congratulations! \nYou won again!");
        winHLRyu.setFont(new Font(80));
        winHLRyu.setFill(Color.BLUEVIOLET);
        winHLRyu.setTextAlignment(TextAlignment.CENTER);

        backToMapButtonWinRyu = new Button("Zurück zur Map");
        backToMapButtonWinRyu.getStyleClass().add("button");

        winTextRyu = new Text("Jetzt hast du auch Ryu davon überzeugt teil deiner Band zu werden! Wow, du bist so nah dran deinen Traum zu verwirklichen. Dir fehlt noch ein Star-Band-Mitgllied und ich glaube du hast schon eine gute Idee wer das sein könnte, nicht wahr?");
        winTextRyu.setFont(new Font(20));
        winTextRyu.setFill(Color.BLACK);
        TextFlow textFlowRyu = new TextFlow(winTextRyu);
        textFlowRyu.setPrefWidth(screenWidth - 160);
        textFlowRyu.setPadding(new Insets(0, 80, 0, 80));
        textFlowRyu.setTextAlignment(TextAlignment.CENTER);

        Region spacer3 = new Region();
        Region spacer4 = new Region();
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        VBox.setVgrow(spacer4, Priority.ALWAYS);

        HBox imageBoxRyu = new HBox(10);
        imageBoxRyu.setAlignment(Pos.BOTTOM_CENTER);
        imageBoxRyu.setPadding(new Insets(0, 0, 64, 0));

        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(new Image(new File("assets/LevelWin/ryuLevel" + i + ".png").toURI().toString()));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(100);

            imageBoxRyu.getChildren().add(imageView);
        }

        winScreenRyu.getChildren().addAll(spacer3, winHLRyu, textFlowRyu, backToMapButtonWinRyu, spacer4, imageBoxRyu);



        //DEFAULT IN GAME:
        centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(getCanvas());
        setCenter(centerBox);
    }

    public void setzeCanvasLose(){
        this.setCenter(gameOverScreen);
    }

    public void setzeCanvasWinGigi(){
        this.setCenter(winScreenGigi);
    }

    public void setzeCanvasWinRyu(){
        this.setCenter(winScreenRyu);
    }

    public Canvas getCanvas() {
        return canvas;
    }

}