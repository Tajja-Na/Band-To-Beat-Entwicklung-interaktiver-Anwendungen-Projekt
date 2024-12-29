package eiboprojekt.presentation.scenes.Entity;

import javafx.scene.image.Image;

//stores variables that will be used in player, usw. classes
public class Entity {

    public int x, y;
    public int speed;
    // jump ertsmal draußen
    public Image back, front, standL, standR, walk1, walk2, walkL1, walkL2, walkR1, walkR2, jumpL, jumpR;

    public String direction;

    public int sprintCountr = 0;
    public int sprintNum;
}
