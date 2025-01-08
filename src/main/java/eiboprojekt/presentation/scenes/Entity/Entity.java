package eiboprojekt.presentation.scenes.Entity;

import eiboprojekt.presentation.scenes.GameView.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//stores variables that will be used in player, usw. classes
public abstract class Entity {

    public int weltX, weltY;
    public int levelX, levelY;
    public int speed;
    // jump ertsmal draußen
    public Image back, back1, back2, front, standL, standR, walk1, walk2, walkL1, walkL2, walkR1, walkR2, jumpL, jumpR;

    public String direction;

    public int sprintCountr = 0;
    public int sprintNum;

    // Für die collision -> hier erstellen wie eine klasse die ein rechteck auf der
    // figur
    // produkziert dass man an diesen punkt die collision hat -> weil ansonsten wird
    // das ganze 64x64 feld als Collisions Area ausgewählt
    public SolideRec solideArea;
    public boolean collisionON = false;

    public GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Abstrakte Methode, die alle Unterklassen implementieren müssen
    public abstract void draw(GraphicsContext gc, int tileSize);

    public abstract void setPosition(int x, int y);

    public boolean isNear(Entity other) {
        int distance = 20; // Anpassbarer Erkennungsradius
        return Math.abs(this.weltX - other.weltX) < distance &&
                Math.abs(this.weltY - other.weltY) < distance;
    }

    public void facePlayer(MainCharacter player) {
        int dx = player.weltX - this.weltX;
        int dy = player.weltY - this.weltY;

        if (Math.abs(dx) > Math.abs(dy)) {
            this.direction = (dx > 0) ? "right" : "left";
        } else {
            this.direction = (dy > 0) ? "down" : "up";
        }
    }

}
