package eiboprojekt.presentation.scenes.GameView;

import java.util.Arrays;
import java.util.List;

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
    private Button gameStartButton; // Initialize this button
    private Text pageText;
    private List<String> introTexts;
    private int currentPage = 0;

    public DialogPage() {
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
        gameStartButton = new Button("Spiel starten");

        // Set styles
        setStyle("-fx-background-color: #282c34;");
        pageText.setStyle("-fx-fill: white; -fx-font-size: 18px;");
        nextButton.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");
        zurueckButton.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");
        gameStartButton.setStyle("-fx-font-size: 16px; -fx-background-color: #61dafb; -fx-text-fill: black;");

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
        centerBox.getChildren().addAll(pageText, navigationBox, gameStartButton);

        // Add center box to the center of the BorderPane
        setCenter(centerBox);

        // Button actions
        nextButton.setOnAction(e -> nextPage());
        zurueckButton.setOnAction(e -> previousPage());
        // gameStartButton.setOnAction(e -> startGame());
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
        pageText.setText(introTexts.get(currentPage));
        zurueckButton.setDisable(currentPage == 0);
        nextButton.setDisable(currentPage == introTexts.size() - 1);
        gameStartButton.setVisible(currentPage == introTexts.size() - 1);
    }

    // private void startGame() {
    /// app.switchView }

    public Button getNextButton() {
        return gameStartButton;
    }
}