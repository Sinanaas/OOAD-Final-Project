package main;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage {

	private Stage stage;

	private static MainStage mainStage;

	public static MainStage getInstance() {
		return mainStage = mainStage == null ? new MainStage() : mainStage;
	}

	private MainStage() {
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	public void setScene(Scene scene) {
		if (stage != null) {
			stage.setScene(scene);
		}
	}

	public void closeStage() {
		if (stage != null) {
			stage.close();
		}
	}
}
