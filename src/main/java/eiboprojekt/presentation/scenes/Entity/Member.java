
package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;

import eiboprojekt.presentation.scenes.GameView.GamePanel;

public class Member extends Entity {
    private Image image;

    public Member(GamePanel gp) {
        super(gp);
        direction = "default";
        this.solideArea = new SolideRec(25, gp.tileSize, 6, gp.tileSize); // x,y, width und height vom

        getPlayerImage(); // Methode zum Laden des NPC-Bildes
    }

    public void getPlayerImage() {
        try {
            String basePath = "assets/Character/Charakter2/";
            front = new Image(new File(basePath + "front.png").toURI().toString());
            back = new Image(new File(basePath + "back.png").toURI().toString());
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

    // getter
    public int getX() {
        return this.weltX;
    }

    public int getY() {
        return this.weltY;
    }

}