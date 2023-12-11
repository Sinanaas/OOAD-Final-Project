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
		return registerPage = registerPage == null ? new RegisterPage() : registerPage;
	}

	private RegisterPage() {
		initialize();
		addEventListener();
	}

	private Scene scene;
	private VBox vb;
	private HBox radioButtonHBox;
	private Label registerTitle, usernameTitle, emailTitle, passwordTitle, genderTitle;
	private TextField usernameInput, emailInput;
	private PasswordField passwordInput;
	private ToggleGroup genderToggleGroup;
	private RadioButton maleRadioButton, femaleRadioButton;
	private Button registerButton;
	private Hyperlink loginHyperlink;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		vb = new VBox(10);
		registerTitle = new Label("Register");
		usernameTitle = new Label("Username");
		emailTitle = new Label("Email");
		passwordTitle = new Label("Password");
		genderTitle = new Label("Gender");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");
		emailInput = new TextField();
		emailInput.setPromptText("Input your email here");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");

		radioButtonHBox = new HBox(10);
		genderToggleGroup = new ToggleGroup();
		maleRadioButton = new RadioButton("Male");
		femaleRadioButton = new RadioButton("Female");
		maleRadioButton.setToggleGroup(genderToggleGroup);
		femaleRadioButton.setToggleGroup(genderToggleGroup);
		radioButtonHBox.getChildren().addAll(maleRadioButton, femaleRadioButton);

		registerButton = new Button("Register");
		loginHyperlink = new Hyperlink("Already have an account? Click here to login!");
		vb.getChildren().addAll(registerTitle, usernameTitle, usernameInput, emailTitle, emailInput, passwordTitle,
				passwordInput, genderTitle, radioButtonHBox, registerButton, loginHyperlink);

		registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}

	private void addEventListener() {
		registerButton.setOnMouseClicked(e -> {
			String username = usernameInput.getText();
			String userEmail = emailInput.getText();
			String userPassword = passwordInput.getText();
			String userGender = ((RadioButton) genderToggleGroup.getSelectedToggle()).getText();
			
			if(UserController.register(username, userEmail, userPassword, userGender)) {
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
