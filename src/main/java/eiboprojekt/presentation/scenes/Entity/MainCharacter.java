package eiboprojekt.presentation.scenes.Entity;

import java.io.File;
import java.io.IOException;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import eiboprojekt.presentation.scenes.GameView.KeyHandlern;
import eiboprojekt.presentation.scenes.Object.Gitarre;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainCharacter extends Entity {
    private KeyHandlern keyHandler;

    private App app; 

    public final int screenX;
    public final int screenY;

    // Objekte
    boolean hasMicro = false;
    public boolean hasKeyboard = false;
    public boolean hasGitarre = false;
    public boolean hasDrums = false;

    public MainCharacter(App app, KeyHandlern keyHandler) {
        this.app = app;
        this.keyHandler = keyHandler;

        screenX = app.screenWidth / 2 - (app.tileSize / 2); // screenX und y is da wo der mc auf dem screen spawnt
        screenY = app.screenHeight / 2 - (app.tileSize / 2);

        this.solideArea = new SolideRec(25, app.tileSize, 6, app.tileSize); // x, y,

        // rechteck

        setDefaultValues();
        getPlayerImage(); // Spielerbilder direkt laden
    }

    public void setDefaultValues() {
        weltX = app.tileSize * 10; // Spalte 10 spawnpunkt von timmy auf der karte
        weltY = (app.tileSize * 27) + (app.tileSize / 2); // Zeile 27

        levelX = app.tileSize * 2;
        levelY = app.tileSize * 11;

        speed = 5;

        direction = "default";
    }

    public void getPlayerImage() {
        try {
            String basePath = "assets/Character/Charakter1/";
            front = new Image(new File(basePath + "timmy pixel front stand.png").toURI().toString());
            back1 = new Image(new File(basePath + "timmy pixel sprint hinten 1.png").toURI().toString());
            back2 = new Image(new File(basePath + "timmy pixel sprint hinten 2.png").toURI().toString());
            walk1 = new Image(new File(basePath + "timmy pixel sprint front 1.png").toURI().toString());
            walk2 = new Image(new File(basePath + "timmy pixel sprint front 2.png").toURI().toString());
            walkL1 = new Image(new File(basePath + "timmy pixel sprint 1 L.png").toURI().toString());
            walkL2 = new Image(new File(basePath + "timmy pixel sprint 2 L.png").toURI().toString());
            walkR1 = new Image(new File(basePath + "timmy pixel sprint 1 R.png").toURI().toString());
            walkR2 = new Image(new File(basePath + "timmy pixel sprint 2 R.png").toURI().toString());
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

    public void update() {
        // System.out.println("Player position: x=" + weltX + ", y=" + weltY);
        // Update der Spielerposition je nach gedrückter Taste
        // Eine einzige if-Bedingung für alle Tasteneingaben
        if (keyHandler.isUp() == true || keyHandler.isDown() == true || keyHandler.isLeft() == true
                || keyHandler.isRight() == true) {
            // Update der Spielerposition je nach gedrückter Taste
            if (keyHandler.isUp() == true) { // explizit true verwenden
                direction = "up";
            }
            if (keyHandler.isDown() == true) { // explizit true verwenden
                direction = "down";
            }
            if (keyHandler.isLeft() == true) { // explizit true verwenden
                direction = "left";
            }
            if (keyHandler.isRight() == true) { // explizit true verwenden
                direction = "right";
            }

            // checkt feld collision -> wenn false kann nicht laufen -> wenn true laufen
            collisionON = false;
            app.getGpController().cChecker.checkFeld(this);

            // CHECK OBJECT COLLISION
            int objIndex = app.getGpController().oChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (collisionON == false) {
                switch (direction) {
                    case "up":
                        weltY -= speed; // wurde von oben hier rein gesetzt -> bewegt sich so

                        break;
                    case "down":
                        weltY += speed;

                        break;
                    case "left":
                        weltX -= speed;

                        break;
                    case "right":
                        weltX += speed;

                        break;
                    default:
                        break;
                }
            }
            sprintCountr++;
            if (sprintCountr > 12) {
                sprintNum = sprintNum == 1 ? 2 : 1;
                sprintCountr = 0;
            }
        }
    }

    public void draw(GraphicsContext gc, int tileSize) {

        // Wechsle dann zu deinem Bild, wenn das Rechteck funktioniert
        Image image;
        switch (direction) {
            case "up":
                image = sprintNum == 1 ? back1 : back2;
                break;
            case "down":
                image = sprintNum == 1 ? walk1 : walk2;
                break;
            case "left":
                image = sprintNum == 1 ? walkL1 : walkL2;
                break;
            case "right":
                image = sprintNum == 1 ? walkR1 : walkR2;
                break;
            default:
                image = front; // Standardbild
                break;
        }
        if (image != null) {
            gc.drawImage(image, screenX, screenY, tileSize, tileSize);
            // System.out.println("Drawing player at: x=" + screenX + ", y=" + screenY + ",
            // direction: " + direction);
        } else {
            System.err.println("Failed to draw player: Image is null");
            // Zeichne ein Ersatz-Rechteck
            gc.setFill(Color.RED);
            gc.fillRect(screenX, screenY, tileSize, tileSize);
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = app.getGpController().obj[i].name;

            switch (objectName) {
                case "Schlagzeug":
                    hasDrums = true;
                    app.getGpController().obj[i] = null;
                    //gp.playSE(2);
                    System.out.println("You've got drums!");
                    break;
                case "Gitarre":
                    hasGitarre = true;
                    app.getGpController().obj[i] = null;
                    //gp.playSE(2);
                    System.out.println("You've got a gitarre!");
                    break;
                case "Mikrofon":
                    hasMicro = true;
                    app.getGpController().obj[i] = null;
                    //gp.playSE(2);
                    System.out.println("You've got a microphone! Let's find some bandmembers!");
                    break;
                case "Keyboard":
                    hasKeyboard = true;
                    app.getGpController().obj[i] = null;
                    //gp.playSE(2);
                    System.out.println("You've got a keyboard!");
                    break;
            }
        }
    }

    /*
     * für die interaktion
     * public boolean isNear(Entity member) {
     * int distanceX = Math.abs(this.weltX - member.weltX); // Weltkoordinaten
     * vergleichen
     * int distanceY = Math.abs(this.weltY - member.weltY);
     * 
     * // Definiere eine maximale Distanz, z. B. 64 Pixel
     * int maxDistance = 64;
     * 
     * return distanceX < maxDistance && distanceY < maxDistance;
     * }
     */

    public void interact() {
        // Hier kannst du die Interaktion starten (z.B. Dialog anzeigen)
        System.out.println("Interacting with Member1");
        // In deinem Fall würdest du hier den Dialog öffnen
        // z.B. Öffnen eines neuen Fensters, das den Dialog zeigt
    }

    public int getX() {
        return this.weltX;
    }

    public int getY() {
        return this.weltY;
    }

    @Override
    public void setPosition(int x, int y) {
        this.weltX = x;
        this.weltY = y;
    }

}
