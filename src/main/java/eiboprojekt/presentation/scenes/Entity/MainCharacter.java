package eiboprojekt.presentation.scenes.Entity;

import java.io.File;
import java.io.IOException;

import eiboprojekt.presentation.scenes.GameView.GamePanel;
import eiboprojekt.presentation.scenes.GameView.KeyHandlern;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainCharacter extends Entity {
    GamePanel gp;
    KeyHandlern keyHandler;
    private int tileSize; // Hinzugefügt

    public MainCharacter(GamePanel gamePanel, KeyHandlern keyHandler) {
        this.gp = gamePanel;
        this.keyHandler = keyHandler;
        this.tileSize = gamePanel.tileSize; // `tileSize` aus GamePanel übernehmen

        setDefaultValues();
        getPlayerImage(); // Spielerbilder direkt laden
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 5;

        direction = "default";
    }

    public void getPlayerImage() {
        try {
            String basePath = "assets/Character/Charakter1/";
            front = new Image(new File(basePath + "front.png").toURI().toString());
            back = new Image(new File(basePath + "back.png").toURI().toString());
            walk1 = new Image(new File(basePath + "walk1.png").toURI().toString());
            walk2 = new Image(new File(basePath + "walk2.png").toURI().toString());
            walkL1 = new Image(new File(basePath + "walkL1.png").toURI().toString());
            walkL2 = new Image(new File(basePath + "walkL2.png").toURI().toString());
            walkR1 = new Image(new File(basePath + "walkR1.png").toURI().toString());
            walkR2 = new Image(new File(basePath + "walkR2.png").toURI().toString());
            standL = new Image(new File(basePath + "standL.png").toURI().toString());
            standR = new Image(new File(basePath + "standR.png").toURI().toString());
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
        System.out.println("Player position: x=" + x + ", y=" + y);
        // Update der Spielerposition je nach gedrückter Taste
        // Eine einzige if-Bedingung für alle Tasteneingaben
        if (keyHandler.isUp() == true || keyHandler.isDown() == true || keyHandler.isLeft() == true
                || keyHandler.isRight() == true) {
            // Update der Spielerposition je nach gedrückter Taste
            if (keyHandler.isUp() == true) { // explizit true verwenden
                direction = "up";
                y -= speed;
            }
            if (keyHandler.isDown() == true) { // explizit true verwenden
                direction = "down";
                y += speed;
            }
            if (keyHandler.isLeft() == true) { // explizit true verwenden
                direction = "left";
                x -= speed;
            }
            if (keyHandler.isRight() == true) { // explizit true verwenden
                direction = "right";
                x += speed;
            }
            sprintCountr++;
            if (sprintCountr > 12) {
                sprintNum = sprintNum == 1 ? 2 : 1;
                sprintCountr = 0;
            }
        }
    }

    public void draw(GraphicsContext gc, int tileSize) {

        // Wechsle dann zu deinem Bild, wenn das Rechteck funktioniert
        Image image;
        switch (direction) {
            case "up":
                image = sprintNum == 1 ? walk1 : walk2;
                break;
            case "down":
                image = sprintNum == 1 ? walk1 : walk2;
                break;
            case "left":
                image = sprintNum == 1 ? walkL1 : walkL2;
                break;
            case "right":
                image = sprintNum == 1 ? walkR1 : walkR2;
                break;
            default:
                image = front; // Standardbild
                break;
        }
        if (image != null) {
            gc.drawImage(image, x, y, tileSize, tileSize);
            System.out.println("Drawing player at: x=" + x + ", y=" + y + ", direction: " + direction);
        } else {
            System.err.println("Failed to draw player: Image is null");
            // Zeichne ein Ersatz-Rechteck
            gc.setFill(Color.RED);
            gc.fillRect(x, y, tileSize, tileSize);
        }
    }

    // für die interaktion
    public boolean isNear(Member1 member) {
        int distanceX = Math.abs(this.x - member.getX());
        int distanceY = Math.abs(this.y - member.getY());

        // definieren hier eine maximale Distanz -> z.B. 64 Pixel
        return distanceX < 64 && distanceY < 64;
    }

    public void interact() {
        // Hier kannst du die Interaktion starten (z.B. Dialog anzeigen)
        System.out.println("Interacting with Member1");
        // In deinem Fall würdest du hier den Dialog öffnen
        // z.B. Öffnen eines neuen Fensters, das den Dialog zeigt
    }

}
