package eiboprojekt.presentation.scenes.Felder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import eiboprojekt.presentation.scenes.GameView.GamePanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FeldManager {
    private final int ANZAHL_FELDER_GESAMT = 30;
    private GamePanel gp;

    private Feld[] alleFelder;
    private int[] [] karteFeldNr;

    private String zuladeneKarte = "assets/Karte/testWeltKarte.txt";  //vorerst erste levelkarte hardcoded drinne, danach dynamisch mit den npc interaktion zu erkennen
                                                                   //maybe dann einfach das man beim levelstart den setter von zuladeneKarte aufruft und es dadurch ändert?

    public FeldManager(GamePanel gp){
        this.gp = gp;

        alleFelder = new Feld[ANZAHL_FELDER_GESAMT];
        karteFeldNr = new int [gp.MAX_WELT_COL] [gp.MAX_WELT_ROW];
        
        getFeldImage();
        ladeKarte(zuladeneKarte);
    }

    public void getFeldImage(){
        alleFelder[0] = new Feld();
        alleFelder[0].image = new Image("file:assets/background/himmel 1 tile.png");

        alleFelder[1] = new Feld();
        alleFelder[1].image = new Image("file:assets/background/gras 1 tile.png");

        alleFelder[0] = new Feld();
        alleFelder[0].image = new Image("file:assets/background/himmel 1 tile.png");

        alleFelder[1] = new Feld();
        alleFelder[1].image = new Image("file:assets/background/gras 1 tile.png");
    }

    public void ladeKarte(String zuladeneKarte){

        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        //"assets/Karte/levelbase1.txt"
        try{
            String path = zuladeneKarte;

            BufferedReader br = new BufferedReader(new FileReader(path));

            int col = 0;
            int row = 0;

            while(col < gp.MAX_WELT_COL && row < gp.MAX_WELT_ROW){

                String temp = br.readLine();

                while(col < gp.MAX_WELT_COL){
                    String[] nummern = temp.split(" ");

                    int nr = Integer.parseInt(nummern[col]);

                    karteFeldNr[col][row] = nr;
                    col++;
                }

                col = 0;
                row++;
            }

            br.close();
            System.out.println("hab die karte geladen!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc){
        System.out.println("hi ich bin in draw vom fm drinne");
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int feldNr = karteFeldNr[col][row];

            gc.drawImage(alleFelder[feldNr].image, x, y, gp.tileSize, gp.tileSize);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
