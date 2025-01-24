package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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

    private String currentPartner;

    public DialogPage(int width, int height, String partnerName, App app) {
        this.app = app;
        this.currentPartner = partnerName;
        initializeUI(width, height);
        centerDialog(width, height);
        loadDialogs(partnerName + "_dialog.txt");
        getStylesheets()
                .add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());
    }

    public void initializeUI(int width, int height) {
        background = new Rectangle(width, height);
        background.setFill(Color.web("#2b2828"));
        background.setArcWidth(15);
        background.setArcHeight(15);
        background.setStroke(Color.WHITE);
        background.setStrokeWidth(3);

        StackPane imageContainer = new StackPane();
        imageContainer.setAlignment(Pos.CENTER);

        leftImageIdle = new ImageView();
        leftImageTalking = new ImageView();
        rightImageIdle = new ImageView();
        rightImageTalking = new ImageView();

        configureImageViews();

        imageContainer.getChildren().addAll(leftImageIdle, leftImageTalking, rightImageIdle, rightImageTalking);
        imageContainer.setTranslateY(-height / 2 - 85);

        HBox textBox = createTextBox();

        HBox buttonBox = createButtonBox();

        startButton = new Button("Start Game");
        startButton.setVisible(false);

        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);

        dialogContent.getChildren().addAll(textBox, buttonBox, startButton);

        getChildren().addAll(background, dialogContent, imageContainer);
    }

    private void configureImageViews() {
        for (ImageView iv : new ImageView[] { leftImageIdle, leftImageTalking, rightImageIdle, rightImageTalking }) {
            iv.setFitWidth(170);
            iv.setFitHeight(170);
            iv.setPreserveRatio(true);
            iv.setVisible(false);
        }

        leftImageIdle.setTranslateX(-200);
        leftImageTalking.setTranslateX(-200);
        rightImageIdle.setTranslateX(200);
        rightImageTalking.setTranslateX(200);
    }

    private HBox createTextBox() {
        HBox textBox = new HBox(10);
        textBox.setAlignment(Pos.BASELINE_CENTER);
        textBox.setPadding(new Insets(30, 0, 30, 0));
        textBox.setMinWidth(1000);
        textBox.setMinHeight(100);

        pageText = new Text("");
        pageText.setStyle("-fx-fill: white; -fx-font-size: 20px;");

        textFlow = new TextFlow(pageText);
        textFlow.setMaxSize(600, 250);
        textFlow.setPrefSize(600, 250);
        textBox.getChildren().add(textFlow);

        return textBox;
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        closeButton = new Button("X");
        nextButton = new Button(">");
        backButton = new Button("<");
        backButton.setDisable(true);

        buttonBox.getChildren().addAll(backButton, closeButton, nextButton);

        return buttonBox;
    }

    public void centerDialog(int width, int height) {
        setLayoutX((app.screenWidth - width) / 2 + 350);
        setLayoutY((app.screenHeight - height) / 2 + 300);
        setPrefSize(width, height);
    }

    public void loadDialogs(String filePath) {
        dialogs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DIALOG_PATH + filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dialogs.add(line.trim());
            }
            if (dialogs.isEmpty()) {
                dialogs.add("No dialogs found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCharacterImages() throws Exception {
        Image timmyIdle = loadImage(CHARACTER_PATH + "Charakter1/timmy1.png");
        Image timmyTalking = loadImage(CHARACTER_PATH + "Charakter1/timmy2.png");
        Image partnerIdle = loadImage(CHARACTER_PATH + currentPartner + "/idle.png");
        Image partnerTalking = loadImage(CHARACTER_PATH + currentPartner + "/talking.png");

        leftImageIdle.setImage(timmyIdle);
        leftImageTalking.setImage(timmyTalking);
        rightImageIdle.setImage(partnerIdle);
        rightImageTalking.setImage(partnerTalking);

    }

    public Image loadImage(String path) throws Exception {
        File file = new File(path);
        if (!file.exists())
            throw new IOException("File not found: " + path);
        return new Image(file.toURI().toString());
    }

    public void setImagesVisibility(boolean timmyTalkingVisible, boolean partnerTalkingVisible) {
        leftImageIdle.setVisible(!timmyTalkingVisible);
        leftImageTalking.setVisible(timmyTalkingVisible);

        rightImageIdle.setVisible(!partnerTalkingVisible);
        rightImageTalking.setVisible(partnerTalkingVisible);
    }

    public void setCurrentPartner(String partnerName) {
        this.currentPartner = partnerName;
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

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
