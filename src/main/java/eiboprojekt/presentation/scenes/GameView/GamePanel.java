package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.TextBubble;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class GamePanel extends BorderPane{
    private App app;
    private final Canvas canvas;

    // Dialog
    private DialogPage dialogPage; // Referenz für DialogPage

    private TextBubble warnung = new TextBubble("Achtung der Spielstand wird nicht gespeichert!", 275, 50);
    private boolean showWarning = true;  // Zeigt an, ob eine Warnung sichtbar ist
    
    private TextBubble instrumentWarnung = new TextBubble("", 300, 50);
    private boolean showTextBubble = false; // TextBubble wird zu Beginn angezeigt

    public GamePanel(App app){
        this.app = app;
        // Erstelle das Canvas und füge es zum Panel hinzu
        canvas = new Canvas(app.screenWidth, app.screenHeight);
        this.getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public boolean isShowTextBubble() {
        return showTextBubble;
    }

    public boolean isShowWarning() {
        return showWarning;
    }

    public TextBubble getWarnung() {
        return warnung;
    }

    public void setWarnung(TextBubble warnung) {
        this.warnung = warnung;
    }

    public void setShowWarning(boolean showWarning) {
        this.showWarning = showWarning;
    }

    public void setInstrumentWarnung(TextBubble instumentWarnung) {
        this.instrumentWarnung = instumentWarnung;
    }

    public void setShowTextBubble(boolean showTextBubble) {
        this.showTextBubble = showTextBubble;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public DialogPage getDialogPage() {
        return dialogPage;
    }

    public void setDialogPage(DialogPage dialogPage) {
        this.dialogPage = dialogPage;
    }

    public TextBubble getInstrumentWarnung() {
        return instrumentWarnung;
    }
}