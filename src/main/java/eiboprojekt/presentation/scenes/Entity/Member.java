package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;

public class Member extends Entity {
    private Image image;

    public Member(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        getPlayerImage(); // Methode zum Laden des NPC-Bildes
    }

    public void getPlayerImage() {
        try {
            // Sicherstellen, dass der Pfad relativ zum Arbeitsverzeichnis korrekt ist
            String basePath = "assets/Character/Charakter2/";

            // Pfad zu Bild (stelle sicher, dass das Bild existiert)
            File file = new File(basePath + "testPerson.png");

            if (file.exists()) {
                image = new Image(file.toURI().toString()); // Bild aus der Datei laden
                System.out.println("Front image loaded: " + (image != null));
            } else {
                System.err.println("Image file not found: " + file.getPath());
            }

        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Die update-Methode ist leer, da sich Member1 nicht bewegt
    public void update() {
        // Keine Bewegung, daher leer
    }

    public void draw(GraphicsContext gc, int screenX, int screenY) {
        if (image != null) {
            // Das Bild auf der gegebenen Position zeichnen
            gc.drawImage(image, weltX, weltY);
            // System.out.println("Drawing Member1 at: x=" + x + ", y=" + y);
        } else {
            // Fehlerbehandlung, falls das Bild null ist
            System.err.println("Failed to draw Member1: Image is null");
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
