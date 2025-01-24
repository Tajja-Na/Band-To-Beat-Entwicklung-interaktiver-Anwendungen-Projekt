package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Introduction extends BorderPane {

    private Button gameStartButton;
    private Button weiterButton;
    private Button zurueckButton;

    private List<String> introTexts;
    private int currentPage = 0;

    private Text pageText;
    private TextFlow textFlow;

    public Introduction(int width, int height) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        getStylesheets()
                .add(getClass().getResource("/eiboprojekt/presentation/scenes/GameView/style.css").toExternalForm());

        introTexts = Arrays.asList(
                "Dein Name ist Timmy und dein Traum war es schon immer eine Band zu gründen. Aber das muss ich dir ja nicht sagen, das weißt du selbst, denn du bist ja Timmy. Deine Mission ist es Bandmitglieder zu finden und diese von deinem Talent zu überzeugen, damit sie sich dir anschließen! Das wird nicht immer leicht sein, aber du bist ja auch nicht der Typ, der schnell aufgibt. \n",
                "Also los, gehe hinaus in die Welt und suche deine Teammates! Sei nicht schüchtern und spreche sie an! (Wie im echten Leben einfach e drücken, wenn du in der Nähe bist.) Vielleicht wollen sie nicht direkt mit dir reden, aber dann bring ihnen doch ein cooles Instrument, vielleicht lockert das die Stimmung. Dann steht dir nichts mehr im Weg, mit ein wenig Geschick und Taktgefühl hast du sicher im Handumdrehen deine Band beisammen! \nLet's goooooo Timmy!");

        initializeUI();
    }

    private void initializeUI() {

        HBox imageBox = new HBox(10);
        imageBox.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(
                    new Image(new File("assets/Welcome/member" + i + ".png").toURI().toString()));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(100);

            if (i == 0) {
                Text caption = new Text("Das bist du! Timmy!!");
                caption.setStyle("-fx-fill: white;");
                VBox imageWithCaption = new VBox(8, imageView, caption);
                imageWithCaption.setAlignment(Pos.TOP_CENTER);
                imageBox.getChildren().add(imageWithCaption);
            } else {
                imageBox.getChildren().add(imageView);
            }
        }

        // Textbox und Navigationsbuttons
        VBox textBox = new VBox(10);
        textBox.setAlignment(Pos.CENTER);
        textBox.setPadding(new Insets(0, 100, 0, 100));
        textBox.setMaxWidth(800);
        textBox.getStyleClass().add("text-box");

        pageText = new Text(introTexts.get(currentPage));
        pageText.setStyle("-fx-fill: white;");
        textFlow = new TextFlow(pageText);
        textFlow.setPrefWidth(600);
        textFlow.setPadding(new Insets(20, 20, 20, 20));
        textFlow.setStyle("-fx-text-fill: white;");

        textBox.setPadding(new Insets(0, 100, 0, 100));
        textBox.getChildren().add(textFlow);

        HBox navigationBox = new HBox(10);
        navigationBox.setAlignment(Pos.CENTER);

        weiterButton = new Button("Weiter");
        weiterButton.getStyleClass().add("button");
        zurueckButton = new Button("Zurück");
        zurueckButton.setDisable(true);
        zurueckButton.getStyleClass().add("button");
        gameStartButton = new Button("Start Game");
        gameStartButton.setVisible(false);
        gameStartButton.getStyleClass().add("button");

        navigationBox.getChildren().addAll(zurueckButton, weiterButton);

        // Alles zusammen in mainBox:
        VBox mainBox = new VBox(50);

        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(imageBox, textBox, navigationBox, gameStartButton);
        mainBox.getStyleClass().add("main-box");

        setCenter(mainBox);
    }

    public Button getSwitchButton() {
        return gameStartButton;
    }

    public Button getWeiterButton() {
        return weiterButton;
    }

    public Button getZurueckButton() {
        return zurueckButton;
    }

    public Button getGameStartButton() {
        return gameStartButton;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<String> getIntroTexts() {
        return introTexts;
    }

    public void setIntroTexts(List<String> introTexts) {
        this.introTexts = introTexts;
    }

    public Text getPageText() {
        return pageText;
    }

    public void setPageText(Text pageText) {
        this.pageText = pageText;
    }
}
