package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;

public class LoginPage {
	private static LoginPage loginPage;
	
	public static LoginPage getInstance() {
		return loginPage = loginPage == null ? new LoginPage() : loginPage;
	}
	
	private LoginPage() {
		initialize();
		addEventListener();
	}

	private Scene scene;
	private VBox vb;
	private Label loginTitle, usernameTitle, passwordTitle;
	private TextField usernameInput;
	private PasswordField passwordInput;
	private Button loginButton;
	private Hyperlink registerHyperlink;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		vb = new VBox(10);
		loginTitle = new Label("Login");
		usernameTitle = new Label("Username");
		passwordTitle = new Label("Password");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");
		loginButton = new Button("Login");
		registerHyperlink = new Hyperlink("Don't have an account? Register Here!");
		vb.getChildren().addAll(loginTitle, usernameTitle, usernameInput, passwordTitle, passwordInput, loginButton,
				registerHyperlink);
		loginTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}

	private void addEventListener() {
		loginButton.setOnMouseClicked(e -> {
			String username = usernameInput.getText();
			String password = passwordInput.getText();
			if (UserController.login(username, password)) {
				UserHomePage userHomePage = UserHomePage.getInstance();
				userHomePage.show();
			}

		});

		registerHyperlink.setOnAction(e -> {
			RegisterPage registerPage = RegisterPage.getInstance();
			registerPage.show();
		});
	}

}
