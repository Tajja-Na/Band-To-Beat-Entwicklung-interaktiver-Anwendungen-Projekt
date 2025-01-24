package eiboprojekt.presentation.scenes.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.MainCharacterLevel;
import eiboprojekt.presentation.scenes.Felder.FeldManagerLevel;
import eiboprojekt.presentation.scenes.Sounds.Sound;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameLevelController {
    // Setup
    private App app;
    private GameLevel gl;
    private FeldManagerLevel fm;
    private KeyHandlern keyHandler;
    private boolean youWon;
    private Sound sound;
    private final int LEVEL_LAENGE = 105; // 105 sekunden

    // Objekte und Kollision
    private int collisionCount = 0; // Kollisionen zählen
    private final int maxCollisions = 3;
    private List<Double[]> obstacles = new ArrayList<>();
    private Double lastObstacleX = null;
    private Image obstacleImage;
    private String obstacleName;

    // GameLevel Loop
    public AnimationTimer gameLoop;
    private boolean levelThreadRunning = false;

    // Timer
    private long startTime;
    private final int MAX_TIME = 105;
    private int elapsedTime;

    private final MainCharacterLevel player;

    public GameLevelController(int width, int height, App app, String obstacleName) {
        this.app = app;
        this.gl = new GameLevel();
        this.obstacleName = obstacleName;
        this.fm = new FeldManagerLevel(app);
        startTime = System.currentTimeMillis();
        elapsedTime = 0;

        keyHandler = new KeyHandlern();
        gl.setOnKeyPressed(this::handleKeyPressed);
        gl.setOnKeyReleased(this::handleKeyReleased);

        gl.setFocusTraversable(true);
        gl.requestFocus();

        // Player
        player = new MainCharacterLevel(app, keyHandler);
        gl.running = true;
        youWon = false;

        // Objekte
        obstacles = new ArrayList<>();
        String path = "assets/Objects/";
        obstacleImage = new Image(new File(path + obstacleName).toURI().toString());

        // Sound
        sound = app.getSound();
    }

    public void startLevelThread(GraphicsContext gc) {
        if (levelThreadRunning) {
            return; // Verhindere, dass der Game-Thread mehrfach gestartet wird
        }
        levelThreadRunning = true;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gl.running) {
                    update();
                    draw(gc);
                } else {
                    stop();
                }
            }
        };
        gameLoop.start();
    }

    public void stopLevelThread() {
        if (gameLoop != null) {
            gameLoop.stop();
            levelThreadRunning = false;
        }
    }

    public void update() {

        elapsedTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
        if (elapsedTime > MAX_TIME) {
            elapsedTime = MAX_TIME;
            gl.running = false;
        }

        player.update();

        if (!youWon) {
            // Hindernisse bewegen
            for (Double[] obstacle : obstacles) {
                obstacle[0] -= 5; // Bewegung nach links
            }

            // Aktualisiere die Position des letzten Hindernisses
            if (!obstacles.isEmpty()) {
                lastObstacleX = obstacles.get(obstacles.size() - 1)[0];
            } else {
                lastObstacleX = null;
            }

            // Kollisionserkennung
            checkCollisions();

            // Neue Hindernisse hinzufügen
            if (Math.random() < 0.02) { // Zufälliges Erzeugen
                if (lastObstacleX == null || app.screenWidth - lastObstacleX >= 4 * app.tileSize) {
                    Double[] newObstacle = new Double[] { (double) app.screenWidth,
                            (double) (player.groundY + app.tileSize / 2) };
                    obstacles.add(newObstacle);
                }
            }
        }

        // Listener für den Fall Win
        if (gl.running) {
            sound.getcurrentPosition().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue.intValue() >= 6) {
                            youWon = true;
                            if (gl.running) {
                                switch (obstacleName) {
                                    case "Gigi.png":
                                        app.setGigiLevelGeschafft(true);
                                        gl.setzeCanvasWinGigi();
                                        gl.backToMapButtonWinGigi.setOnAction(e -> {
                                            stopLevelThread();
                                            goToMap();
                                        });
                                        break;
                                    case "Ryu.png":
                                        app.setRyuLevelGeschafft(true);
                                        gl.setzeCanvasWinRyu();
                                        gl.backToMapButtonWinRyu.setOnAction(e -> {
                                            stopLevelThread();
                                            goToMap();
                                        });
                                        break;
                                    case "Tyler.png":

                                        stopLevelThread();
                                        app.switchView("ENDEVIEW");
                                        break;

                                    default:
                                        break;
                                }
                            }
                        }
                    });
        }

    }

    public void draw(GraphicsContext gc) {

        gc.fillRect(0, 0, gl.getCanvas().getWidth(), gl.getCanvas().getHeight());
        drawLevel();
        player.draw(gc, app.tileSize);

        for (Double[] obstacle : obstacles) {
            gc.drawImage(obstacleImage, obstacle[0], obstacle[1], app.tileSize, app.tileSize);
        }

        // Kollisionszähler zeichnen
        gc.setFill(Color.PURPLE);
        gc.fillText("Kollisionen: " + collisionCount + " (bei 3 bist du raus!)", 10, 20);

        // Zeitanzeige zeichnen
        gc.setFill(Color.PURPLE);
        gc.setFont(Font.font("System", FontWeight.BOLD, 20));
        gc.fillText(elapsedTime + "s / " + MAX_TIME + "s", 10, 50);

        // Bei Game Over
        if (!gl.running) {
            gl.setzeCanvasLose();
            stopLevelThread();
            gl.retryButton.setOnAction(e -> {
                restartGame();
            });

            gl.backToMapButton.setOnAction(e -> {
                goToMap();
            });
        }

    }

    private void checkCollisions() {
        for (Iterator<Double[]> iterator = obstacles.iterator(); iterator.hasNext();) {
            Double[] obstacle = iterator.next();
            if (player.getBounds().intersects(obstacle[0], obstacle[1], app.tileSize, app.tileSize)) {
                collisionCount++;
                iterator.remove();
                if (collisionCount >= maxCollisions) {
                    gl.running = false;
                }
            }
        }
    }

    private void restartGame() {
        switch (obstacleName) {
            case "Gigi.png":
                app.switchView("GAMELevel1");
                break;
            case "Ryu.png":
                app.switchView("GAMELevel2");
                break;
            case "Tyler.png":
                app.switchView("GAMELevel3");
                break;
        }

    }

    private void goToMap() {
        app.switchView("GAMEPANEL");
    }

    private void drawLevel() {
        fm.ladeKarte("assets/Karte/levelbase1.txt", gl.MAX_LEVEL_COL, gl.MAX_LEVEL_ROW);
        fm.drawLevel(gl.getCanvas().getGraphicsContext2D(), gl.MAX_LEVEL_COL, gl.MAX_LEVEL_ROW);

        player.draw(gl.getCanvas().getGraphicsContext2D(), app.tileSize);
    }

    public void handleKeyPressed(KeyEvent event) {
        keyHandler.keyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        keyHandler.keyReleased(event);
    }

    public GameLevel getGl() {
        return gl;
    }
}