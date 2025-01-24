package eiboprojekt.presentation.scenes.GameView;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Welcome extends BorderPane {

    Button switchButton;

    public Welcome(int width, int height) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        initializeUI();
    }

    private void initializeUI() {
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.TOP_CENTER);

        // Logo
        ImageView logoView = new ImageView(new Image(new File("assets/Welcome/Logo.png").toURI().toString()));
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(750); // Breite des Logos
        // Button
        switchButton = new Button("Press Start!");
        switchButton.getStyleClass().add("start-button");

        centerBox.getChildren().addAll(logoView, switchButton);

        // Background Image
        ImageView backgroundImage = new ImageView(
                new Image(new File("assets/Welcome/timmy_detail_ansicht_mund_zu.png").toURI().toString()));
        backgroundImage.setOpacity(0.4);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitWidth(280);
        backgroundImage.setStyle("-fx-cursor: hand;");
        StackPane.setAlignment(backgroundImage, Pos.BOTTOM_RIGHT);

        // Main Layout
        StackPane mainPane = new StackPane(backgroundImage, centerBox);
        mainPane.getStyleClass().add("view");
        setCenter(mainPane);
    }
}