package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.MainStage;

public class UserHomePage {
    private static UserHomePage userHomePage;
    private Scene scene;
    private VBox vb;
    public static UserHomePage getInstance() {
        return userHomePage = userHomePage == null ? new UserHomePage() : userHomePage;
    }

    private UserHomePage() {
        initialize();
        addEventListener();
    }

    private void addEventListener() {
    }

    private void initialize() {
        Label label = new Label("User Home Page");
        vb = new VBox(10);
        vb.getChildren().add(label);

        scene = new Scene(vb, 500, 500);
    }

    public void show() {
        MainStage mainStage = MainStage.getInstance();
        mainStage.getStage().setScene(scene);
    }
}
