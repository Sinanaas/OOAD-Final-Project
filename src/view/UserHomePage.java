package view;

import controller.PCBookController;
import controller.PCController;
import controller.TransactionController;
import helper.Helper;
import helper.UserSessionHelper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PC;

public class UserHomePage {
        private static UserHomePage userHomePage;
        private Scene scene;
        private HBox hb, bawah;
        private BorderPane bp;
        private Button reportButton, logoutButton, transactionBtn;
        private TableView pcTable;
        public static UserHomePage getInstance() {
//                return userHomePage = userHomePage == null ? new UserHomePage() : userHomePage;
                return new UserHomePage();
        }

        private UserHomePage() {
                initialize();
                addEventListener();
        }

        public void _repaint() {
                pcTable.getItems().clear();
                pcTable.getItems().addAll(PCController.getAllPCData());
        }

        private void addEventListener() {
                reportButton.setOnAction((ActionEvent event) -> {
                        ReportPage reportPage = ReportPage.getInstance();
                        reportPage.show();
                });

                logoutButton.setOnAction((ActionEvent event) -> {
                        LoginPage loginPage = LoginPage.getInstance();
                        UserSessionHelper.getInstance().clear();
                        loginPage.show();
                });

                transactionBtn.setOnAction((ActionEvent event) -> {
//                        List<TransactionDetail> transactionHeaderList = TransactionController.getUserTransactionDetail(UserSessionHelper.getInstance().getLoggedInUserId());
                        TransactionHistoryDetailPage transactionHistoryDetailPage = TransactionHistoryDetailPage.getInstance(null, TransactionController.getUserTransactionDetail(UserSessionHelper.getInstance().getLoggedInUserId()));
                        transactionHistoryDetailPage.show();
                });
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
                _repaint();
        }

        private void initialize() {
                bawah = new HBox();
                transactionBtn = new Button("Your Transaction History");
                Label label = new Label("User Home Page");
                label.setFont(Font.font("Arial", FontWeight.BOLD, 36));
                reportButton = new Button("Report PC");
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER_LEFT); // Set alignment to center
                vb.setPadding(new Insets(15, 12, 15, 12));
                vb.setSpacing(10);

                // logout
                logoutButton = new Button("Logout");
                bp = new BorderPane();
                bp.setLeft(label);
                bp.setRight(logoutButton);

                // table
                TableColumn<PC, String> pcIDColumn = new TableColumn<>("ID");
                TableColumn<PC, String> pcConditionColumn = new TableColumn<>("Condition");
                TableColumn<PC, Void> bookPCColumn = new TableColumn<>("Book PC");

                pcIDColumn.setMinWidth(300);
                pcConditionColumn.setMinWidth(300);

                pcIDColumn.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                pcConditionColumn.setCellValueFactory(new PropertyValueFactory<>("PCCondition"));

                bookPCColumn.setMinWidth(174);

                bookPCColumn.setCellFactory(param -> new TableCell<>() {
                        private final Button btn = new Button("Book PC");
                        {
                                btn.setOnAction((ActionEvent event) -> {
                                        PC pc = getTableView().getItems().get(getIndex());

                                        if (pc.getPCCondition().equals("Broken")) {
                                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC is broken", "Please select another PC");
                                                return;
                                        }

                                        if (pc.getPCCondition().equals("Maintenance")) {
                                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC is under maintenance", "Please select another PC");
                                                return;
                                        }

                                        if (PCBookController.getPCBookedDetail(pc.getPCID()) == null ) {
                                                BookPCPage bookPCPage = BookPCPage.getInstance(pc.getPCID());
                                                bookPCPage.show();
                                        } else {
                                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC is already booked", "Please select another PC");
                                                return;
                                        }
                                });
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                        setGraphic(null);
                                } else {
                                        setGraphic(btn);
                                }
                        }
                });

                pcTable = new TableView<>();
                pcTable.getColumns().addAll(pcIDColumn, pcConditionColumn, bookPCColumn);
                pcTable.getItems().addAll(PCController.getAllPCData());
                pcTable.setPrefHeight(400);
                pcTable.setPrefWidth(800);
                bawah.getChildren().addAll(reportButton, transactionBtn);
                bawah.setSpacing(10);
                vb.getChildren().addAll(bp, pcTable, bawah);
                scene = new Scene(vb, 800, 600);
        }
}