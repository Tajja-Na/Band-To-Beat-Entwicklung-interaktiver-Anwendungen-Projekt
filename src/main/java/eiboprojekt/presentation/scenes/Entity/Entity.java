package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {

    public int weltX, weltY;
    public int levelX, levelY;
    public int speed;

    public Image back, back1, back2, front, standL, standR, walk1, walk2, walkL1, walkL2, walkR1, walkR2;

    public String direction;

    public int sprintCountr = 0;
    public int sprintNum;

    // Für Object Collision
    public int solidAreaDefaultX, solidAreaDefaultY;

    // Für die collision -> hier erstellen wie eine klasse die ein rechteck auf der
    // figur
    // produziert dass man an diesen punkt die collision hat -> weil ansonsten wird
    // das ganze 64x64 feld als Collisions Area ausgewählt
    public SolideRec solideArea;
    public boolean collisionON = false;

    // Abstrakte Methode, die alle Unterklassen implementieren müssen
    public abstract void draw(GraphicsContext gc, int tileSize);

    public abstract void setPosition(int x, int y);

    public boolean isNear(Entity other) {
        int distance = 20; // Anpassbarer Erkennungsradius
        return Math.abs(this.weltX - other.weltX) < distance &&
                Math.abs(this.weltY - other.weltY) < distance;
    }
}