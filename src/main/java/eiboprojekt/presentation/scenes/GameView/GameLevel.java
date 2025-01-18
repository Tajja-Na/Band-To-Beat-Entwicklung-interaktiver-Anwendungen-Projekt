package eiboprojekt.presentation.scenes.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameLevel extends BorderPane {
    //Setup
    private App app;
    private FeldManager fm;
    private GamePanel gp;
    private KeyHandlern keyHandler;
    private Canvas canvas;
    private Sound sound;
    private boolean running;
    private boolean youWon;

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

    private Image obstacleImage;

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

        // Initialisiere den KeyHandler
        keyHandler = new KeyHandlern();
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);

        // Setze den Fokus für Tasteneingaben
        this.setFocusTraversable(true);
        this.requestFocus(); // Fokus setzen

        //Player
        player = new MainCharacterLevel(gp, keyHandler, this);
        running = true;
        youWon = false;

        //Objekte
        obstacles = new ArrayList<>();

        // Hindernisbild laden
        obstacleImage = new Image(new File("src/main/java/eiboprojekt/presentation/scenes/Object/assets/gitarre.png").toURI().toString());

        //Sound
        sound = new Sound();
        //Hier dann der Index vom Lied des Levels, einfügen des Liedes in der Sound-Klasse
        playMusic(0);

        //Style für die Buttons später:
        getStylesheets().add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());
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
                if (running) {
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
        stopMusic();
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
            if (lastObstacleX == null || screenWidth - lastObstacleX >= 3 * tileSize) {
                Double[] newObstacle = new Double[]{(double) screenWidth, (double) player.groundY};
                obstacles.add(newObstacle);
            }
        }
    }

    public void draw(GraphicsContext gc) {

        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawLevel();
        player.draw(gc, tileSize);

        for (Double[] obstacle : obstacles) {
            gc.drawImage(obstacleImage, obstacle[0], obstacle[1], tileSize, tileSize);
        }
    
        // Kollisionszähler zeichnen
        gc.setFill(Color.PURPLE);
        gc.fillText("Kollisionen: " + collisionCount, 10, 20);

        //Bei Game Over
        if (!running) {
        //GraphicsContext gc = canvas.getGraphicsContext2D();
        //Image backgroundImage = new Image(new File("assets/background/background test.jpg").toURI().toString());

        VBox gameOverScreen = new VBox(40);
        gameOverScreen.setAlignment(Pos.CENTER);
        gameOverScreen.setPrefSize(screenWidth, screenHeight);

        gameOverScreen.setStyle("-fx-background-image: url(\"file:assets/background/background-test.jpg\");" + "-fx-background-size: cover;");

        Text gameOverText = new Text("Oh no, Game Over!");
        gameOverText.setFont(new Font(80));
        gameOverText.setFill(Color.RED);

        Button retryButton = new Button("Versuch's nochmal");
        retryButton.getStyleClass().add("button");
        Button backToMapButton = new Button("Zurück zur Map");
        backToMapButton.getStyleClass().add("button");

        retryButton.setOnAction(e -> {
            stopLevelThread();
            restartGame();
        });
        backToMapButton.setOnAction(e -> {
            stopLevelThread();
            goToMap();
        });

        HBox buttons = new HBox(16);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(retryButton, backToMapButton);

        gameOverScreen.getChildren().addAll(gameOverText, buttons);

        this.setCenter(gameOverScreen);
        }

        if (youWon) {
            // Hier dann Dialog mit Character, der sich der Band anschließt! Yey!
        }

    }

    private void checkCollisions() {
        for (Iterator<Double[]> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            Double[] obstacle = iterator.next();
            if (player.getBounds().intersects(obstacle[0], obstacle[1], tileSize, tileSize)) {
                collisionCount++;
                iterator.remove();
                if (collisionCount >= maxCollisions) {
                    running = false;
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
        VBox centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);
        
        fm.ladeKarte("assets/Karte/levelbase1.txt", MAX_LEVEL_COL, MAX_LEVEL_ROW);
        fm.drawLevel(canvas.getGraphicsContext2D(), MAX_LEVEL_COL, MAX_LEVEL_ROW); 

        player.draw(canvas.getGraphicsContext2D(), gp.tileSize);

        centerBox.getChildren().addAll(canvas);
        setCenter(centerBox);
    }

    public void playMusic(int i) {

        sound.loadTrack(i);
        sound.play();
        sound.setVolume(0.2); // nicht so laut
    }

    public void stopMusic() {
        sound.stop();
    }

    public void handleKeyPressed(KeyEvent event) {
        keyHandler.keyPressed(event);

    }

    public void handleKeyReleased(KeyEvent event) {
        keyHandler.keyReleased(event);

    }
}
