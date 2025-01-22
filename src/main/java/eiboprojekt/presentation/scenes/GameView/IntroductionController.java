package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import javafx.scene.control.Button;

public class IntroductionController {
    private App app;
    private Introduction intro;
    private Button weiterButton;
    private Button zurueckButton;
    private Button gameStartButton;
    
    public IntroductionController(App app){
        this.app = app;
        intro = new Introduction(app.screenWidth, app.screenHeight);
        weiterButton = intro.getWeiterButton();
        zurueckButton = intro.getZurueckButton();
        gameStartButton = intro.getGameStartButton();

        initialize();
    }

    public void initialize(){
        
        weiterButton.setOnAction(e -> nextPage());

        zurueckButton.setOnAction(e -> previousPage());

        intro.getSwitchButton().setOnAction(e -> app.switchView("GAMEPANEL")); 
    }

    public void nextPage() {
        if (intro.getCurrentPage() < intro.getIntroTexts().size() - 1) {
            intro.setCurrentPage(intro.getCurrentPage()+1);
            updatePage();
        }
    }

    public void previousPage() {
        if (intro.getCurrentPage() > 0) {
            intro.setCurrentPage(intro.getCurrentPage()-1);
            updatePage();
        }
    }

    private void updatePage() {
        intro.getPageText().setText(intro.getIntroTexts().get(intro.getCurrentPage()));
        zurueckButton.setDisable(intro.getCurrentPage() == 0);
        weiterButton.setDisable(intro.getCurrentPage() == intro.getIntroTexts().size() - 1);
        gameStartButton.setVisible(intro.getCurrentPage() == intro.getIntroTexts().size() - 1);
    }

    public Introduction getIntro() {
        return intro;
    }
}
