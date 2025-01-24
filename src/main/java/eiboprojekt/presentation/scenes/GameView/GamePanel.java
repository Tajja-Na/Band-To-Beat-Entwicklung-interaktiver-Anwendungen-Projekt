package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Entity.TextBubble;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class GamePanel extends BorderPane {
    private App app;
    private final Canvas canvas;

    private DialogPageController dpController;
    private TextBubble warnung = new TextBubble("Achtung der Spielstand wird nicht gespeichert!", 275, 50);
    private boolean showWarning = true;
    private TextBubble instrumentWarnung = new TextBubble("", 300, 50);
    private boolean showTextBubble = false;

    public GamePanel(App app) {
        this.app = app;
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

    public TextBubble getInstrumentWarnung() {
        return instrumentWarnung;
    }

    public DialogPageController getDpController() {
        return dpController;
    }

    public void setDpController(DialogPageController dpController) {
        this.dpController = dpController;
    }
}