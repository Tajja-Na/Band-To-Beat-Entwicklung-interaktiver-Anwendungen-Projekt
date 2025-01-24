package eiboprojekt.presentation.scenes.Object;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.Entity.SolideRec;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Objekt {

    public Image image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public SolideRec solidArea = new SolideRec(0, 0, 64, 64);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(GraphicsContext gc, App app, MainCharacter player) {
        // Berechne die Bildschirmposition basierend auf der Weltposition und der Spielerposition
        int screenX = worldX - player.weltX + player.screenX;
        int screenY = worldY - player.weltY + player.screenY;

        // Zeichne nur, wenn der Member im sichtbaren Bereich ist
        if (screenX > -app.tileSize && screenX < app.screenWidth && screenY > -app.tileSize
                && screenY < app.screenHeight) {

            gc.drawImage(image, screenX, screenY, app.tileSize, app.tileSize);

        }
    }

    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }
}
