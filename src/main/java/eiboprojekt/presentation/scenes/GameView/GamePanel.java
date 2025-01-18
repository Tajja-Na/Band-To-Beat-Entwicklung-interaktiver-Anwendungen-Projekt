
package eiboprojekt.presentation.scenes.GameView;

import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Comparator;

import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.CollisionCheck;
import eiboprojekt.presentation.scenes.Entity.Entity;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.Member;
import eiboprojekt.presentation.scenes.Entity.TextBubble;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import eiboprojekt.presentation.scenes.Sounds.Sound;
import eiboprojekt.presentation.scenes.Object.Gitarre;
import eiboprojekt.presentation.scenes.Object.Keyboard;
import eiboprojekt.presentation.scenes.Object.Mikrofon;
import eiboprojekt.presentation.scenes.Object.Object;
import eiboprojekt.presentation.scenes.Object.Schlagzeug;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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

    public Canvas getCanvas() {
        return canvas;
    }

    // KeyHandler -> Tasteneingaben
    public final KeyHandlern keyHandler;

    // Player
    public final MainCharacter player;

    // Objects
    public Object obj[] = new Object[10];
    public AssetSetter aSetter = new AssetSetter(this);

    // weil wir brauchen mehrere members
    public Entity members[] = new Entity[3];

    // Dialog
    private DialogPage dialogPage; // Referenz für DialogPage

    private TextBubble warnung = new TextBubble("Achtung der Spielstand wird nicht gespeichert!", 275, 50);

    private boolean showWarning = false; // Zeigt an, ob eine Warnung sichtbar ist
    private String warningText = ""; // Text der Warnung

    // Checker für die Collusion
    public CollisionCheck cChecker;
    public CollisionCheck oChecker;

    // Sound
    Sound sound = new Sound();

    // AnimationTimer für die Spielschleife
    AnimationTimer gameLoop;
    // AnimationTimer aktualisiert das Spiel in einem nahezu konstanten Intervall,
    // was einem typischen Wert von 60 FPS entspricht -> also mam braucht kein
    // Thread oder sleep einbauensss
    // Spielerposition und Geschwindigkeit

    // Im Konstruktor der GamePanel-Klasse
    public GamePanel(App app) {
        this.app = app;

        // Erstelle das Canvas und füge es zum Panel hinzu
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);

        // Initialisiere den KeyHandler
        keyHandler = new KeyHandlern();
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);

        // Setze den Fokus für Tasteneingaben
        this.setFocusTraversable(true);
        this.requestFocus();

        feldM = new FeldManager(this);
        player = new MainCharacter(this, keyHandler);

        // DialogPage initialisieren und hinzufügen
        dialogPage = new DialogPage(700, 250, this, "default", "default");
        dialogPage.setVisible(false); // Dialog standardmäßig unsichtbar
        this.getChildren().add(dialogPage); // Dialog zur GamePanel-Oberfläche hinzufügen

        // Member erstellen und in der Welt platzieren
        members[0] = new Member(this, "assets/Character/Gigi/", "Gigi", "Gitarre");
        members[0].setPosition(tileSize * 30, tileSize * 27);

        members[1] = new Member(this, "assets/Character/Ryu/", "Ryu", "Drum");
        members[1].setPosition(tileSize * 45, tileSize * 4);

        members[2] = new Member(this, "assets/Character/Tyler/", "Tyler", "Keyboard");
        members[2].setPosition(tileSize * 33, tileSize * 41);

        // Objekte platzieren
        obj[0] = new Schlagzeug();
        obj[0].setPosition(4 * tileSize, 4 * tileSize);

        obj[1] = new Keyboard();
        obj[1].setPosition(61 * tileSize, 11 * tileSize);

        obj[2] = new Mikrofon();
        obj[2].setPosition(13 * tileSize, 28 * tileSize);

        obj[3] = new Gitarre();
        obj[3].setPosition(21 * tileSize, 12 * tileSize);

        // Collusion
        cChecker = new CollisionCheck(this);
        oChecker = new CollisionCheck(this);

        // Musik starten
        playMusic(0);

    }

    public FeldManager getFM() {
        return feldM;
    }

    // Füge eine Variable hinzu, um den Zustand des Timers zu verfolgen
    private boolean gameThreadRunning = false;

    public void startGameThread(GraphicsContext gc) {
        if (gameThreadRunning) {
            return; // Verhindere, dass der Game-Thread mehrfach gestartet wird
        }
        gameThreadRunning = true;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!app.isImLevel()) {
                    update();
                    draw(gc);
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

    private void draw(GraphicsContext gc) {
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

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        feldM.ladeKarte("assets/Karte/bb.txt");
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

        // Objekte zeichnen
        // obj[0].draw(gc,tileSize, this);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(gc, tileSize, this);
            }
        }

        // Warnung anzeigen
        if (showWarning) {
            // Hintergrund der Warnung
            double warningBoxWidth = 400; // Breite des Warnungsrechtecks
            double warningBoxHeight = 50; // Höhe des Warnungsrechtecks
            double warningBoxX = (screenWidth - warningBoxWidth) / 2; // Berechnet X für die Mitte
            double warningBoxY = 20; // Abstand vom oberen Rand

            // Textposition
            double textX = warningBoxX + 50; // Kleiner Rand für den Text
            double textY = warningBoxY + 30; // Text etwas unterhalb der Boxhöhe

            gc.setFill(Color.WHITE);
            gc.fillRoundRect(warningBoxX, warningBoxY, warningBoxWidth, warningBoxHeight, 10, 10); // Hintergrund von
                                                                                                   // Warnung
            gc.setFill(Color.BLACK);
            gc.fillText(warningText, textX, textY); // Zeichnet den Warnungstext
        }

    }

    // Musik Methoden
    public void playMusic(int i) {

        sound.loadTrack(i);
        sound.play();
        sound.loop();
        sound.setVolume(0.1); // nicht so laut
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) { // SE = Sound Effect! YEY!

        sound.loadTrack(i);
        sound.play();
    }

    public void handleKeyPressed(KeyEvent event) {
        // Übergibt die Tasteneingabe an den KeyHandler, um generelle Tastenaktionen zu
        // verarbeiten
        keyHandler.keyPressed(event);

        // Wenn die Taste "E" gedrückt wurde
        if (event.getCode() == KeyCode.E) {
            // Iteriert über alle Entities die "members" darstellen
            for (Entity member : members) {
                // Prüft, ob das aktuelle Entity ein Member-Objekt ist
                if (member instanceof Member) {
                    Member m = (Member) member; // Castet Entity zu Member

                    // Überprüft, ob der Spieler in der Nähe des Members ist
                    if (m.isNear(player, tileSize)) {
                        // Member schaut den Spieler an
                        m.facePlayer(player);

                        // Prüft, ob der Spieler das benötigte Instrument besitzt
                        boolean canInteract = false; // Standardmäßig kann keine Interaktion erfolgen
                        switch (m.getInstrument()) { // Prüft, welches Instrument der Member benötigt
                            case "Gitarre":
                                canInteract = player.hasGitarre; // Hat der Spieler die Gitarre?
                                break;
                            case "Drum":
                                canInteract = player.hasDrums; // Hat der Spieler das Schlagzeug?
                                break;
                            case "Keyboard":
                                canInteract = player.hasKeyboard; // Hat der Spieler das Keyboard?
                                break;
                            default:
                                break; // Für unbekannte Instrumente wird keine Interaktion erlaubt
                        }

                        if (canInteract) {
                            // Der Spieler besitzt das benötigte Instrument -> Dialog starten
                            dialogPage.setCurrentPartner(m.getName()); // Setzt den aktuellen Dialogpartner
                            dialogPage.show(); // Zeigt die Dialogseite an
                            showWarning = false; // Blendet mögliche Warnungen aus
                        } else {
                            // Der Spieler besitzt das benötigte Instrument NICHT -> Warnung anzeigen
                            showWarning = true;
                            warningText = "Du benötigst das Instrument " + m.getInstrument() + " für diese Person!";
                        }

                        break; // Nur ein Dialog oder eine Warnung zur selben Zeit behandeln
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