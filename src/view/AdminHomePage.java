package view;

import helper.UserSessionHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;

public class AdminHomePage {
        private static AdminHomePage adminHomePage;
        private Scene scene;
        private Label title, text;
        private Button viewAllStaff, pcManagement, jobManagement, transactionHistory, viewAllReport, logout;
        VBox vb;
        HBox hb;
        public static AdminHomePage getInstance() {
//                return adminHomePage = adminHomePage == null ? new AdminHomePage() : adminHomePage;
                return new AdminHomePage();
        }

        public  AdminHomePage() {
                initialize();
                addEventListener();
        }

        private void addEventListener() {
                logout.setOnAction(e -> {
                        LoginPage loginPage = LoginPage.getInstance();
                        UserSessionHelper.getInstance().clear();
                        loginPage.show();
                });

                viewAllStaff.setOnAction(e -> {
                        ViewAllStaffPage viewAllStaffPage = ViewAllStaffPage.getInstance();
                        viewAllStaffPage.show();
                });

                pcManagement.setOnAction(e -> {
                        PCManagementPage pcManagementPage = PCManagementPage.getInstance();
                        pcManagementPage.show();
                });
//
                jobManagement.setOnAction(e -> {
                        JobManagementPage jobManagementPage = JobManagementPage.getInstance();
                        jobManagementPage.show();
                });

                transactionHistory.setOnAction(e -> {
                        TransactionHistoryPage transactionHistoryPage = TransactionHistoryPage.getInstance();
                        transactionHistoryPage.show();
                });

                viewAllReport.setOnAction(e -> {
                        ViewAllReportPage viewAllReportPage = ViewAllReportPage.getInstance();
                        viewAllReportPage.show();
                });
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void initialize() {
                title = new Label("Welcome to Admin Home Page");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                text = new Label("What do you want to do?");
                text.setFont(new Font("Arial", 16));

                viewAllStaff = new Button("View All Staff");

                pcManagement = new Button("PC Management");

                jobManagement = new Button("Job Management");

                transactionHistory = new Button("Transaction History");

                viewAllReport = new Button("View All Report");

                hb = new HBox();
                hb.setSpacing(10);
                hb.setAlignment(Pos.CENTER);

                logout = new Button("Logout");

                vb = new VBox();
                vb.setSpacing(10);
                vb.setAlignment(Pos.CENTER);
                vb.setPadding(new Insets(15, 12, 15, 12));

                hb.getChildren().addAll(viewAllStaff, pcManagement, jobManagement, transactionHistory, viewAllReport);
                vb.getChildren().addAll(title, text, hb, logout);

                scene = new Scene(vb, 800, 600);
        }
}
