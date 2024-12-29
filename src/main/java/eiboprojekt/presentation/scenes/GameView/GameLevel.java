package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameLevel extends BorderPane {
    public GameLevel(int width, int height) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        initializeUI();
    }

    private void initializeUI() {
        VBox centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);

        Text test = new Text("AHHHHHH");
        test.setStyle("-fx-fill: white; -fx-font-size: 88px;");

        centerBox.getChildren().addAll(test);

        setCenter(centerBox);
        setStyle("-fx-background-color:rgb(0, 85, 255)");
    }

}
