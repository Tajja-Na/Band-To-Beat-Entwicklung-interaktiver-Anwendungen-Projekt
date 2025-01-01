package eiboprojekt.presentation.scenes.Level;

import java.util.ArrayList;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Felder.FeldManager;

public class LevelManager {

    private int levelWidth;
    private int levelHeight;
    private App app;
    private FeldManager fm;

    private int MAX_ANZAHL_LEVEL;

    private ArrayList<Level> alleLevel;
     

    public LevelManager(int width, int height, App app){
        //this.fm = fm; levelmanager muss probably auch das gp kriegen, damit er fm ziehen kann, oder das setzen der zu ladenen karte läuft über die app?
        this.app = app;
        this.levelHeight = height;
        this.levelWidth = width;

        this.alleLevel = new ArrayList<Level>();
    }

    public void ladeLevel(){
        for(int i = 0; i < MAX_ANZAHL_LEVEL; i++){
            alleLevel.add(i, new Level(levelHeight, levelWidth, app, fm));
        }
    }
}