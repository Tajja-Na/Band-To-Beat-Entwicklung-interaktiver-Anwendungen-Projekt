package eiboprojekt.presentation.scenes.Felder;

import java.io.BufferedReader;
import java.io.FileReader;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FeldManagerLevel {
    private final int ANZAHL_FELDER_GESAMT = 150;
    private GamePanel gp;
    private App app;

    public Feld[] alleFelder;
    public int[][] karteFeldNr;

    public FeldManagerLevel(App app){
        this.app = app;

        alleFelder = new Feld[ANZAHL_FELDER_GESAMT];
        karteFeldNr = new int[app.MAX_WELT_COL][app.MAX_WELT_ROW];

        getFeldImage();
    }

    public void getFeldImage(){
        // Im Level
        alleFelder[0] = new Feld();
        alleFelder[0].image = new Image("file:assets/Background/himmel 1 tile.png");
        
        alleFelder[1] = new Feld();
        alleFelder[1].image = new Image("file:assets/Background/gras 1 tile neu.png");
        
        // In der Welt Ansicht
        alleFelder[2] = new Feld();
        alleFelder[2].image = new Image("file:assets/Background/worldBackground/grass.png");
    }

    public void ladeKarte(String zuladeneKarte, int MaxlevelCol, int MaxlevelRow) {
        try {
            String path = zuladeneKarte;
            BufferedReader br = new BufferedReader(new FileReader(path));

            int col = 0;
            int row = 0;

            while (col < MaxlevelCol && row < MaxlevelRow) {

                String temp = br.readLine();

                while (col < MaxlevelCol) {
                    String[] nummern = temp.split(" ");

                    int nr = Integer.parseInt(nummern[col]);

                    karteFeldNr[col][row] = nr;
                    col++;
                }

                col = 0;
                row++;
            }
            br.close();

            //System.out.println("hab die Levelkarte geladen!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawLevel(GraphicsContext gc, int MaxLevelCol, int MaxLevelRow) {
        //System.out.println("hi ich bin in drawLevel vom fm drinne");
        int weltCol = 0;
        int weltRow = 0;

        while (weltCol <  MaxLevelCol && weltRow < MaxLevelRow) {
            int feldNr = karteFeldNr[weltCol][weltRow];

            int weltX = weltCol * app.tileSize;
            int weltY = weltRow * app.tileSize;

            gc.drawImage(alleFelder[feldNr].image, weltX, weltY, app.tileSize, app.tileSize);

            weltCol++;

            if (weltCol == MaxLevelCol) {
                weltCol = 0;
                weltRow++;
            }
        }
    }
}
