package eiboprojekt;

import eiboprojekt.presentation.scenes.Navigation;
import eiboprojekt.presentation.scenes.Felder.FeldManager;
import eiboprojekt.presentation.scenes.GameView.DialogPage;
import eiboprojekt.presentation.scenes.GameView.GameLevel;
import eiboprojekt.presentation.scenes.GameView.GameLevelController;
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

    private GameLevelController glController; // vorläufig damit ein Level gemacht werden kann und die LOgik stimmt,
                                              // danach
    // wird mit einem LevelManager gearbeitet
    // -> grobe idee ist das jeder NPC mit einem Level verknüpft ist und somit dann
    // erkannt wird welches Level geladen und welche Karte dezeichnet werden muss

    private Scene scene;

    private boolean ImLevel = false;

    private Sound sound;
    private SoundController soundController;

    public final int tileSize = 64; // hier 128x128
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // Fensterbreite in Pixel
    public final int screenHeight = tileSize * maxScreenRow; // Fensterhöhe in Pixel

    // Die Weltkarte ist größer als der screen anzeigt, weshalb das nochmal extra
    // als variable definiert ist
    public final int MAX_WELT_COL = 64; // 16*4
    public final int MAX_WELT_ROW = 48; // 12*4 oder *5
    public final int WORLS_WIDTH = tileSize * MAX_WELT_COL;
    public final int WORLS_HEIGHT = tileSize * MAX_WELT_ROW;

    @Override
    public void start(Stage primaryStage) {
        Navigation.setCurrentView(new SimpleStringProperty());

        sound = new Sound();
        soundController = new SoundController(sound);

        // Null-Prüfung und Initialisierung
        gpController = new GamePanelController(this);
        System.out.println("GamePanel wurde initialisiert");

        welcomeView = new Welcome(screenWidth, screenHeight);
        System.out.println("WelcomeView wurde initialisiert");

        introductionView = new Introduction(screenWidth, screenHeight);

        // gameLevel = new GameLevel(gamePanel.screenWidth, gamePanel.screenHeight,
        // this, gamePanel);

        rootPane = new StackPane();
        scene = new Scene(rootPane, screenWidth, screenHeight);

        // Fügen Sie zunächst die Welcome-Ansicht hinzu
        rootPane.getChildren().add(welcomeView);

        // Konfigurieren Sie den Switch-Button in der Welcome-Ansicht
        welcomeView.getSwitchButton().setOnAction(e -> switchView("INTRODUCTION"));
        introductionView.getSwitchButton().setOnAction(e -> switchView("GAMEPANEL"));

        // dialogPage.getSwitchButton().setOnAction(e -> switchView("GAMELevel1"));
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
                // gamePanel.stopGameThread(); // Beende den Game-Thread, bevor du ihn neu
                // startest hier muss dann stop Level Thread später hin!
                rootPane.getChildren().add(gpController.getGp());
                gpController.getGp().requestFocus();
                gpController.startGameThread(gpController.getGp().getCanvas().getGraphicsContext2D()); // Starte den
                                                                                                       // Game-Thread
                                                                                                       // neu
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
                glController = new GameLevelController(screenWidth, screenHeight, this, "gitarre.png");
                rootPane.getChildren().add(glController.getGl());
                glController.getGl().requestFocus();
                glController.startLevelThread(glController.getGl().getCanvas().getGraphicsContext2D()); // Starte den
                                                                                                        // Level-Thread
                setImLevel(true);
                break;

            case "GAMELevel2":
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                glController = new GameLevelController(screenWidth, screenHeight, this, "drum.png");
                rootPane.getChildren().add(glController.getGl());
                glController.getGl().requestFocus();
                glController.startLevelThread(glController.getGl().getCanvas().getGraphicsContext2D()); // Starte den
                                                                                                        // Level-Thread
                setImLevel(true);
                break;

            case "GAMELevel3":
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                glController = new GameLevelController(screenWidth, screenHeight, this, "keyboard.png");
                rootPane.getChildren().add(glController.getGl());
                glController.getGl().requestFocus();
                glController.startLevelThread(glController.getGl().getCanvas().getGraphicsContext2D()); // Starte den
                                                                                                        // Level-Thread
                setImLevel(true);
                break;
            default:
                System.err.println("Unbekannte Ansicht: " + viewName);
                break;
        }
    }

    public void setImLevel(boolean imLevel) {
        ImLevel = imLevel;
    }

    public boolean isImLevel() {
        return ImLevel;
    }

    public GamePanelController getGpController() {
        return gpController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
