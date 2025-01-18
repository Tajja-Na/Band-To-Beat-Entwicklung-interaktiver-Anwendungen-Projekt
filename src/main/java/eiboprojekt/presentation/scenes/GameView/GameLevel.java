package eiboprojekt.presentation.scenes.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.CollisionCheck;
import eiboprojekt.presentation.scenes.Entity.Entity;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.MainCharacterLevel;
import eiboprojekt.presentation.scenes.Entity.Member;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import eiboprojekt.presentation.scenes.Object.Objekt;
import eiboprojekt.presentation.scenes.Sounds.Sound;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameLevel extends BorderPane {
    //Setup
    private App app;
    private FeldManager fm;
    private GamePanel gp;
    private KeyHandlern keyHandler;
    private Canvas canvas;
    private Sound sound;

    // Screen settings
    final int originalTileSize = 64; // 64x64 tile
    final int scale = 1; // damit man es halt passend skalieren kann

    public final int tileSize = originalTileSize * scale; // hier 128x128
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // Fensterbreite in Pixel
    public final int screenHeight = tileSize * maxScreenRow; // Fensterhöhe in Pixel

    private int MAX_LEVEL_COL = 32; //16*2
    private int MAX_LEVEL_ROW = 12; //12*1
    public final int WORLS_WIDTH = tileSize * MAX_LEVEL_COL;
    public final int WORLS_HEIGHT = tileSize * MAX_LEVEL_ROW;

    //Objekte und Kollision
    private int collisionCount = 0; // Kollisionen zählen
    private final int maxCollisions = 3;
    private List<Double[]> obstacles = new ArrayList<>();
    private Double lastObstacleX = null;

    //Game Loop bzw Level Loop 
    AnimationTimer gameLoop;
    // Füge eine Variable hinzu, um den Zustand des Timers zu verfolgen
    private boolean levelThreadRunning = false;

    private final MainCharacterLevel player;  

    public Canvas getCanvas() {
        return canvas;
    }

    public GameLevel(int width, int height, App app, GamePanel gp) {

        this.app = app;
        this.gp = gp;   //für was?
        this.fm = gp.getFM();
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);
        /* 
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        */

        // Initialisiere den KeyHandler
        keyHandler = new KeyHandlern();
        // Setze die Tasteneingaben (auf Tastendruck und Tastenauslösung hören)
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);

        // Setze den Fokus für Tasteneingaben
        this.setFocusTraversable(true);
        this.requestFocus(); // Fokus setzen

        //Player
        player = new MainCharacterLevel(gp, keyHandler, this);

        //Objekte
        obstacles = new ArrayList<>();

        //Sound
        sound = new Sound();

        playMusic(0);
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
                if (app.isImLevel()) {
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
        player.update();
        
        // Hindernisse bewegen
        for (Double[] obstacle : obstacles) {
            obstacle[0] -= 5; // Bewegung nach links
        }

        //Objekte Spawnen
        if (Math.random() < 0.02) { 
            if (lastObstacleX == null || lastObstacleX - screenWidth >= 3 * tileSize) {
                Double[] newObstacle = new Double[]{(double) screenWidth, (double) player.groundY};
                obstacles.add(newObstacle);
                lastObstacleX = newObstacle[0];
            }
        }

        checkCollisions();
    }

    public void draw(GraphicsContext gc) {

        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawLevel();
        player.draw(gc, tileSize);

        for (Double[] obstacle : obstacles) {
            gc.fillRect(obstacle[0], obstacle[1], tileSize, tileSize);
        }
    
        // Kollisionszähler zeichnen
        gc.setFill(Color.PURPLE);
        gc.fillText("Kollisionen: " + collisionCount, 10, 20);

    }

    private void checkCollisions() {
        for (Iterator<Double[]> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            Double[] obstacle = iterator.next();
            if (player.getBounds().intersects(obstacle[0], obstacle[1], tileSize, tileSize)) {
                collisionCount++;
                iterator.remove();
                if (collisionCount >= maxCollisions) {
                    gameOver();
                }
            }
        }
    }
    
    private void gameOver() {
        stopLevelThread();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillText("Game Over", screenWidth / 2, screenHeight / 2);
    }

    private void drawLevel(){
        VBox centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);
        
        fm.ladeKarte("assets/Karte/levelbase1.txt", MAX_LEVEL_COL, MAX_LEVEL_ROW);
        fm.drawLevel(canvas.getGraphicsContext2D(), MAX_LEVEL_COL, MAX_LEVEL_ROW); //hier muss halt schon fm.draw() hin aber da muss irgendwie noch davor passieren das die passende karte geladen wird

        player.draw(canvas.getGraphicsContext2D(), gp.tileSize);

        centerBox.getChildren().addAll(canvas);
        setCenter(centerBox);
    }

    public void playMusic(int i) {

        sound.stop();
        sound.loadTrack(i);
        sound.play();
        sound.setVolume(0.2); // nicht so laut
    }

    // Kann man eventuell durch ein lambda ausdruck im construktor ersetzen.
    public void handleKeyPressed(KeyEvent event) {
        keyHandler.keyPressed(event);

    }

    public void handleKeyReleased(KeyEvent event) {
        keyHandler.keyReleased(event);

    }
}