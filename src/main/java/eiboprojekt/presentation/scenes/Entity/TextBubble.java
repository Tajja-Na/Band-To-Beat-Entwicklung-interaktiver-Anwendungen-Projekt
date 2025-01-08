package eiboprojekt.presentation.scenes.Entity;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TextBubble {
    private String message; // Die Nachricht in der Bubble
    private int width; // Breite der Bubble
    private int height; // Höhe der Bubble
    private int padding; // Abstand des Textes zum Rand
    private Color backgroundColor; // Hintergrundfarbe
    private Color borderColor; // Rahmenfarbe
    private Color textColor; // Textfarbe

    public TextBubble(String message, int width, int height) {
        this.message = message;
        this.width = width;
        this.height = height;
        this.padding = 10;
        this.backgroundColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        this.textColor = Color.BLACK;
    }

    // Methode zum Zeichnen der Bubble
    public void draw(GraphicsContext gc, int x, int y) {
        // Berechne die Position der Bubble (zentriere sie horizontal)
        int bubbleX = x - width / 2;
        int bubbleY = y - height;

        // Zeichne die Bubble (Hintergrund mit abgerundeten Ecken)
        gc.setFill(backgroundColor);
        gc.fillRect(bubbleX, bubbleY, width, height);

        // Setze die Randdicke
        gc.setStroke(borderColor);
        gc.setLineWidth(2); // Dickerer Rand, kannst die Zahl anpassen, um die gewünschte Dicke zu erreichen

        // Zeichne den Rahmen der Bubble
        gc.strokeRect(bubbleX, bubbleY, width, height);

        // Zeichne den Text in der Mitte der Bubble
        gc.setFill(textColor);
        gc.fillText(message, bubbleX + padding, bubbleY + height / 2 + 5);
    }
}
