package eiboprojekt.presentation.scenes.GameView;

import java.io.InterruptedIOException;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.Member1;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GamePanel extends BorderPane {
    // zur referenz dass es mit dem switch geht
    private App app;

    // Screen settings
    final int originalTileSize = 64; // 64x64 tile
    final int scale = 1; // damit man es halt passend skalieren kann

    public final int tileSize = originalTileSize * scale; // hier 128x128
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // Fensterbreite in Pixel
    public final int screenHeight = tileSize * maxScreenRow; // Fensterhöhe in Pixel

    // Canvas für das Zeichnen
    private final Canvas canvas;

    // KeyHandler -> Tasteneingaben
    public final KeyHandlern keyHandler;

    // Player
    private final MainCharacter player;

    private final Member1 member1;

    // Dialog
    private DialogPage dialogPage; // Referenz für DialogPage

    // AnimationTimer für die Spielschleife
    AnimationTimer gameLoop;
    // AnimationTimer aktualisiert das Spiel in einem nahezu konstanten Intervall,
    // was einem typischen Wert von 60 FPS entspricht -> also mam braucht kein
    // Thread oder sleep einbauensss
    // Spielerposition und Geschwindigkeit

    public GamePanel(App app) {
        this.app = app;
        // Erstelle das Canvas und füge es zum Panel hinzu
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);

        // Initialisiere den KeyHandler
        keyHandler = new KeyHandlern();
        // Setze die Tasteneingaben (auf Tastendruck und Tastenauslösung hören)
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);

        // MouseEvent handler
        canvas.setOnMouseClicked(this::handleMouseClick);

        // Setze den Fokus für Tasteneingaben
        this.setFocusTraversable(true);
        this.requestFocus(); // Fokus setzen

        // Beispiel aus GamePanel oder einer anderen Rendering-Schleife:
        player = new MainCharacter(this, keyHandler);

        member1 = new Member1(300, 300); // Beispielposition für den NPC

        // Hintergrundfarbe für das Panel
        this.setStyle("-fx-background-color: black;");

        canvas.setOnMouseClicked(event -> handleMouseClick(event));

    }

    public void startGameThread() {
        // Erstelle einen AnimationTimer, der jedes Frame aufgerufen wird
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                if(!app.isImLevel()){
                    // 1. Update: Update information like character position
                    update();

                    // 2. Draw: Draw the screen with updated information
                    draw();
                } else{
                    stop();
                }
            }
        };
        gameLoop.start();
    }

    public void update() {
        // also hier wird das bild vom player geholt
        player.update();
        // member1.update(); bewegt sich fürs erste nicht
    }

    private void draw() {
        // Hole das GraphicsContext des Canvas, um darauf zu zeichnen
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Bildschirm löschen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Spieler zeichnen und tileSize übergeben
        player.draw(gc, tileSize);

        member1.draw(gc);
    }

    // diese methode ist da für die keyanwendungen also wenn man was drücken sollte
    public void handleKeyPressed(KeyEvent event) {
        keyHandler.keyPressed(event);

        if (event.getCode().toString().equals("E") && player.isNear(member1)) {

            // vllt hier einfach auch so ein getButton und dann im app mit setAction
            // aufrufen
            app.switchView("DIALOG1"); // Wechsel zur DialogPage
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        System.out.println("Key Released: " + event.getCode()); // Testausgabe
        keyHandler.keyReleased(event);
    }

    // geht nnoch nicht dass man mit der maus auf die figur klickt
    public void handleMouseClick(javafx.scene.input.MouseEvent event) {
        // Überprüfe, ob der Mausklick in der Nähe von Member1 ist
        if (isNearMember1(event.getX(), event.getY())) {
            app.switchView("DIALOG1"); // Wechsel zur DialogPage
        }
    }

    private boolean isNearMember1(double mouseX, double mouseY) {
        // Definiere einen Bereich um Member1, in dem Klicks erkannt werden
        double distance = 20; // Anpassbar je nach Bedarf
        return Math.abs(mouseX - member1.getX()) < distance &&
                Math.abs(mouseY - member1.getY()) < distance;
    }

}
