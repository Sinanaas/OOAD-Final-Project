package view;

import controller.PCBookController;
import controller.TransactionController;
import controller.UserController;
import helper.UserSessionHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import model.User;

import java.util.Date;
import java.util.List;

public class TransactionHistoryDetailPage {
        public static TransactionHistoryDetailPage transactionHistoryDetailPage;
        private List<TransactionHeader> transactionID;
        private List<TransactionDetail> transactionDetailList;
        private Scene scene;
        private Button back;
        private VBox vb;
        private Label title;
        private TableView transactionHistoryDetailTable;
        public static TransactionHistoryDetailPage getInstance(List<TransactionHeader> transactionID, List<TransactionDetail> transactionDetailList) {
//                return transactionHistoryDetailPage = transactionHistoryDetailPage == null ? new TransactionHistoryDetailPage() : transactionHistoryDetailPage;
                return new TransactionHistoryDetailPage(transactionID, transactionDetailList);
        }

        public TransactionHistoryDetailPage(List<TransactionHeader> transactionID, List<TransactionDetail> transactionDetailList) {
                this.transactionID = transactionID;
                this.transactionDetailList = transactionDetailList;
                initialize();
                addEventListener();
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void initialize() {
                title = new Label("Transaction History Detail Page");
                title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

                back = new Button("Back");

                transactionHistoryDetailTable = new TableView();
                TableColumn<TransactionDetail, String> transactionIdColumn = new TableColumn<>("Transaction ID");
                transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
                transactionIdColumn.setMinWidth(194);

                TableColumn<TransactionDetail, String> pcIdColumn = new TableColumn<>("PC ID");
                pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                pcIdColumn.setMinWidth(194);

                TableColumn<TransactionDetail, String> customerNameColumn = new TableColumn<>("Customer Name");
                customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
                customerNameColumn.setMinWidth(194);

                TableColumn<TransactionDetail, String> bookedTimeColumn = new TableColumn<>("Booked Time");
                bookedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));
                bookedTimeColumn.setMinWidth(192);

                transactionHistoryDetailTable.getColumns().addAll(transactionIdColumn, pcIdColumn, customerNameColumn, bookedTimeColumn);
                if (transactionID != null && transactionDetailList == null) {
                        for (TransactionHeader transactionHeader : transactionID) {
                                transactionHistoryDetailTable.getItems().addAll(TransactionController.getAllTransactionDetail(transactionHeader.getTransactionID()));
                        }
                } else if (transactionID == null && transactionDetailList != null) {
                        transactionHistoryDetailTable.getItems().addAll(transactionDetailList);
                }

                vb = new VBox();
                vb.setSpacing(10);
                vb.getChildren().addAll(back, title, transactionHistoryDetailTable);
                vb.setAlignment(Pos.CENTER_LEFT);
                vb.setPadding(new Insets(30, 12, 15, 12));
                scene = new Scene(vb, 800, 600);
        }

        private void addEventListener() {
                back.setOnAction(event -> {
                        List<User> userList = User.getAllUserData();
                        User temp = new User();
                        for (User user : userList) {
                                if (user.getUserID().equals(UserSessionHelper.getInstance().getLoggedInUserId())) {
                                        temp = user;
                                        break;
                                }
                        }
                        if (temp.getUserRole() == 3) {
                                TransactionHistoryPage transactionHistoryPage = TransactionHistoryPage.getInstance();
                                transactionHistoryPage.show();
                        } else if (temp.getUserRole() == 0) {
                                UserHomePage userHomePage = UserHomePage.getInstance();
                                userHomePage.show();
                        }
                });
        }

}
