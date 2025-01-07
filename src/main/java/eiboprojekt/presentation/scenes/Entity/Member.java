
package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;

import eiboprojekt.presentation.scenes.GameView.GamePanel;

public class Member extends Entity {
    private String basePath;

    public Member(GamePanel gp, String basePath) {
        super(gp);
        this.basePath = basePath;
        direction = "default";
        this.solideArea = new SolideRec(25, gp.tileSize, 6, gp.tileSize); // x, y, width, height

        getPlayerImage(); // Bilder laden
    }

    public void getPlayerImage() {
        try {
            front = new Image(new File(basePath + "front.png").toURI().toString());
            back = new Image(new File(basePath + "back.png").toURI().toString());
            standL = new Image(new File(basePath + "standL.png").toURI().toString());
            standR = new Image(new File(basePath + "standR.png").toURI().toString());
            jumpL = new Image(new File(basePath + "jumpL.png").toURI().toString());
            jumpR = new Image(new File(basePath + "jumpR.png").toURI().toString());

            System.out.println("Images loaded for Member: " + basePath);
        } catch (Exception e) {
            System.err.println("Error loading images for Member from " + basePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Die update-Methode ist leer, da sich Member1 nicht bewegt
    public void update() {
        // Keine Bewegung, daher leer
    }

    // methode in der basisklasse abstrakt definiert
    @Override
    public void draw(GraphicsContext gc, int tileSize) {
        // Berechne die Bildschirmposition basierend auf der Weltposition und der
        // Spielerposition
        int screenX = weltX - gp.player.weltX + gp.player.screenX;
        int screenY = weltY - gp.player.weltY + gp.player.screenY;

        // Zeichne nur, wenn der Member im sichtbaren Bereich ist
        if (screenX > -tileSize && screenX < gp.screenWidth &&
                screenY > -tileSize && screenY < gp.screenHeight) {
            Image imageToDraw;
            switch (direction) {
                case "up":
                    imageToDraw = back;
                    break;
                case "left":
                    imageToDraw = standL;
                    break;
                case "right":
                    imageToDraw = standR;
                    break;
                default:
                    imageToDraw = front;
                    break;
            }
            gc.drawImage(imageToDraw, screenX, screenY, tileSize, tileSize);

        } else {
            // Fehlerbehandlung, falls das Bild nicht geladen wurde
            System.err.println("Failed to draw Member: Image is null");
        }
    }

    public void setPosition(int x, int y) {
        this.weltX = x;
        this.weltY = y;
    }

    // Member reagiert auf den player und dreht sich zu ihm
    public void facePlayer(MainCharacter player) {
        if (player.getX() < this.weltX) {
            this.direction = "left";
            System.out.println("Member schaut nach links.");
        } else if (player.getX() > this.weltX) {
            this.direction = "right";
            System.out.println("Member schaut nach rechts.");
        } else if (player.getY() < this.weltY) {
            this.direction = "up";
            System.out.println("Member schaut nach oben.");
        } else if (player.getY() > this.weltY) {
            this.direction = "down";
            System.out.println("Member schaut nach unten.");
        }
    }

    // Methode, um zu prüfen, ob der Spieler in der Nähe ist
    public boolean isNear(MainCharacter player, int distanceThreshold) {
        int deltaX = Math.abs(player.getX() - this.weltX);
        int deltaY = Math.abs(player.getY() - this.weltY);

        if (deltaX < distanceThreshold && deltaY < distanceThreshold) {
            System.out.println("Super, Spieler in der Nähe!");
            System.out.println(" ");

            // Spieler ist in der Nähe
            return true;
        } else {
            System.out.println("Schade, Spieler NICHR in der Nähe!");
            System.out.println(" ");
            // Spieler ist nicht in der Nähe
            return false;

        }
    }

    // getter
    public int getX() {
        return this.weltX;
    }

    public int getY() {
        return this.weltY;
    }

}