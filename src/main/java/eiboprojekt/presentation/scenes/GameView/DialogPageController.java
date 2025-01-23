package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;

public class DialogPageController {
    private App app;
    private DialogPage dp;

    private String currentPartner;

    private Button closeButton, nextButton, backButton, startButton;

    public DialogPageController(App app, String currentPartner) {
        this.app = app;
        this.currentPartner = currentPartner;
        dp = new DialogPage(700, 260, currentPartner, app);

        this.closeButton = dp.getCloseButton();
        this.nextButton = dp.getNextButton();
        this.backButton = dp.getBackButton();
        this.startButton = dp.getStartButton();

        updateDialogText(); // Aktualisiert den Text mit dem ersten Dialog
        initialize();
    }

    public void initialize() {
        // Je nach Partner anderes Level auswählen! funktioniert nicht
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

        // Event-Handler für die Schaltflächen
        closeButton.setOnAction(e -> dp.hide());
        nextButton.setOnAction(e -> nextDialog());
        backButton.setOnAction(e -> previousDialog());
    }

    // Aktualisiert den Dialogtext
    public void updateDialogText() {
        if (!dp.getDialogs().isEmpty() && dp.getCurrentDialogIndex() < dp.getDialogs().size()) {
            String line = dp.getDialogs().get(dp.getCurrentDialogIndex());
            String[] parts = line.split(":", 2);

            if (parts.length == 2) {
                String speaker = parts[0].trim(); // Sprecher extrahieren
                String dialog = parts[1].trim(); // Dialog extrahieren

                dp.getPageText().setText(speaker + ": " + dialog); // Text anzeigen
                // pageText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill:
                // white;");

                updateImages(speaker); // Bild des Sprechers aktualisieren
            } else {
                dp.getPageText().setText(line); // Einzeilige Dialoge ohne Sprecher
            }

            backButton.setDisable(dp.getCurrentDialogIndex() == 0); // "Zurück"-Button deaktivieren, wenn es der erste Dialog
                                                            // ist
            nextButton.setDisable(dp.getCurrentDialogIndex() == dp.getDialogs().size() - 1); // "Weiter"-Button deaktivieren, wenn es
                                                                             // der letzte Dialog ist

            // Start-Button anzeigen, wenn der letzte Dialog erreicht ist
            startButton.setVisible(dp.getCurrentDialogIndex() == dp.getDialogs().size() - 1);

            System.out.println("Current Dialog Index: " + dp.getCurrentDialogIndex()); // Debugging-Information
            // System.out.println("Current Dialog: " + line); // Debugging-Information
        }
    }

    // Aktualisiert die Bilder des Sprechers
    public void updateImages(String speaker) {
        try {
            dp.loadCharacterImages(); // Lädt die Charakterbilder

            ColorAdjust darkerEffect = new ColorAdjust(0, 0, -0.5, 0); // Dunklerer Effekt für inaktive Charaktere

            if (speaker.equalsIgnoreCase("Timmy")) {
                dp.setImagesVisibility(true, false); // Timmy spricht
                dp.getRightImageIdle().setEffect(darkerEffect); // Partnerbild dunkeln
            } else if (speaker.equalsIgnoreCase(currentPartner)) {
                dp.setImagesVisibility(false, true); // Partner spricht
                dp.getLeftImageIdle().setEffect(darkerEffect); // Timmy-Bild dunkeln
            }
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            dp.setImagesVisibility(false, false); // Alle Bilder ausblenden bei einem Fehler
        }
    }

    // Nächsten Dialog anzeigen
    public void nextDialog() {
        if (dp.getCurrentDialogIndex() < dp.getDialogs().size() - 1) {
            dp.setCurrentDialogIndex(dp.getCurrentDialogIndex()+1);
            updateDialogText();
        }
    }

    // Vorherigen Dialog anzeigen
    public void previousDialog() {
        if (dp.getCurrentDialogIndex() > 0) {
            dp.setCurrentDialogIndex(dp.getCurrentDialogIndex()-1);
            updateDialogText();
        }
    }

    // Setzt den aktuellen Partner und lädt dessen Dialoge
    public void setCurrentPartner(String partnerName) {
        dp.setCurrentPartner(partnerName); // Partnername aktualisieren
        dp.loadDialogs(partnerName + "_dialog.txt"); // Dialoge für den neuen Partner laden
        resetDialog(); // Dialog zurücksetzen und den ersten Dialogtext anzeigen
    }

    // Setzt den Dialog auf den Anfang zurück
    public void resetDialog() {
        dp.setCurrentDialogIndex(0); // Index zurücksetzen
        updateDialogText(); // Text und Schaltflächen aktualisieren
    }

    public DialogPage getDp() {
        return dp;
    }

    public void setDp(DialogPage dp) {
        this.dp = dp;
    }
}