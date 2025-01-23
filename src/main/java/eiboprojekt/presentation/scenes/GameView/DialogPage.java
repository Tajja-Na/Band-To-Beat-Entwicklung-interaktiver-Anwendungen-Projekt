package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eiboprojekt.App;

public class DialogPage extends StackPane {
    // Konstanten für den Pfad zu den Ressourcen
    private static final String ASSETS_PATH = "assets/";
    private static final String DIALOG_PATH = ASSETS_PATH + "Dialog/";
    private static final String CHARACTER_PATH = ASSETS_PATH + "Character/";

    private App app;
    private Rectangle background;
    private Button closeButton, nextButton, backButton, startButton;

    private TextFlow textFlow;
    private Text pageText;
    private List<String> dialogs;
    private int currentDialogIndex = 0;

    private ImageView leftImageIdle, leftImageTalking, rightImageIdle, rightImageTalking;

    private String currentPartner; // Aktueller Dialogpartner

    // Konstruktor, der die Dialogseite mit der Partnerbezeichnung initialisiert
    public DialogPage(int width, int height, String partnerName, App app) {
        this.app = app;
        this.currentPartner = partnerName; // Setzt den Dialogpartner
        initializeUI(width, height); // UI-Elemente initialisieren
        centerDialog(width, height); // Positioniert das Dialogfenster
        loadDialogs(partnerName + "_dialog.txt"); // Lädt den Dialog des Partners
        getStylesheets().add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());
    }

    // Initialisiert alle UI-Komponenten
    public void initializeUI(int width, int height) {
        // Hintergrundrechteck erstellen
        background = new Rectangle(width, height);
        background.setFill(Color.web("#2b2828")); // Hintergrundfarbe auf #2b2828 setzen
        background.setArcWidth(15); // Abgerundete Ecken
        background.setArcHeight(15);
        background.setStroke(Color.WHITE); // Setzt den Rahmen auf Weiß
        background.setStrokeWidth(3); // Breite des Rahmens (z.B. 3 Pixel)

        // StackPane für die Bilder der Charaktere
        StackPane imageContainer = new StackPane();
        imageContainer.setAlignment(Pos.CENTER);

        // Initialisieren der ImageViews für die Charaktere
        leftImageIdle = new ImageView();
        leftImageTalking = new ImageView();
        rightImageIdle = new ImageView();
        rightImageTalking = new ImageView();

        configureImageViews(); // Bild-Views konfigurieren

        // Die Bilder in den Container einfügen
        imageContainer.getChildren().addAll(leftImageIdle, leftImageTalking, rightImageIdle, rightImageTalking);
        imageContainer.setTranslateY(-height / 2 - 85); // Position der Bilder anpassen

        // Textbox für den Dialog erstellen
        HBox textBox = createTextBox();

        // Button-Box für Navigation
        HBox buttonBox = createButtonBox();

        // Der Start-Button (wird zunächst verborgen)
        startButton = new Button("Start Game");
        startButton.setVisible(false); // Anfangs unsichtbar

        // VBox für alle Dialog-Elemente
        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);

        dialogContent.getChildren().addAll(textBox, buttonBox, startButton);

        // Alle Komponenten zum Dialog hinzufügen
        getChildren().addAll(background, dialogContent, imageContainer);
    }

    // Konfiguriert die Bild-Views (z.B. Größe, Sichtbarkeit)
    private void configureImageViews() {
        // Alle Bild-Views konfigurieren
        for (ImageView iv : new ImageView[] { leftImageIdle, leftImageTalking, rightImageIdle, rightImageTalking }) {
            iv.setFitWidth(170);
            iv.setFitHeight(170);
            iv.setPreserveRatio(true); // Erhält das Seitenverhältnis
            iv.setVisible(false); // Zu Beginn unsichtbar
        }

        // Position der Bilder anpassen
        leftImageIdle.setTranslateX(-200);
        leftImageTalking.setTranslateX(-200);
        rightImageIdle.setTranslateX(200);
        rightImageTalking.setTranslateX(200);
    }

    // Erstellt die Textbox, in der der Dialog angezeigt wird
    private HBox createTextBox() {
        HBox textBox = new HBox(10);
        textBox.setAlignment(Pos.BASELINE_CENTER);
        textBox.setPadding(new Insets(30, 0, 30, 0));
        textBox.setMinWidth(1000);
        textBox.setMinHeight(100); // Feste Höhe für die Textbox

        pageText = new Text("");
        pageText.setStyle("-fx-fill: white; -fx-font-size: 20px;"); // Stil des
        // Textes

        textFlow = new TextFlow(pageText);
        textFlow.setMaxSize(600, 250); // Maximal erlaubte Breite und Höhe
        textFlow.setPrefSize(600, 250); // Feste Breite und Höhe
        textBox.getChildren().add(textFlow);

        return textBox;
    }

    // Erstellt die Schaltflächen für die Navigation im Dialog
    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        closeButton = new Button("X");
        nextButton = new Button(">");
        // nextButton.getStyleClass().add("button");

        backButton = new Button("<");
        // (backButton.getStyleClass().add("button");

        backButton.setDisable(true); // Der "Zurück"-Button ist am Anfang deaktiviert

        buttonBox.getChildren().addAll(backButton, closeButton, nextButton);

        return buttonBox;
    }

    // Zentriert das Dialogfenster auf dem Bildschirm
    public void centerDialog(int width, int height) {
        setLayoutX((app.screenWidth - width) / 2 + 350); // Horizontal zentrieren
        setLayoutY((app.screenHeight - height) / 2 + 300); // Vertikal zentrieren
        setPrefSize(width, height); // Fixiere die Größe der gesamten DialogBox
    }

    // Lädt den Dialog aus einer Datei
    public void loadDialogs(String filePath) {
        dialogs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DIALOG_PATH + filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dialogs.add(line.trim()); // Entfernt Leerzeichen und fügt den Dialog hinzu
            }
            if (dialogs.isEmpty()) {
                dialogs.add("No dialogs found."); // Falls keine Dialoge gefunden werden
            }

            System.out.println("Dialogs loaded: " + dialogs.size());

        } catch (IOException e) {
            e.printStackTrace();
            dialogs.add("Error loading dialogs.");
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        }
    }

    // Lädt die Bilder für Timmy und den Partner
    public void loadCharacterImages() throws Exception {
        Image timmyIdle = loadImage(CHARACTER_PATH + "Charakter1/timmy1.png");
        Image timmyTalking = loadImage(CHARACTER_PATH + "Charakter1/timmy2.png");
        Image partnerIdle = loadImage(CHARACTER_PATH + currentPartner + "/idle.png");
        Image partnerTalking = loadImage(CHARACTER_PATH + currentPartner + "/talking.png");

        // Setzt die Bilder in die entsprechenden ImageViews
        leftImageIdle.setImage(timmyIdle);
        leftImageTalking.setImage(timmyTalking);
        rightImageIdle.setImage(partnerIdle);
        rightImageTalking.setImage(partnerTalking);

        System.out.println("Current Partner: " + currentPartner); // Debugging-Information
    }

    // Hilfsmethode zum Laden eines Bildes
    public Image loadImage(String path) throws Exception {
        File file = new File(path);
        if (!file.exists())
            throw new IOException("File not found: " + path);
        return new Image(file.toURI().toString());
    }

    // Setzt die Sichtbarkeit der Bilder
    public void setImagesVisibility(boolean timmyTalkingVisible, boolean partnerTalkingVisible) {
        leftImageIdle.setVisible(!timmyTalkingVisible);
        leftImageTalking.setVisible(timmyTalkingVisible);

        rightImageIdle.setVisible(!partnerTalkingVisible);
        rightImageTalking.setVisible(partnerTalkingVisible);
    }

    public void setCurrentPartner(String partnerName) {
        this.currentPartner = partnerName; // Partnername aktualisieren
    }

    // Zeigt das Dialogfenster an
    public void show() {
        setVisible(true);
    }

    // Versteckt das Dialogfenster
    public void hide() {
        setVisible(false);
    }

    // Gibt den Start-Button zurück
    public Button getSwitchButton() {
        return startButton;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getStartButton() {
        return startButton;
    }

    public int getCurrentDialogIndex() {
        return currentDialogIndex;
    }

    public void setCurrentDialogIndex(int currentDialogIndex) {
        this.currentDialogIndex = currentDialogIndex;
    }

    public List<String> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<String> dialogs) {
        this.dialogs = dialogs;
    }

    public ImageView getLeftImageIdle() {
        return leftImageIdle;
    }

    public void setLeftImageIdle(ImageView leftImageIdle) {
        this.leftImageIdle = leftImageIdle;
    }

    public ImageView getLeftImageTalking() {
        return leftImageTalking;
    }

    public void setLeftImageTalking(ImageView leftImageTalking) {
        this.leftImageTalking = leftImageTalking;
    }

    public ImageView getRightImageIdle() {
        return rightImageIdle;
    }

    public void setRightImageIdle(ImageView rightImageIdle) {
        this.rightImageIdle = rightImageIdle;
    }

    public ImageView getRightImageTalking() {
        return rightImageTalking;
    }

    public void setRightImageTalking(ImageView rightImageTalking) {
        this.rightImageTalking = rightImageTalking;
    }

    public Text getPageText() {
        return pageText;
    }

    public void setPageText(Text pageText) {
        this.pageText = pageText;
    }
}
