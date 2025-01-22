
package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.GameView.GamePanel;

public class Member extends Entity {
    private String basePath;
    private String name;
    private String instrumente;
    private App app;
    private TextBubble textBubble = new TextBubble("Press E", 60, 40);

    public Member(App app, String basePath, String name, String instrumente) {
        this.basePath = basePath;
        this.name = name;
        this.instrumente = instrumente;
        this.app = app;
        direction = "default";
        this.solideArea = new SolideRec(25, app.tileSize, 6, app.tileSize); // x, y, width, height

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
        int screenX = weltX - app.getGpController().player.weltX + app.getGpController().player.screenX;
        int screenY = weltY - app.getGpController().player.weltY + app.getGpController().player.screenY;

        // Zeichne nur, wenn der Member im sichtbaren Bereich ist
        if (screenX > -tileSize && screenX < app.screenWidth &&
                screenY > -tileSize && screenY < app.screenHeight) {
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
            // Prüfe, ob der Spieler nah genug ist
            if (isNear(app.getGpController().player, app.tileSize)) {
                textBubble.draw(gc, screenX, screenY - app.tileSize / 2);
            }
        } else {
            // Fehlerbehandlung, falls das Bild nicht geladen wurde
            // System.err.println("Failed to draw Member: Image is null");
        }
    }

    public void setPosition(int x, int y) {
        this.weltX = x;
        this.weltY = y;
    }

    // Methode, um zu prüfen, ob der Spieler in der Nähe ist
    public boolean isNear(MainCharacter player, int distanceThreshold) {
        int deltaX = Math.abs(player.getX() - this.weltX);
        int deltaY = Math.abs(player.getY() - this.weltY);

        if (deltaX < distanceThreshold && deltaY < distanceThreshold) {
            // System.out.println("Super, Spieler in der Nähe!");
            // System.out.println(" ");

            // Spieler ist in der Nähe
            return true;
        } else {
            // System.out.println("Schade, Spieler NICHR in der Nähe!");
            // System.out.println(" ");
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

    public String getName() {
        return this.name;
    }

    public String getInstrument() {
        return this.instrumente; // Instrument-Variable in der Member-Klasse
    }

}