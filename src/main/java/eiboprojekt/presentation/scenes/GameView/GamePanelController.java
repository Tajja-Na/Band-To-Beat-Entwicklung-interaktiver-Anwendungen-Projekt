
package eiboprojekt.presentation.scenes.GameView;

import java.util.Arrays;
import java.util.Comparator;
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
import eiboprojekt.presentation.scenes.Object.Objekt;
import eiboprojekt.presentation.scenes.Object.Schlagzeug;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GamePanelController {
    private App app;
    public FeldManager feldM;
    private GamePanel gp;
    public final KeyHandlern keyHandler;
    private boolean gameThreadRunning;

    // AnimationTimer aktualisiert das Spiel in einem nahezu konstanten Intervall,
    // was einem typischen Wert von 60 FPS entspricht -> also mam braucht kein
    // Thread oder sleep einbauensss
    // Spielerposition und Geschwindigkeit
    public AnimationTimer gameLoop;

    // Player
    public final MainCharacter player;
    public Entity members[] = new Entity[3];

    // Objects
    public Objekt obj[] = new Objekt[10];
    private boolean isSchlagzeugPlatziert = false;
    private boolean isKeyboardPlatziert = false;

    // Checker für die Collusion
    public CollisionCheck cChecker;
    public CollisionCheck oChecker;

    // Sound
    Sound sound;

    public GamePanelController(App app) {
        this.app = app;
        this.gp = new GamePanel(app);
        this.sound = app.getSound();
        keyHandler = new KeyHandlern();
        this.gameThreadRunning = false;
        gp.setOnKeyPressed(this::handleKeyPressed);
        gp.setOnKeyReleased(this::handleKeyReleased);

        // Setze den Fokus für Tasteneingaben
        gp.setFocusTraversable(true);
        gp.requestFocus();

        player = new MainCharacter(app, keyHandler);
        feldM = new FeldManager(app, player);

        // Member erstellen und in der Welt platzieren
        members[0] = new Member(app, "assets/Character/Gigi/", "Gigi", "Gitarre");
        members[0].setPosition(app.tileSize * 30, app.tileSize * 27);

        members[1] = new Member(app, "assets/Character/Ryu/", "Ryu", "Drum");
        members[1].setPosition(app.tileSize * 45, app.tileSize * 4);

        members[2] = new Member(app, "assets/Character/Tyler/", "Tyler", "Keyboard");
        members[2].setPosition(app.tileSize * 33, app.tileSize * 41);

        // Objekte platzieren
        obj[2] = new Mikrofon();
        obj[2].setPosition(13 * app.tileSize, 28 * app.tileSize);

        obj[3] = new Gitarre();
        obj[3].setPosition(21 * app.tileSize, 12 * app.tileSize);

        // Collision
        cChecker = new CollisionCheck(app);
        oChecker = new CollisionCheck(app);
    }

    public void startGameThread(GraphicsContext gc) {
        if (gameThreadRunning) {
            return; // Verhindert, dass der Game-Thread mehrfach gestartet wird
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

        player.update();

        // Schlagzeug platzieren, wenn Gigi-Level geschafft
        if (app.isGigiLevelGeschafft() && !isSchlagzeugPlatziert) {
            obj[0] = new Schlagzeug();
            obj[0].setPosition(4 * app.tileSize, 4 * app.tileSize);
            isSchlagzeugPlatziert = true; // Sicherstellen, dass es nur einmal platziert wird
        }

        // Keyboard platzieren, wenn Ryu-Level geschafft
        if (app.isRyuLevelGeschafft() && !isKeyboardPlatziert) {
            obj[1] = new Keyboard();
            obj[1].setPosition(61 * app.tileSize, 11 * app.tileSize);
            isKeyboardPlatziert = true; // Sicherstellen, dass es nur einmal platziert wird
        }

    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gp.getCanvas().getWidth(), gp.getCanvas().getHeight());
        feldM.ladeKarte("assets/Karte/weltMap.txt");
        feldM.draw(gc);

        // Erstellt ein Array aller zu zeichnenden Entities
        Entity[] entitiesToDraw = new Entity[members.length + 1];
        entitiesToDraw[0] = player;
        System.arraycopy(members, 0, entitiesToDraw, 1, members.length);

        // Sortiert das Array basierend auf der Y-Position
        Arrays.sort(entitiesToDraw, Comparator.comparingInt(e -> e != null ? e.weltY : Integer.MAX_VALUE));

        // Zeichnet alle Entities in der sortierten Reihenfolge
        for (Entity entity : entitiesToDraw) {
            if (entity != null) {
                entity.draw(gc, app.tileSize);
            }
        }

        // Objekte zeichnen
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(gc, app, player);
            }
        }

        // TextBubble anzeigen, wenn showTextBubble aktiv ist
        if (gp.isShowTextBubble()) {
            gp.setShowWarning(false);
            gp.getInstrumentWarnung().draw(gc, 500, 100);
        }

        if (gp.isShowWarning()) {
            gp.getWarnung().draw(gc, 200, 100);
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        // Übergibt die Tasteneingabe an den KeyHandler, um generelle Tastenaktionen zu
        // verarbeiten

        // Entfernt die TextBubble, wenn eine WASD-Taste gedrückt wird
        if (gp.isShowWarning() && (event.getCode() == KeyCode.W || event.getCode() == KeyCode.A
                || event.getCode() == KeyCode.S || event.getCode() == KeyCode.D)) {
            gp.setShowWarning(false);
        }

        keyHandler.keyPressed(event);

        // Wenn die Taste "E" gedrückt wurde
        if (event.getCode() == KeyCode.E) {
            for (Entity member : members) {
                // Prüft, ob das aktuelle Entity ein Member-Objekt ist
                if (member instanceof Member) {
                    Member m = (Member) member;
                    if (m.isNear(player, app.tileSize)) {
                        // Prüft, ob der Spieler das benötigte Instrument besitzt
                        boolean canInteract = false; // Standardmäßig kann keine Interaktion erfolgen
                        switch (m.getInstrument()) {
                            case "Gitarre":
                                canInteract = player.hasGitarre;
                                break;
                            case "Drum":
                                canInteract = player.hasDrums;
                                break;
                            case "Keyboard":
                                canInteract = player.hasKeyboard;
                                break;
                            default:
                                break;
                        }

                        if (canInteract) {
                            // Der Spieler besitzt das benötigte Instrument -> Dialog starten
                            // DialogPage initialisieren und hinzufügen
                            gp.setDpController(new DialogPageController(app, m.getName()));
                            gp.getChildren().add(gp.getDpController().getDp());
                            gp.getDpController().setCurrentPartner(m.getName()); // Setzt den aktuellen Dialogpartner
                            gp.getDpController().getDp().show();
                            gp.setShowTextBubble(false);
                        } else {
                            // Der Spieler besitzt das benötigte Instrument NICHT -> Warnung anzeigen
                            gp.setShowTextBubble(true);
                            gp.setInstrumentWarnung(new TextBubble(
                                    "Du benötigst das Instrument " + m.getInstrument() + " für diese Person!", 350,
                                    50));
                        }

                        break; // Nur ein Dialog oder eine Warnung zur selben Zeit behandeln
                    }
                }
            }
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        keyHandler.keyReleased(event);
    }

    public GamePanel getGp() {
        return this.gp;
    }

    public FeldManager getFM() {
        return feldM;
    }
}