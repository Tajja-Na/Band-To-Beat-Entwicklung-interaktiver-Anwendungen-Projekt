package eiboprojekt;

import eiboprojekt.presentation.scenes.Navigation;
import eiboprojekt.presentation.scenes.GameView.EndeView;
import eiboprojekt.presentation.scenes.GameView.GameLevelController;
import eiboprojekt.presentation.scenes.GameView.GamePanelController;
import eiboprojekt.presentation.scenes.GameView.IntroductionController;
import eiboprojekt.presentation.scenes.GameView.WelcomeController;
import eiboprojekt.presentation.scenes.Sounds.Sound;
import eiboprojekt.presentation.scenes.Sounds.SoundController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    private StackPane rootPane;
    private WelcomeController wController;
    private IntroductionController iController;
    private GamePanelController gpController;
    private GameLevelController glController;
    private EndeView endeView;
    private Scene scene;

    private boolean ImLevel = false;

    private Sound sound;

    // Level geschafft
    private boolean gigiLevelGeschafft = false;
    private boolean ryuLevelGeschafft = false;

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
        gpController = new GamePanelController(this);
        wController = new WelcomeController(this);
        iController = new IntroductionController(this);

        rootPane = new StackPane();
        scene = new Scene(rootPane, screenWidth, screenHeight);

        rootPane.getChildren().add(wController.getWelcomeView());

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
                rootPane.getChildren().add(wController.getWelcomeView());
                break;
            case "INTRODUCTION":
                rootPane.getChildren().add(iController.getIntro());
                break;
            case "GAMEPANEL":
                Navigation.getCurrentView().set(viewName);
                rootPane.getChildren().add(gpController.getGp());
                gpController.getGp().requestFocus();
                gpController.startGameThread(gpController.getGp().getCanvas().getGraphicsContext2D());
                setImLevel(false);
                break;
            case "GAMELevel1":
                Navigation.getCurrentView().set("reset");
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                glController = new GameLevelController(screenWidth, screenHeight, this, "Gigi.png");
                rootPane.getChildren().add(glController.getGl());
                glController.getGl().requestFocus();
                glController.startLevelThread(glController.getGl().getCanvas().getGraphicsContext2D());
                setImLevel(true);
                break;

            case "GAMELevel2":
                Navigation.getCurrentView().set("reset");
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                glController = new GameLevelController(screenWidth, screenHeight, this, "Ryu.png");
                rootPane.getChildren().add(glController.getGl());
                glController.getGl().requestFocus();
                glController.startLevelThread(glController.getGl().getCanvas().getGraphicsContext2D());
                setImLevel(true);
                break;

            case "GAMELevel3":
                Navigation.getCurrentView().set("reset");
                Navigation.getCurrentView().set(viewName);
                gpController.stopGameThread();
                glController = new GameLevelController(screenWidth, screenHeight, this, "Tyler.png");
                rootPane.getChildren().add(glController.getGl());
                glController.getGl().requestFocus();
                glController.startLevelThread(glController.getGl().getCanvas().getGraphicsContext2D());
                setImLevel(true);
                break;

            case "ENDEVIEW":
                Navigation.getCurrentView().set(viewName);
                glController.stopLevelThread();
                endeView = new EndeView(this);
                rootPane.getChildren().add(endeView);
                endeView.requestFocus();
                break;
            default:
                System.err.println("Unbekannte Ansicht: " + viewName);
                break;
        }
    }

    public Sound getSound() {
        return sound;
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

    public boolean isGigiLevelGeschafft() {
        return gigiLevelGeschafft;
    }

    public void setGigiLevelGeschafft(boolean gigiLevelGeschafft) {
        this.gigiLevelGeschafft = gigiLevelGeschafft;
    }

    public boolean isRyuLevelGeschafft() {
        return ryuLevelGeschafft;
    }

    public void setRyuLevelGeschafft(boolean ryuLevelGeschafft) {
        this.ryuLevelGeschafft = ryuLevelGeschafft;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
