package eiboprojekt;

import eiboprojekt.presentation.scenes.Felder.FeldManager;
import eiboprojekt.presentation.scenes.GameView.DialogPage;
import eiboprojekt.presentation.scenes.GameView.GameLevel;
import eiboprojekt.presentation.scenes.GameView.GamePanel;
import eiboprojekt.presentation.scenes.GameView.Introduction;
import eiboprojekt.presentation.scenes.GameView.Welcome;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private StackPane rootPane;
    private Welcome welcomeView;
    private Introduction introductionView;
    private DialogPage dialogPage; // DialogPage hinzufügen

    private GamePanel gamePanel;
    private GameLevel gameLevel;
    private FeldManager fm;
    private Scene scene;

    private boolean ImLevel = false;

    public void setImLevel(boolean imLevel) {
        ImLevel = imLevel;
    }

    public boolean isImLevel() {
        return ImLevel;
    }

    @Override
    public void start(Stage primaryStage) {
  
        // Null-Prüfung und Initialisierung
        if (gamePanel == null) {
            gamePanel = new GamePanel(this);
            System.out.println("GamePanel wurde initialisiert");
        }

        if (welcomeView == null) {
            welcomeView = new Welcome(gamePanel.screenWidth, gamePanel.screenHeight);
            System.out.println("WelcomeView wurde initialisiert");
        }
        introductionView = new Introduction(gamePanel.screenWidth, gamePanel.screenHeight);

        fm = new FeldManager(gamePanel);

        gameLevel = new GameLevel(gamePanel.screenWidth, gamePanel.screenHeight, this, fm);

        rootPane = new StackPane();
        scene = new Scene(rootPane, gamePanel.screenWidth, gamePanel.screenHeight);

        // Fügen Sie zunächst die Welcome-Ansicht hinzu
        rootPane.getChildren().add(welcomeView);

        // Konfigurieren Sie den Switch-Button in der Welcome-Ansicht
        welcomeView.getSwitchButton().setOnAction(e -> switchView("INTRODUCTION"));
        introductionView.getSwitchButton().setOnAction(e -> switchView("GAMEPANEL"));

        // Dialog-Seite initialisieren (ohne sofort anzuzeigen)
        // dialogPage = new DialogPage("Dies ist ein Testdialog."); vllt man es sinnn
        // für die verschiedenen sdialoge?
        dialogPage = new DialogPage();
        dialogPage.getNextButton().setOnAction(e -> switchView("GAMELevel1"));
        

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
                rootPane.getChildren().add(welcomeView);
                break;
            case "INTRODUCTION":
                rootPane.getChildren().add(introductionView);
                break;
            case "GAMEPANEL":
                rootPane.getChildren().add(gamePanel);
                gamePanel.requestFocus();
                gamePanel.startGameThread();
                setImLevel(false);
                break;
            case "DIALOG1":
                // Dialog für Member1 anzeigen
                rootPane.getChildren().add(dialogPage);
                break;
            case "GAMELevel1":
                rootPane.getChildren().add(gameLevel);
                gamePanel.requestFocus();
                gamePanel.startGameThread();
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
