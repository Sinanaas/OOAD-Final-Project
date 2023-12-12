package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Report;
import view.BookPCPage;
import view.LoginPage;
import view.ReportPage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override 
	public void start(Stage primaryStage) throws Exception {
		MainStage mainStage = MainStage.getInstance();
		mainStage.setStage(primaryStage);
		
		LoginPage loginPage = LoginPage.getInstance();
		loginPage.show();

//		ReportPage reportPage = ReportPage.getInstance();
//		reportPage.show();

		mainStage.getStage().setTitle("session10OOAD");
		mainStage.getStage().setResizable(false);
		mainStage.getStage().show();
	}
}
