package eiboprojekt.presentation.scenes.Entity;

import java.io.File;

import eiboprojekt.presentation.scenes.GameView.GameLevel;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import eiboprojekt.presentation.scenes.GameView.KeyHandlern;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class MainCharacterLevel extends Entity{
    final double scale = 1.5; // damit man es halt passend skalieren kann

    public int screenX;
    public int screenY;
    public int groundY;
    private KeyHandlern keyHandler;
    private int tileSize;

    private GameLevel level;

    public int velocityY;
    private boolean isOnGround;

    public MainCharacterLevel(GamePanel gamePanel, KeyHandlern keyHandler, GameLevel level) {
        super(gamePanel); // damit es den gp vom Entity bekommz

        this.gp = gamePanel;
        this.level = level;
        this.keyHandler = keyHandler;
        this.tileSize = gamePanel.tileSize; // `tileSize` aus GamePanel übernehmen
        
        screenX =  gp.screenWidth - (15 * gp.tileSize); //spawnpunkt vom character
        screenY = gp.screenHeight - (3 * gp.tileSize);
        groundY = gp.screenHeight - (3 * gp.tileSize);
        
        this.solideArea = new SolideRec(25, gp.tileSize / 2 + 8, 4, gp.tileSize / 2); // x,y, width und height vom
        // rechteck

        setDefaultValues();
        getPlayerImage(); // Spielerbilder direkt laden
        
    }

    public void setDefaultValues() {
        weltX = gp.tileSize * 10; // Spalte 10 spawnpunkt von timmy auf der karte
        weltY = (gp.tileSize * 27) + (gp.tileSize / 2); // Zeile 27

        levelX = gp.tileSize * 2;
        levelY = gp.tileSize * 11;

        speed = 5;

        direction = "default";
    }

    public void getPlayerImage() {
        try {
            String basePath = "assets/Character/Charakter1/";
            walkR1 = new Image(new File(basePath + "timmy pixel sprint 1 R.png").toURI().toString());
            walkR2 = new Image(new File(basePath + "timmy pixel sprint 2 R.png").toURI().toString());
            jumpL = new Image(new File(basePath + "jumpL.png").toURI().toString());
            jumpR = new Image(new File(basePath + "jumpR.png").toURI().toString());

            System.out.println("Front image loaded: " + (front != null));
            // Wiederholen Sie diese Prüfung für die anderen Bilder

        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update() {

    velocityY += 1; // Geschwindigkeit, mit der er wieder auf den Boden kommt
    screenY += velocityY;

    // Bodenprüfung
    if (screenY > groundY) {
        screenY = groundY;
        velocityY = 0;
    }

    if (keyHandler.isJump()) {
        jump();
    }
            sprintCountr++;
            if (sprintCountr > 12) {
                sprintNum = sprintNum == 1 ? 2 : 1;
                sprintCountr = 0;
            } 
    }

    public void jump() {
        if (isOnGround()) { // Prüfe, ob der Charakter auf dem Boden ist
            velocityY = -24; // Höhe des Sprungs
        }
    }
    

    public void draw(GraphicsContext gc, int tileSize) {
        // Wechsle dann zu deinem Bild, wenn das Rechteck funktioniert
        Image image = sprintNum == 1 ? walkR1 : walkR2;
        
        if (image != null) {
            gc.drawImage(image, screenX, screenY, tileSize*scale, tileSize*scale);
            //System.out.println("Drawing player at: x=" + screenX + ", y=" + screenY + ", direction: " + direction);
        } else {
            System.err.println("Failed to draw player: Image is null");
            // Zeichne ein Ersatz-Rechteck
            gc.setFill(Color.RED);
            gc.fillRect(screenX, screenY, tileSize, tileSize);
        }
    }

    public boolean isOnGround() {
        return screenY >= groundY; // Überprüfe, ob der Charakter auf Bodenhöhe ist
    }

    public int getX() {
        return this.weltX;
    }

    public int getY() {
        return this.weltY;
    }

    public Rectangle2D getBounds() {
        return new javafx.geometry.Rectangle2D(screenX, screenY, tileSize * scale, tileSize * scale);
    }
    @Override
    public void setPosition(int x, int y) {
        this.weltX = x;
        this.weltY = y;
    }
}
