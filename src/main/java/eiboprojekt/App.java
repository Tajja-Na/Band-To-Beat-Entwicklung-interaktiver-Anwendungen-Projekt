package eiboprojekt;

import eiboprojekt.presentation.scenes.Navigation;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import eiboprojekt.presentation.scenes.GameView.DialogPage;
import eiboprojekt.presentation.scenes.GameView.GameLevel;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import eiboprojekt.presentation.scenes.GameView.GamePanelController;
import eiboprojekt.presentation.scenes.GameView.Introduction;
import eiboprojekt.presentation.scenes.GameView.Welcome;
import eiboprojekt.presentation.scenes.Sounds.Sound;
import eiboprojekt.presentation.scenes.Sounds.SoundController;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private StackPane rootPane;
    private Welcome welcomeView;
    private Introduction introductionView;
    private DialogPage dialogPage; // DialogPage hinzufügen

    private GamePanelController gpController;
    private GameLevel gameLevel; // vorläufig damit ein Level gemacht werden kann und die LOgik stimmt, danach
                                 // wird mit einem LevelManager gearbeitet
    // -> grobe idee ist das jeder NPC mit einem Level verknüpft ist und somit dann
    // erkannt wird welches Level geladen und welche Karte dezeichnet werden muss

    private Scene scene;

    private boolean ImLevel = false;

    private Sound sound;

    private SoundController soundController;

    public void setImLevel(boolean imLevel) {
        ImLevel = imLevel;
    }

    public boolean isImLevel() {
        return ImLevel;
    }

    @Override
    public void start(Stage primaryStage) {
        Navigation.setCurrentView(new SimpleStringProperty());

        sound = new Sound();
        soundController = new SoundController(sound);

        // Null-Prüfung und Initialisierung
        gpController = new GamePanelController(this);
        System.out.println("GamePanel wurde initialisiert");

        welcomeView = new Welcome(gpController.getGp().screenWidth, gpController.getGp().screenHeight);
        System.out.println("WelcomeView wurde initialisiert");

        introductionView = new Introduction(gpController.getGp().screenWidth, gpController.getGp().screenHeight);

        //gameLevel = new GameLevel(gamePanel.screenWidth, gamePanel.screenHeight, this, gamePanel);

        rootPane = new StackPane();
        scene = new Scene(rootPane, gpController.getGp().screenWidth, gpController.getGp().screenHeight);

        // Fügen Sie zunächst die Welcome-Ansicht hinzu
        rootPane.getChildren().add(welcomeView);

        // Konfigurieren Sie den Switch-Button in der Welcome-Ansicht
        welcomeView.getSwitchButton().setOnAction(e -> switchView("INTRODUCTION"));
        introductionView.getSwitchButton().setOnAction(e -> switchView("GAMEPANEL"));

        //dialogPage.getSwitchButton().setOnAction(e -> switchView("GAMELevel1"));
        // Dialog-Seite initialisieren (ohne sofort anzuzeigen)
        // dialogPage = new DialogPage("Dies ist ein Testdialog."); vllt man es sinnn
        // für die verschiedenen sdialoge?
        // dialogPage = new DialogPage(this); // Pass 'this' as the App instance
        // dialogPage.getNextButton().setOnAction(e -> switchView("GAMELevel1"));

        // hier vllt auch so ein action ding für gamePanel

        primaryStage.setTitle("Game Game Game Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchView(String viewName) {
        rootPane.getChildren().clear();
        switch (viewName) {
            case "WELCOME":
                Navigation.getCurrentView().set(viewName);
                rootPane.getChildren().add(welcomeView);
                break;
            case "INTRODUCTION":
                rootPane.getChildren().add(introductionView);
                break;
            case "GAMEPANEL":
                Navigation.getCurrentView().set(viewName);
                //gamePanel.stopGameThread(); // Beende den Game-Thread, bevor du ihn neu startest hier muss dann stop Level Thread später hin!
                rootPane.getChildren().add(gpController.getGp());
                gpController.getGp().requestFocus();
                gpController.startGameThread(gpController.getGp().getCanvas().getGraphicsContext2D()); // Starte den Game-Thread neu
                setImLevel(false);
                break;
            /*
             * case "DIALOG":
             * gamePanel.stopGameThread(); // Beende den Game-Thread
             * rootPane.getChildren().add(dialogPage);
             * setImLevel(false);
             * break;
             */
            case "GAMELevel1":
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                gameLevel = new GameLevel(gpController.getGp().screenWidth, gpController.getGp().screenHeight, this, gpController.getGp(), "gitarre.png");
                rootPane.getChildren().add(gameLevel);
                gameLevel.requestFocus();
                gameLevel.startLevelThread(gameLevel.getCanvas().getGraphicsContext2D()); // Starte den Level-Thread
                setImLevel(true);
                break;

            case "GAMELevel2":
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                gameLevel = new GameLevel(gpController.getGp().screenWidth, gpController.getGp().screenHeight, this, gpController.getGp(), "drum.png");
                rootPane.getChildren().add(gameLevel);
                gameLevel.requestFocus();
                gameLevel.startLevelThread(gameLevel.getCanvas().getGraphicsContext2D()); // Starte den Level-Thread
                setImLevel(true);
                break;

            case "GAMELevel3":
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                gameLevel = new GameLevel(gpController.getGp().screenWidth, gpController.getGp().screenHeight, this, gpController.getGp(), "keyboard.png");
                rootPane.getChildren().add(gameLevel);
                gameLevel.requestFocus();
                gameLevel.startLevelThread(gameLevel.getCanvas().getGraphicsContext2D()); // Starte den Level-Thread
                setImLevel(true);
                break;
            default:
                System.err.println("Unbekannte Ansicht: " + viewName);
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
