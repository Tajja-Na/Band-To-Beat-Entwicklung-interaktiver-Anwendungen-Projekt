package eiboprojekt.presentation.scenes.GameView;

import java.util.Arrays;
import java.util.List;

import eiboprojekt.App;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

//dialoge aber weiß nicht wie wir hier mehrere dialoge drauß machen 

public class DialogPage extends BorderPane {

    private Button nextButton;
    private Button zurueckButton;
    private Button gameStartButton;
    private Text pageText;
    private List<String> introTexts;
    private int currentPage = 0;
    private Button zurueckMap;
    private Thread dialogThread;

    App app;

    public DialogPage(App app) {
        this.app = app;
        initializeUI();
    }

    private void initializeUI() {
        // Create a vertical box to center content
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        // Initialize dialog text and navigation buttons
        pageText = new Text();
        nextButton = new Button("Weiter");
        zurueckButton = new Button("Zurück");
        zurueckMap = new Button("Zurück zur Map");
        gameStartButton = new Button("Spiel starten");

        // Set styles
        setStyle("-fx-background-color: #282c34;");
        pageText.setStyle("-fx-fill: white; -fx-font-size: 18px;");
        nextButton.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");
        zurueckButton.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");
        gameStartButton.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");
        zurueckMap.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");

        // Initialize intro texts
        introTexts = Arrays.asList(
                "bkbkbkbk",
                "uhh",
                "help",
                "i");

        // Initialize page content
        updatePage();

        // Create navigation box
        HBox navigationBox = new HBox(10);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getChildren().addAll(zurueckButton, nextButton);

        // Add all elements to the center box
        centerBox.getChildren().addAll(pageText, navigationBox, gameStartButton, zurueckMap);

        // Add center box to the center of the BorderPane
        setCenter(centerBox);

        // Button actions
        nextButton.setOnAction(e -> nextPage());
        zurueckButton.setOnAction(e -> previousPage());
        zurueckMap.setOnAction(e -> app.switchView("GAMEPANEL"));

        // Start a background task in a new Thread
        dialogThread = new Thread(() -> {
            try {
                // Simulate some background work
                Thread.sleep(2000); // Simulating a delay of 2 seconds

                // After background work, update UI on JavaFX Application Thread
                Platform.runLater(() -> {
                    System.out.println("Dialog thread work done. Proceeding...");
                    // You can update UI elements here if needed
                });

            } catch (InterruptedException e) {
                // Handle interruption gracefully
                System.out.println("Dialog thread interrupted.");
            }
        });
    }

    private void nextPage() {
        if (currentPage < introTexts.size() - 1) {
            currentPage++;
            updatePage();
        }
    }

    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            updatePage();
        }
    }

    private void updatePage() {
        // Update the page text
        pageText.setText(introTexts.get(currentPage));
        zurueckButton.setDisable(currentPage == 0);
        nextButton.setDisable(currentPage == introTexts.size() - 1);
        gameStartButton.setVisible(currentPage == introTexts.size() - 1);
    }

    // Start the dialog thread
    public void startDialog() {
        dialogThread.start();
    }

    // Stop the dialog thread
    public void stopDialog() {
        dialogThread.interrupt();
    }

    // Get the next button for external use (e.g., in App)
    public Button getNextButton() {
        return gameStartButton;
    }
}
