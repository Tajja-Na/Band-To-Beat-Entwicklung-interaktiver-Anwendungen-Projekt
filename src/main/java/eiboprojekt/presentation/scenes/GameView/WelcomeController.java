package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import javafx.scene.control.Button;

public class WelcomeController {
    private Welcome welcome;
    private Button switchButton;
    private App app;

    public WelcomeController(App app){
        this.app = app;
        welcome = new Welcome(app.screenWidth, app.screenHeight);
        switchButton = welcome.switchButton;
        initialize();
    }

    public void initialize(){
        switchButton.setOnAction(e -> app.switchView("INTRODUCTION"));
    }

    public Welcome getWelcomeView(){
        return welcome;
    }
}