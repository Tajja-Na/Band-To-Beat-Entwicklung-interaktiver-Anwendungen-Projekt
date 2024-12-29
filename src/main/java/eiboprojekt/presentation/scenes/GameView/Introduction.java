package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class Introduction extends BorderPane {

    private Button gameStartButton;
    private Button weiterButton;
    private Button zurückButton;
    private List<String> introTexts;
    private int currentPage = 0;
    private Text pageText;

    public Introduction(int width, int height) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        introTexts = Arrays.asList(
                "Willkommen zu unserem Spiel!",
                "Hier ist die zweite Seite der Einführung.",
                "Dies ist die dritte und letzte Seite der Einführung.",
                "Bereit zum Spielen?");

        initializeUI();
    }

    private void initializeUI() {
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        pageText = new Text(introTexts.get(currentPage));
        gameStartButton = new Button("Start Game");
        gameStartButton.setVisible(false);

        weiterButton = new Button("Weiter");
        zurückButton = new Button("Zurück");
        zurückButton.setDisable(true);

        HBox navigationBox = new HBox(10);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getChildren().addAll(zurückButton, weiterButton);

        centerBox.getChildren().addAll(pageText, navigationBox, gameStartButton);

        setCenter(centerBox);

        weiterButton.setOnAction(e -> nextPage());
        zurückButton.setOnAction(e -> previousPage());
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
        zurückButton.setDisable(currentPage == 0);
        weiterButton.setDisable(currentPage == introTexts.size() - 1);
        gameStartButton.setVisible(currentPage == introTexts.size() - 1);
    }

    public Button getSwitchButton() {
        return gameStartButton;
    }
}
