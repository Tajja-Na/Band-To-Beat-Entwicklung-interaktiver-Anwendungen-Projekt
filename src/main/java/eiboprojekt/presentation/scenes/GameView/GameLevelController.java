package eiboprojekt.presentation.scenes.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.Entity;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.MainCharacterLevel;
import eiboprojekt.presentation.scenes.Entity.Member;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import eiboprojekt.presentation.scenes.Felder.FeldManagerLevel;
import eiboprojekt.presentation.scenes.Object.Objekt;
import eiboprojekt.presentation.scenes.Sounds.Sound;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameLevelController{
    //Setup
    private App app;
    private GameLevel gl;
    private FeldManagerLevel fm;
    private KeyHandlern keyHandler;

    private Sound sound;

    private boolean youWon;

    //Objekte und Kollision
    private int collisionCount = 0; // Kollisionen zählen
    private final int maxCollisions = 3;
    private List<Double[]> obstacles = new ArrayList<>();
    private Double lastObstacleX = null;

    private Image obstacleImage;

    private String obstacleName;

    //Game Loop bzw Level Loop 
    AnimationTimer gameLoop;
    // Füge eine Variable hinzu, um den Zustand des Timers zu verfolgen
    private boolean levelThreadRunning = false;

    private final MainCharacterLevel player;  

    public GameLevelController(int width, int height, App app, String obstacleName) {
        this.app = app; 
        this.gl = new GameLevel();
        this.obstacleName = obstacleName;
        this.fm = new FeldManagerLevel(app);

        // Initialisiere den KeyHandler
        keyHandler = new KeyHandlern();
        gl.setOnKeyPressed(this::handleKeyPressed);
        gl.setOnKeyReleased(this::handleKeyReleased);

        // Setze den Fokus für Tasteneingaben
        gl.setFocusTraversable(true);
        gl.requestFocus(); // Fokus setzen

        //Player
        player = new MainCharacterLevel(app, keyHandler, gl);
        gl.running = true;
        youWon = false;

        //Objekte
        obstacles = new ArrayList<>();
        String path = "src/main/java/eiboprojekt/presentation/scenes/Object/assets/";
        // Hindernisbild laden
        obstacleImage = new Image(new File(path+obstacleName).toURI().toString());

        //Sound
        sound = new Sound();
        //Hier dann der Index vom Lied des Levels, einfügen des Liedes in der Sound-Klasse
        //playMusic(0);
    }

    public void startLevelThread(GraphicsContext gc) {
        if (levelThreadRunning) {
            return; // Verhindere, dass der Game-Thread mehrfach gestartet wird
        }
        levelThreadRunning = true;
        System.out.println("Level Thread gestartet.");

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
        //stopMusic();
        if (gameLoop != null) {
            gameLoop.stop();
            levelThreadRunning = false;
        }
    }

    public void update() {
        player.update();
        
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
                Double[] newObstacle = new Double[]{(double) app.screenWidth, (double) (player.groundY + app.tileSize/2)};
                obstacles.add(newObstacle);
            }
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
        gc.fillText("Kollisionen: " + collisionCount, 10, 20);

        //Bei Game Over
        if (!gl.running) {
            gl.setzeCanvas();
            gl.retryButton.setOnAction(e -> {
                stopLevelThread();
                restartGame();
            });

            gl.backToMapButton.setOnAction(e -> {
                stopLevelThread();
                goToMap();
            });
        }

        if (youWon) {
            // Hier dann Dialog mit Character, der sich der Band anschließt! Yey!
            // hier noch gamethread beenden und 
        }
    }

    private void checkCollisions() {
        for (Iterator<Double[]> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            Double[] obstacle = iterator.next();
            if (player.getBounds().intersects(obstacle[0], obstacle[1], app.tileSize, app.tileSize)) {
                collisionCount++;
                iterator.remove();
                if (collisionCount >= maxCollisions) {
                    gl.running = false;
                    //gameOver();
                }
            }
        }
    }

    private void restartGame() {
        app.switchView("GAMELevel1");
    }

    private void goToMap() {
        app.switchView("GAMEPANEL");
    }

    private void drawLevel(){
        fm.ladeKarte("assets/Karte/levelbase1.txt", gl.MAX_LEVEL_COL, gl.MAX_LEVEL_ROW);
        fm.drawLevel(gl.getCanvas().getGraphicsContext2D(), gl.MAX_LEVEL_COL, gl.MAX_LEVEL_ROW); 

        player.draw(gl.getCanvas().getGraphicsContext2D(), app.tileSize);
    }

    /*public void playMusic(int i) {

        sound.loadTrack(i);
        sound.play();
        sound.setVolume(0.2); // nicht so laut
    }*/

    /*public void stopMusic() {
        sound.stop();
    }*/

    public void handleKeyPressed(KeyEvent event) {
        keyHandler.keyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        keyHandler.keyReleased(event);
    }

    public GameLevel getGl(){
        return gl;
    }
}