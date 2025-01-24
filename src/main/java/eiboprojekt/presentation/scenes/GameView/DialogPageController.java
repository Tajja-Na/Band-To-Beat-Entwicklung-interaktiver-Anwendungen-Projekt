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

        updateDialogText();
        initialize();
    }

    public void initialize() {
        // Je nach Partner anderes Level auswählen!
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

        closeButton.setOnAction(e -> dp.hide());
        nextButton.setOnAction(e -> nextDialog());
        backButton.setOnAction(e -> previousDialog());
    }

    public void updateDialogText() {
        if (!dp.getDialogs().isEmpty() && dp.getCurrentDialogIndex() < dp.getDialogs().size()) {
            String line = dp.getDialogs().get(dp.getCurrentDialogIndex());
            String[] parts = line.split(":", 2);

            if (parts.length == 2) {
                String speaker = parts[0].trim();
                String dialog = parts[1].trim();

                dp.getPageText().setText(speaker + ": " + dialog);

                updateImages(speaker);
            } else {
                dp.getPageText().setText(line);
            }

            backButton.setDisable(dp.getCurrentDialogIndex() == 0);
            nextButton.setDisable(dp.getCurrentDialogIndex() == dp.getDialogs().size() - 1);

            // Start-Button anzeigen, wenn der letzte Dialog erreicht ist
            startButton.setVisible(dp.getCurrentDialogIndex() == dp.getDialogs().size() - 1);
        }
    }

    public void updateImages(String speaker) {
        try {
            dp.loadCharacterImages();

            ColorAdjust darkerEffect = new ColorAdjust(0, 0, -0.5, 0);

            if (speaker.equalsIgnoreCase("Timmy")) {
                dp.setImagesVisibility(true, false);
                dp.getRightImageIdle().setEffect(darkerEffect);
            } else if (speaker.equalsIgnoreCase(currentPartner)) {
                dp.setImagesVisibility(false, true);
                dp.getLeftImageIdle().setEffect(darkerEffect);
            }
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            dp.setImagesVisibility(false, false);
        }
    }

    public void nextDialog() {
        if (dp.getCurrentDialogIndex() < dp.getDialogs().size() - 1) {
            dp.setCurrentDialogIndex(dp.getCurrentDialogIndex() + 1);
            updateDialogText();
        }
    }

    public void previousDialog() {
        if (dp.getCurrentDialogIndex() > 0) {
            dp.setCurrentDialogIndex(dp.getCurrentDialogIndex() - 1);
            updateDialogText();
        }
    }

    public void setCurrentPartner(String partnerName) {
        dp.setCurrentPartner(partnerName);
        dp.loadDialogs(partnerName + "_dialog.txt");
        resetDialog();
    }

    public void resetDialog() {
        dp.setCurrentDialogIndex(0);
        updateDialogText();
    }

    public DialogPage getDp() {
        return dp;
    }

    public void setDp(DialogPage dp) {
        this.dp = dp;
    }
}