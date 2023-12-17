package view;

import helper.UserSessionHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PC;

public class AdminHomePage {
        private static AdminHomePage adminHomePage;
        private Scene scene;
        private Label title, text;
        private Button viewAllStaff, pcManagement, jobManagement, transactionHistory, viewAllReport, logout;
        VBox vb;
        HBox hb, atas;
        BorderPane bp;
        TableView pcTable;
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
                pcTable = new TableView<>();
                title = new Label("Welcome to Admin Home Page");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                // table
                TableColumn<PC, String> pcIDCol = new TableColumn<>("PC ID");
                TableColumn<PC, String> pcStatusCol = new TableColumn<>("PC Condition");

                pcIDCol.setMinWidth(388);
                pcStatusCol.setMinWidth(380);

                pcIDCol.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                pcStatusCol.setCellValueFactory(new PropertyValueFactory<>("PCCondition"));

                pcTable.getColumns().addAll(pcIDCol, pcStatusCol);
                pcTable.getItems().addAll(PC.getAllPCData());

                text = new Label("What do you want to do?");
                text.setFont(new Font("Arial", 16));

                viewAllStaff = new Button("View All Staff");

                pcManagement = new Button("PC Management");

                jobManagement = new Button("Job Management");

                transactionHistory = new Button("Transaction History");

                viewAllReport = new Button("View All Report");

                hb = new HBox();
                hb.setSpacing(10);
                hb.setAlignment(Pos.CENTER_LEFT);

                logout = new Button("Logout");

                vb = new VBox();
                vb.setSpacing(10);
                vb.setAlignment(Pos.CENTER);
                vb.setPadding(new Insets(15, 12, 15, 12));
                bp = new BorderPane();
                bp.setLeft(title);
                bp.setRight(logout);
                hb.getChildren().addAll(viewAllStaff, pcManagement, jobManagement, transactionHistory, viewAllReport);
                vb.getChildren().addAll(bp, pcTable, hb);

                scene = new Scene(vb, 800, 600);
        }
}
