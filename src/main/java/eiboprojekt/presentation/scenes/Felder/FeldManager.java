
package eiboprojekt.presentation.scenes.Felder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.MainCharacter;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FeldManager {
    private final int ANZAHL_FELDER_GESAMT = 150;
    //private GamePanel gp;
    private App app;
    private MainCharacter player;

    public Feld[] alleFelder;
    public int[][] karteFeldNr;

    // vorerst erste levelkarte hardcoded drinne,
                                                          // danach dynamisch mit den npc interaktion zu
                                                          // erkennen
                                                          // maybe dann einfach das man beim levelstart den
                                                          // setter von zuladeneKarte aufruft und es dadurch
                                                          // ändert?

    public FeldManager(App app, MainCharacter player) {
        this.app = app;
        this.player = player;

        alleFelder = new Feld[ANZAHL_FELDER_GESAMT];
        karteFeldNr = new int[app.MAX_WELT_COL][app.MAX_WELT_ROW];

        getFeldImage(); 
    }

    public void getFeldImage() {
        // Im Level
        alleFelder[0] = new Feld();
        alleFelder[0].image = new Image("file:assets/Background/himmel 1 tile.png");
        
        alleFelder[1] = new Feld();
        alleFelder[1].image = new Image("file:assets/Background/gras 1 tile neu.png");
        
        // In der Welt Ansicht
        alleFelder[2] = new Feld();
        alleFelder[2].image = new Image("file:assets/Background/worldBackground/grass.png");

        alleFelder[25] = new Feld();
        alleFelder[25].image = new Image("file:assets/Background/worldBackground/wall.png");
        alleFelder[25].collision = true;

        // Dirt Weg
        alleFelder[3] = new Feld();
        alleFelder[3].image = new Image("file:assets/Background/worldBackground/W1.png");

        alleFelder[4] = new Feld();
        alleFelder[4].image = new Image("file:assets/Background/worldBackground/W2.png");

        alleFelder[5] = new Feld();
        alleFelder[5].image = new Image("file:assets/Background/worldBackground/WegB.png");

        alleFelder[6] = new Feld();
        alleFelder[6].image = new Image("file:assets/Background/worldBackground/OL.png");

        alleFelder[7] = new Feld();
        alleFelder[7].image = new Image("file:assets/Background/worldBackground/OR.png");

        alleFelder[8] = new Feld();
        alleFelder[8].image = new Image("file:assets/Background/worldBackground/UL.png");

        alleFelder[9] = new Feld();
        alleFelder[9].image = new Image("file:assets/Background/worldBackground/UR.png");

        alleFelder[10] = new Feld();
        alleFelder[10].image = new Image("file:assets/Background/worldBackground/Start.png");

        alleFelder[11] = new Feld();
        alleFelder[11].image = new Image("file:assets/Background/worldBackground/EndeL.png");

        alleFelder[12] = new Feld();
        alleFelder[12].image = new Image("file:assets/Background/worldBackground/EndeR.png");

        // Haus 1 Alles -> Soll Solide sein
        alleFelder[13] = new Feld();
        alleFelder[13].image = new Image("file:assets/Haus/Haus1/H11.png");
        alleFelder[13].collision = true;

        alleFelder[14] = new Feld();
        alleFelder[14].image = new Image("file:assets/Haus/Haus1/H12.png");
        alleFelder[14].collision = true;

        alleFelder[15] = new Feld();
        alleFelder[15].image = new Image("file:assets/Haus/Haus1/H13.png");
        alleFelder[15].collision = true;

        alleFelder[16] = new Feld();
        alleFelder[16].image = new Image("file:assets/Haus/Haus1/H14.png");
        alleFelder[16].collision = true;

        alleFelder[17] = new Feld();
        alleFelder[17].image = new Image("file:assets/Haus/Haus1/H15.png");
        alleFelder[17].collision = true;

        alleFelder[18] = new Feld();
        alleFelder[18].image = new Image("file:assets/Haus/Haus1/H16.png");
        alleFelder[18].collision = true;

        alleFelder[19] = new Feld();
        alleFelder[19].image = new Image("file:assets/Haus/Haus1/H17.png");
        alleFelder[19].collision = true;

        alleFelder[20] = new Feld();
        alleFelder[20].image = new Image("file:assets/Haus/Haus1/H18.png");
        alleFelder[20].collision = true;

        alleFelder[21] = new Feld();
        alleFelder[21].image = new Image("file:assets/Haus/Haus1/H19.png");
        alleFelder[21].collision = true;

        alleFelder[22] = new Feld();
        alleFelder[22].image = new Image("file:assets/background/worldBackground/Z.png");
        alleFelder[22].collision = true;

        alleFelder[23] = new Feld();
        alleFelder[23].image = new Image("file:assets/background/worldBackground/Zz.png");
        alleFelder[23].collision = true;

        alleFelder[24] = new Feld();
        alleFelder[24].image = new Image("file:assets/background/worldBackground/Zzz.png");
        alleFelder[24].collision = true;

    }

    public void ladeKarte(String zuladeneKarte) {

        // System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // "assets/Karte/levelbase1.txt"
        try {
            String path = zuladeneKarte;
            BufferedReader br = new BufferedReader(new FileReader(path));

            int col = 0;
            int row = 0;

            while (col < app.MAX_WELT_COL && row < app.MAX_WELT_ROW) {

                String temp = br.readLine();

                while (col < app.MAX_WELT_COL) {
                    String[] nummern = temp.split(" ");

                    int nr = Integer.parseInt(nummern[col]);

                    karteFeldNr[col][row] = nr;
                    col++;
                }

                col = 0;
                row++;
            }
            br.close();

            //System.out.println("hab die karte geladen!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc) {
        //System.out.println("hi ich bin in draw vom fm drinne");
        int weltCol = 0;
        int weltRow = 0;

        while (weltCol < app.MAX_WELT_COL && weltRow < app.MAX_WELT_ROW) {
            int feldNr = karteFeldNr[weltCol][weltRow];

            int weltX = weltCol * app.tileSize;
            int weltY = weltRow * app.tileSize;
            int screenX = weltX - player.weltX + player.screenX;
            // hier geht es darum wo die tiles gezeichnet werden, die karten koordinaten
            // sind anders als die vom player, der sich auf dem screen bewegt, die karten
            // koordinate 0 0 mag bei der karte zwar 0 0 sein aber sie ist zum beispiel vom
            // player 500 500 entfernt, da er sich in der mitt bewegt
            int screenY = weltY - player.weltY + player.screenY;

            if (weltX + app.tileSize * 2 > player.weltX - player.screenX &&
                    weltX - app.tileSize * 2 < player.weltX + player.screenX &&
                    weltY + app.tileSize * 2 > player.weltY - player.screenY &&
                    weltY - app.tileSize * 2 < player.weltY + player.screenY) { // hier wird überprüft welche
                                                                                     // koordinate der karte sich auf
                                                                                     // dem screen befindet und nur
                                                                                     // diese werden dann gezeichnet,
                                                                                     // damit die performance geschont
                                                                                     // wird

                gc.drawImage(alleFelder[feldNr].image, screenX, screenY, app.tileSize, app.tileSize);
            }

            weltCol++;

            if (weltCol == app.MAX_WELT_COL) {
                weltCol = 0;
                weltRow++;
            }
        }
    }
}