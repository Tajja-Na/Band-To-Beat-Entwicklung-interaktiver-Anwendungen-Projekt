package eiboprojekt.presentation.scenes.Entity;

import java.io.File;
import eiboprojekt.App;
import eiboprojekt.presentation.scenes.GameView.GameLevel;
import eiboprojekt.presentation.scenes.GameView.KeyHandlern;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainCharacterLevel extends Entity {
    final double scale = 1.5; // Timmy ist bisschen größer im Level

    public int screenX;
    public int screenY;
    public int groundY;
    private KeyHandlern keyHandler;
    private int tileSize;
    private App app;

    public int velocityY;
    private boolean isOnGround;

    public MainCharacterLevel(App app, KeyHandlern keyHandler) {
        this.app = app;
        this.keyHandler = keyHandler;
        this.tileSize = app.tileSize;

        screenX = app.screenWidth - (15 * app.tileSize); // spawnpunkt vom character
        screenY = app.screenHeight - (3 * app.tileSize);
        groundY = app.screenHeight - (3 * app.tileSize);

        this.solideArea = new SolideRec(25, app.tileSize / 2 + 8, 4, app.tileSize / 2); // für collisioncheck

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        weltX = app.tileSize * 10; // Spalte 10 spawnpunkt von timmy auf der karte
        weltY = (app.tileSize * 27) + (app.tileSize / 2); // Zeile 27

        levelX = app.tileSize * 2;
        levelY = app.tileSize * 11;

        speed = 5;

        direction = "default";
    }

    public void getPlayerImage() {
        try {
            String basePath = "assets/Character/Charakter1/";
            walkR1 = new Image(new File(basePath + "timmy pixel sprint 1 R.png").toURI().toString());
            walkR2 = new Image(new File(basePath + "timmy pixel sprint 2 R.png").toURI().toString());
        } catch (Exception e) {
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

        // Running
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
        Image image = sprintNum == 1 ? walkR1 : walkR2;
        if (image != null) {
            gc.drawImage(image, screenX, screenY, tileSize * scale, tileSize * scale);
        } else {
            System.err.println("Failed to draw player: Image is null");
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
