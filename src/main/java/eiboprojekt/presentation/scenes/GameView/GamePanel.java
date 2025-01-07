
package eiboprojekt.presentation.scenes.GameView;

import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Comparator;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.CollisionCheck;
import eiboprojekt.presentation.scenes.Entity.Entity;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.Member;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GamePanel extends BorderPane {
    // zur referenz dass es mit dem switch geht
    private App app;
    public FeldManager feldM;

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

    // Canvas für das Zeichnen
    private final Canvas canvas;

    // KeyHandler -> Tasteneingaben
    public final KeyHandlern keyHandler;

    // Player
    public final MainCharacter player;

    // weil wir brauchen mehrere members
    public Entity members[] = new Entity[3];

    // Dialog
    private DialogPage dialogPage; // Referenz für DialogPage

    // Checker für die Collusion
    public CollisionCheck cChecker;

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
        // canvas.setOnMouseClicked(this::handleMouseClick);

        // Setze den Fokus für Tasteneingaben
        this.setFocusTraversable(true);
        this.requestFocus(); // Fokus setzen

        feldM = new FeldManager(this);

        // Beispiel aus GamePanel oder einer anderen Rendering-Schleife:
        player = new MainCharacter(this, keyHandler);

        // Member erstellen und in der Welt platzieren
        /// Member erstellen und in der Welt platzieren
        members[0] = new Member(this, "assets/Character/Charakter2/");
        members[0].setPosition(tileSize * 30, tileSize * 27);

        members[1] = new Member(this, "assets/Character/Charakter3/");
        members[1].setPosition(tileSize * 45, tileSize * 4);

        members[2] = new Member(this, "assets/Character/Charakter4/");
        members[2].setPosition(tileSize * 33, tileSize * 41);

        // Hintergrundfarbe für das Panel
        // this.setStyle("-fx-background-color: black;");

        // canvas.setOnMouseClicked(event -> handleMouseClick(event));

        // Collusion
        cChecker = new CollisionCheck(this);
    }

    public FeldManager getFM() {
        return feldM;
    }

    // Füge eine Variable hinzu, um den Zustand des Timers zu verfolgen
    private boolean gameThreadRunning = false;

    public void startGameThread() {
        if (gameThreadRunning) {
            return; // Verhindere, dass der Game-Thread mehrfach gestartet wird
        }
        gameThreadRunning = true;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!app.isImLevel()) {
                    update();
                    draw();
                } else {
                    stop();
                }
            }
        };
        gameLoop.start();
    }

    public void stopGameThread() {
        if (gameLoop != null) {
            gameLoop.stop();
            gameThreadRunning = false;
        }
    }

    public void update() {
        // also hier wird das bild vom player geholt
        player.update();
        // hier wird immer geupdated -> collision von member und player
        cChecker.checkPlayerMemberCollision(player, members);
    }

    private void draw() {
        // Hole das GraphicsContext des Canvas, um darauf zu zeichnen
        // GraphicsContext gc = canvas.getGraphicsContext2D();

        // Bildschirm löschen
        // gc.setFill(Color.BLACK);
        // gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // feldM.draw(gc);

        // Spieler zeichnen und tileSize übergeben
        /// player.draw(gc, tileSize);

        // da alle gezeichnet werden erstekken wir ein array dafür
        // for (Entity member : members) {
        // if (member != null) {
        // member.draw(gc, tileSize);
        // }
        // }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        feldM.draw(gc);

        // Erstelle ein Array aller zu zeichnenden Entities
        Entity[] entitiesToDraw = new Entity[members.length + 1];
        entitiesToDraw[0] = player;
        System.arraycopy(members, 0, entitiesToDraw, 1, members.length);

        // Sortiere das Array basierend auf der Y-Position
        Arrays.sort(entitiesToDraw, Comparator.comparingInt(e -> e != null ? e.weltY : Integer.MAX_VALUE));

        // Zeichne alle Entities in der sortierten Reihenfolge
        for (Entity entity : entitiesToDraw) {
            if (entity != null) {
                entity.draw(gc, tileSize);
            }
        }
    }

    // diese methode ist da für die keyanwendungen also wenn man was drücken sollte
    public void handleKeyPressed(KeyEvent event) {
        keyHandler.keyPressed(event);

        // Wenn "E" gedrückt wird, prüfe die Nähe zu einem Member
        if (event.getCode().toString().equals("E")) {
            for (Entity member : members) {
                if (member instanceof Member) {
                    Member m = (Member) member;

                    // Verwende die isNear-Methode des Members
                    if (m.isNear(player, tileSize)) { // Der Schwellenwert kann angepasst werden
                        m.facePlayer(player); // Member dreht sich zum Spieler
                        app.switchView("DIALOG"); // Wechsel zur Dialog-Seite
                        break; // Nur ein Dialog zur Zeit
                    }
                }
            }
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        System.out.println("Key Released: " + event.getCode()); // Testausgabe
        keyHandler.keyReleased(event);
    }

}
