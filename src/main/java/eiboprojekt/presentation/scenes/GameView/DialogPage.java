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

    private GamePanel gp;
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
    public DialogPage(int width, int height, GamePanel gp, String partnerName, App app) {
        this.app = app;
        this.gp = gp;
        this.currentPartner = partnerName; // Setzt den Dialogpartner
        initializeUI(width, height); // UI-Elemente initialisieren
        centerDialog(width, height); // Positioniert das Dialogfenster
        loadDialogs(partnerName + "_dialog.txt"); // Lädt den Dialog des Partners
        updateDialogText(); // Aktualisiert den Text mit dem ersten Dialog
        getStylesheets()
                .add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());
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

        //Je nach Partner anderes Level auswählen!   funktioniert nicht 
        switch (currentPartner) {
            case "Gigi":
                startButton.setOnAction(e -> app.switchView("GAMELevel1"));
                break;
            case "Ryu":
                startButton.setOnAction(e -> app.switchView("GAMELevel2"));
                break;
            case "Tyler":
                startButton.setOnAction(e -> app.switchView("GAMELevel3"));
                break;
        }

        //startButton.setOnAction(e -> app.switchView("GAMELevel3"));

        // VBox für alle Dialog-Elemente
        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);
        // dialogContent.minHeight(height);
        // dialogContent.minWidth(width);
        dialogContent.getChildren().addAll(textBox, buttonBox, startButton);
        // dialogContent.getStyleClass().add("main-box");

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

        // Event-Handler für die Schaltflächen
        closeButton.setOnAction(e -> hide());
        nextButton.setOnAction(e -> nextDialog());
        backButton.setOnAction(e -> previousDialog());

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

    // Aktualisiert den Dialogtext
    public void updateDialogText() {
        if (!dialogs.isEmpty() && currentDialogIndex < dialogs.size()) {
            String line = dialogs.get(currentDialogIndex);
            String[] parts = line.split(":", 2);

            if (parts.length == 2) {
                String speaker = parts[0].trim(); // Sprecher extrahieren
                String dialog = parts[1].trim(); // Dialog extrahieren

                pageText.setText(speaker + ": " + dialog); // Text anzeigen
                // pageText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill:
                // white;");

                updateImages(speaker); // Bild des Sprechers aktualisieren
            } else {
                pageText.setText(line); // Einzeilige Dialoge ohne Sprecher
            }

            backButton.setDisable(currentDialogIndex == 0); // "Zurück"-Button deaktivieren, wenn es der erste Dialog
                                                            // ist
            nextButton.setDisable(currentDialogIndex == dialogs.size() - 1); // "Weiter"-Button deaktivieren, wenn es
                                                                             // der letzte Dialog ist

            // Start-Button anzeigen, wenn der letzte Dialog erreicht ist
            startButton.setVisible(currentDialogIndex == dialogs.size() - 1);

            System.out.println("Current Dialog Index: " + currentDialogIndex); // Debugging-Information
            // System.out.println("Current Dialog: " + line); // Debugging-Information
        }
    }

    // Aktualisiert die Bilder des Sprechers
    public void updateImages(String speaker) {
        try {
            loadCharacterImages(); // Lädt die Charakterbilder

            ColorAdjust darkerEffect = new ColorAdjust(0, 0, -0.5, 0); // Dunklerer Effekt für inaktive Charaktere

            if (speaker.equalsIgnoreCase("Timmy")) {
                setImagesVisibility(true, false); // Timmy spricht
                rightImageIdle.setEffect(darkerEffect); // Partnerbild dunkeln
            } else if (speaker.equalsIgnoreCase(currentPartner)) {
                setImagesVisibility(false, true); // Partner spricht
                leftImageIdle.setEffect(darkerEffect); // Timmy-Bild dunkeln
            }
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            setImagesVisibility(false, false); // Alle Bilder ausblenden bei einem Fehler
        }
    }

    // Lädt die Bilder für Timmy und den Partner
    private void loadCharacterImages() throws Exception {
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
    private Image loadImage(String path) throws Exception {
        File file = new File(path);
        if (!file.exists())
            throw new IOException("File not found: " + path);
        return new Image(file.toURI().toString());
    }

    // Setzt die Sichtbarkeit der Bilder
    private void setImagesVisibility(boolean timmyTalkingVisible, boolean partnerTalkingVisible) {
        leftImageIdle.setVisible(!timmyTalkingVisible);
        leftImageTalking.setVisible(timmyTalkingVisible);

        rightImageIdle.setVisible(!partnerTalkingVisible);
        rightImageTalking.setVisible(partnerTalkingVisible);
    }

    // Nächsten Dialog anzeigen
    public void nextDialog() {
        if (currentDialogIndex < dialogs.size() - 1) {
            currentDialogIndex++;
            updateDialogText();
        }
    }

    // Vorherigen Dialog anzeigen
    public void previousDialog() {
        if (currentDialogIndex > 0) {
            currentDialogIndex--;
            updateDialogText();
        }
    }

    // Setzt den aktuellen Partner und lädt dessen Dialoge
    public void setCurrentPartner(String partnerName) {
        this.currentPartner = partnerName; // Partnername aktualisieren
        loadDialogs(partnerName + "_dialog.txt"); // Dialoge für den neuen Partner laden
        resetDialog(); // Dialog zurücksetzen und den ersten Dialogtext anzeigen
    }

    // Setzt den Dialog auf den Anfang zurück
    public void resetDialog() {
        currentDialogIndex = 0; // Index zurücksetzen
        updateDialogText(); // Text und Schaltflächen aktualisieren
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
}
