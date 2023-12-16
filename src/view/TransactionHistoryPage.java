package view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.TransactionDetail;
import model.TransactionHeader;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryPage {
        public static TransactionHistoryPage transactionHistoryPage;
        public static TransactionHistoryPage getInstance() {
//                return transactionHistoryPage = transactionHistoryPage == null ? new TransactionHistoryPage() : transactionHistoryPage;
                return new TransactionHistoryPage();
        }
        private Label title;
        private Scene scene;
        private VBox vb;
        private TableView transactionHistoryTable;
        private List<TransactionDetail> transactionDetailList;
        private Button back;
        public TransactionHistoryPage() {
                initialize();
                addEventListener();
                _repaint();
        }

        private void _repaint() {
                transactionDetailList = new ArrayList<>();
                transactionHistoryTable.getItems().clear();
                for (TransactionHeader transactionHeader : TransactionHeader.getAllTransactionHeaderData()) {
//                        transactionDetailList.addAll(TransactionController.getAllTransactionDetail(transactionHeader.getTransactionID()));
                        transactionHistoryTable.getItems().add(transactionHeader);
                }
//                transactionHistoryTable.getItems().addAll(transactionDetailList);
        }

        private void addEventListener() {
                back.setOnAction(event -> {
                        AdminHomePage homePage = AdminHomePage.getInstance();
                        homePage.show();
                });
        }

        private void initialize() {
                back = new Button("Back");

                transactionDetailList = new ArrayList<>();
                title = new Label("Transaction History");
                title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

                transactionHistoryTable = new TableView();
                transactionHistoryTable.setEditable(false);

                TableColumn<TransactionHeader, String> transactionIDCol = new TableColumn<>("Transaction ID");
                TableColumn<TransactionHeader, String> staffIDCol = new TableColumn<>("Staff ID");
                TableColumn<TransactionHeader, String> staffNameCol = new TableColumn<>("Staff Name");
                TableColumn<TransactionHeader, String> transactionTimeCol = new TableColumn<>("Transaction Time");
                TableColumn<TransactionHeader, Void> detailCol = new TableColumn<>("Action");

                transactionIDCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
                staffIDCol.setCellValueFactory(new PropertyValueFactory<>("StaffID"));
                staffNameCol.setCellValueFactory(new PropertyValueFactory<>("StaffName"));
                transactionTimeCol.setCellValueFactory(new PropertyValueFactory<>("TransactionTime"));

                transactionIDCol.setMinWidth(177);
                staffIDCol.setMinWidth(177);
                staffNameCol.setMinWidth(177);
                transactionTimeCol.setMinWidth(175);
                detailCol.setMinWidth(40);

                detailCol.setCellFactory(param -> new TableCell<>() {
                        private final Button btn = new Button("Details");

                        {
                                btn.setOnAction((ActionEvent event) -> {
                                        TransactionHeader transactionHeader = getTableView().getItems().get(getIndex());
                                        List<TransactionHeader> transactionHeaderList = new ArrayList<>();
                                        transactionHeaderList.add(transactionHeader);
                                        TransactionHistoryDetailPage transactionHistoryDetailPage = TransactionHistoryDetailPage.getInstance(transactionHeaderList, null);
                                        transactionHistoryDetailPage.show();
                                });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                        setGraphic(null);
                                } else {
                                        setGraphic(btn);
                                }
                        }
                });
                transactionHistoryTable.getColumns().addAll(transactionIDCol, staffIDCol, staffNameCol, transactionTimeCol, detailCol);

                for (TransactionHeader transactionHeader : TransactionHeader.getAllTransactionHeaderData()) {
                        transactionHistoryTable.getItems().add(transactionHeader);
                }
                vb = new VBox();
                vb.getChildren().addAll(back, title, transactionHistoryTable);
                vb.setSpacing(10);
                vb.setPadding(new Insets(45, 12, 15, 12));

                scene = new Scene(vb, 800, 600);
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
                _repaint();
        }
}
