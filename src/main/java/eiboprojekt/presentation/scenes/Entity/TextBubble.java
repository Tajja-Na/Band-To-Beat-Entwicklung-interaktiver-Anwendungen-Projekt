package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TextBubble {
    private String message;
    private int width;
    private int height;
    private int padding;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;

    public TextBubble(String message, int width, int height) {
        this.message = message;
        this.width = width;
        this.height = height;
        this.padding = 10;
        this.backgroundColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        this.textColor = Color.BLACK;
    }

    public void draw(GraphicsContext gc, int x, int y) {
        int bubbleX = x - width / 2;
        int bubbleY = y - height;

        gc.setFill(backgroundColor);
        gc.fillRect(bubbleX, bubbleY, width, height);

        gc.setStroke(borderColor);
        gc.setLineWidth(2);

        gc.strokeRect(bubbleX, bubbleY, width, height);

        gc.setFill(textColor);
        gc.fillText(message, bubbleX + padding, bubbleY + height / 2 + 5);
    }
}