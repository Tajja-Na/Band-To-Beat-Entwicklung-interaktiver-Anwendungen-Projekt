package eiboprojekt.presentation.scenes.Entity;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Haus extends Entity {

    private Image image;

    public Haus(int startX, int startY) {
        this.weltX = startX;
        this.weltY = startY;
        getPlayerImage(); // Methode zum Laden des NPC-Bildes
    }

    public void getPlayerImage() {
        try {
            // Sicherstellen, dass der Pfad relativ zum Arbeitsverzeichnis korrekt ist
            String basePath = "assets/Haus/";
            // Pfad zu Bild (stelle sicher, dass das Bild existiert)
            File file = new File(basePath + "haus.png");

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
            // Definieren Sie hier die gewünschte Breite und Höhe
            double width = 200; // Beispielwert, anpassen nach Bedarf
            double height = 200; // Beispielwert, anpassen nach Bedarf

            // Das Bild auf der gegebenen Position mit der angegebenen Größe zeichnen
            gc.drawImage(image, weltX, weltY, width, height);
            // System.out.println("Drawing Haus at: x=" + x + ", y=" + y + " with size: " +
            // width + "x" + height);
        } else {
            // Fehlerbehandlung, falls das Bild null ist
            System.err.println("Failed to draw Haus: Image is null");
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
