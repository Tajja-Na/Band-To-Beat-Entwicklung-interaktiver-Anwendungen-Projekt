package eiboprojekt.presentation.scenes.Object;

import eiboprojekt.presentation.scenes.Entity.SolideRec;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Object {

    public Image image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public SolideRec solidArea = new SolideRec(0, 0, 64, 64);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;


    public void draw(GraphicsContext gc, int tileSize, GamePanel gp) {
        // Berechne die Bildschirmposition basierend auf der Weltposition und der
        // Spielerposition
        int screenX = worldX - gp.player.weltX + gp.player.screenX;
        int screenY = worldY - gp.player.weltY + gp.player.screenY;

        // Zeichne nur, wenn der Member im sichtbaren Bereich ist
        if (screenX > -tileSize && screenX < gp.screenWidth && screenY > -tileSize && screenY < gp.screenHeight) {
            
            gc.drawImage(image, screenX, screenY, tileSize, tileSize);

        }
    }

    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }
}
