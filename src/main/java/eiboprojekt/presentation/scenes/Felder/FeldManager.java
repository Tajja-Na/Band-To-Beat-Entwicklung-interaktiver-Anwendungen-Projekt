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
        int weltCol = 0;
        int weltRow = 0;

        while(weltCol < gp.MAX_WELT_COL && weltRow < gp.MAX_WELT_ROW){
            int feldNr = karteFeldNr[weltCol][weltRow];

            int weltX = weltCol * gp.tileSize;
            int weltY = weltRow * gp.tileSize;
            int screenX = weltX - gp.player.weltX + gp.player.screenX; 
            //hier geht es darum wo die tiles gezeichnet werden, die karten koordinaten sind anders als die vom player, der sich auf dem screen bewegt, die karten koordinate 0 0 mag bei der karte zwar 0 0  sein aber sie ist zum beispiel vom player 500 500 entfernt, da er sich in der mitt bewegt
            int screenY = weltY - gp.player.weltY + gp.player.screenY;

            if(weltX + gp.tileSize*2 > gp.player.weltX - gp.player.screenX &&
                weltX - gp.tileSize*2 < gp.player.weltX + gp.player.screenX &&
                    weltY + gp.tileSize*2 > gp.player.weltY - gp.player.screenY &&
                        weltY - gp.tileSize*2 < gp.player.weltY + gp.player.screenY){  //hier wird überprüft welche koordinate der karte sich auf dem screen befindet und nur diese werden dann gezeichnet, damit die performance geschont wird

                gc.drawImage(alleFelder[feldNr].image, screenX, screenY, gp.tileSize, gp.tileSize);
            }

            
            weltCol++;

            if(weltCol == gp.MAX_WELT_COL){
                weltCol = 0;
                weltRow++;
            }
        }
    }
}
