package eiboprojekt.presentation.scenes.Entity;

import java.io.File;
import eiboprojekt.App;
import eiboprojekt.presentation.scenes.GameView.KeyHandlern;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainCharacter extends Entity {

    private App app;
    private KeyHandlern keyHandler;

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

        screenX = app.screenWidth / 2 - (app.tileSize / 2); // Stelle an den der MainCharacter erscheint
        screenY = app.screenHeight / 2 - (app.tileSize / 2);

        this.solideArea = new SolideRec(25, app.tileSize, 6, app.tileSize);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        weltX = app.tileSize * 10; // Spalte 10 -> spawnpunkt von timmy auf der karte
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Update der Spielerposition je nach gedrückter Taste
        if (keyHandler.isUp() == true || keyHandler.isDown() == true || keyHandler.isLeft() == true
                || keyHandler.isRight() == true) {
            if (keyHandler.isUp() == true) {
                direction = "up";
            }
            if (keyHandler.isDown() == true) {
                direction = "down";
            }
            if (keyHandler.isLeft() == true) {
                direction = "left";
            }
            if (keyHandler.isRight() == true) {
                direction = "right";
            }

            // checkt feld collision -> wenn false kann nicht laufen -> wenn true kann
            // laufen
            collisionON = false;
            app.getGpController().cChecker.checkFeld(this);

            // CHECK OBJECT COLLISION
            int objIndex = app.getGpController().oChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (collisionON == false) {
                switch (direction) {
                    case "up":
                        weltY -= speed;

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
            // Sprite Image wechselt, um Bewegung zu zeigen
            if (sprintCountr > 12) {
                sprintNum = sprintNum == 1 ? 2 : 1;
                sprintCountr = 0;
            }
        }
    }

    public void draw(GraphicsContext gc, int tileSize) {
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
        // Falls Bilder nicht laden wird ein Rechteck geladen
        if (image != null) {
            gc.drawImage(image, screenX, screenY, tileSize, tileSize);
        } else {
            System.err.println("Failed to draw player: Image is null");
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
                    System.out.println("You've got drums!");
                    break;
                case "Gitarre":
                    hasGitarre = true;
                    app.getGpController().obj[i] = null;
                    System.out.println("You've got a gitarre!");
                    break;
                case "Mikrofon":
                    hasMicro = true;
                    app.getGpController().obj[i] = null;
                    System.out.println("You've got a microphone! Let's find some bandmembers!");
                    break;
                case "Keyboard":
                    hasKeyboard = true;
                    app.getGpController().obj[i] = null;
                    System.out.println("You've got a keyboard!");
                    break;
            }
        }
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
