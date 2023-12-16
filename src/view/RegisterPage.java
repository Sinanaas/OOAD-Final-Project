package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;

public class RegisterPage {

	private static RegisterPage registerPage;

	public static RegisterPage getInstance() {
//		return registerPage = registerPage == null ? new RegisterPage() : registerPage;
		return new RegisterPage();
	}

	private RegisterPage() {
		initialize();
		addEventListener();
	}

	private Scene scene;
	private VBox vb;
	private Label registerTitle, usernameTitle, passwordTitle, confirmPasswordTitle, ageTitle;
	private TextField usernameInput, ageInput;
	private PasswordField passwordInput, confirmPasswordInput;
	private Button registerButton;
	private Hyperlink loginHyperlink;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		vb = new VBox(10);

		// register
		registerTitle = new Label("Register");
		registerButton = new Button("Register");

		// username
		usernameTitle = new Label("Username");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");

		// password
		passwordTitle = new Label("Password");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");

		// confirm password
		confirmPasswordTitle = new Label("Confirm Password");
		confirmPasswordInput = new PasswordField();
		confirmPasswordInput.setPromptText("Input your password here");

		// age
		ageTitle = new Label("Age");
		ageInput = new TextField();
		ageInput.setPromptText("Input your age here");

		loginHyperlink = new Hyperlink("Already have an account? Click here to login!");
		vb.getChildren().addAll(registerTitle, usernameTitle, usernameInput, passwordTitle,
				passwordInput, confirmPasswordTitle, confirmPasswordInput, ageTitle, ageInput, registerButton, loginHyperlink);

		registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}


	private void addEventListener() {
		registerButton.setOnMouseClicked(e -> {
			String username = usernameInput.getText();
			String userPassword = passwordInput.getText();
			String userConfirmPassword = confirmPasswordInput.getText();
			Integer userAge = ageInput.getText().isEmpty() ? 0 : Integer.parseInt(ageInput.getText());

			if(UserController.addNewUser(username, userPassword, userAge, userConfirmPassword)) {
				LoginPage loginPage = LoginPage.getInstance();
				loginPage.show();
			}
		});

		loginHyperlink.setOnAction(e -> {
			LoginPage loginPage = LoginPage.getInstance();
			loginPage.show();
		});
	}

}
