package eiboprojekt.presentation.scenes.GameView;

import eiboprojekt.App;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EndeView extends BorderPane {
    App app;
    int height;
    int width;
    VBox endScene;

    public EndeView(App app) {
        this.width = app.screenWidth;
        this.height = app.screenHeight;

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        endScene = new VBox(40);
        endScene.setAlignment(Pos.CENTER);
        endScene.setPrefSize(width, height);

        endScene.setStyle("-fx-background-image: url(\"file:assets/background/Test_Ende_.png\");"
                + "-fx-background-size: cover;");

        setCenter(endScene);
    }
}
