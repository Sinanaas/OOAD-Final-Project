package view;

import controller.PCBookController;
import controller.TransactionController;
import helper.UserSessionHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PCBook;
import model.TransactionHeader;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class OperatorHomePage {
        public static OperatorHomePage operatorHomePage;
        public static OperatorHomePage getInstance() {
                return operatorHomePage = operatorHomePage == null ? new OperatorHomePage() : operatorHomePage;
        }
        private Label title;
        private BorderPane bp;
        private HBox hb;
        private Scene scene;
        private VBox vb;
        private TableView pcBookTable;
        private TextField pcIDInput, userIDInput, bookedDateInput, pcBookIDInput;
        private Button cancelBtn, finishBtn, reassignBtn, logout, report;
        public OperatorHomePage() {
                initialize();
                addEventListener();
                _repaint();
        }

        public void _repaint() {
                pcBookTable.getItems().clear();
                pcBookTable.getItems().addAll(PCBookController.getAllPCBookedData());
                pcIDInput.clear();
                userIDInput.clear();
                bookedDateInput.clear();
                pcBookIDInput.clear();
        }
        private void addEventListener() {
                report.setOnAction(e -> {
                        ReportPage reportPage = ReportPage.getInstance();
                        reportPage.show();
                });

                logout.setOnAction(e -> {
                        LoginPage loginPage = LoginPage.getInstance();
                        UserSessionHelper.getInstance().clear();
                        loginPage.show();
                });

                cancelBtn.setOnAction(e -> {
                        List<PCBook> selectedPCBooks = pcBookTable.getSelectionModel().getSelectedItems();

                        if (!selectedPCBooks.isEmpty()) {
                                for (PCBook pcBook : selectedPCBooks) {
                                        Date pcBookBookedDate = new Date(pcBook.getBookedDate().getTime());
                                        LocalDate bookedDate = pcBookBookedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                        if (!LocalDate.now().isBefore(bookedDate)) {
                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setTitle("Cancel Booking");
                                                alert.setHeaderText("Cancel Booking");
                                                alert.setContentText("Cannot cancel booking that already passed");
                                                alert.showAndWait();
                                                return;
                                        }

                                        PCBookController.deleteBookData(pcBook.getBookID());

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Cancel Booking");
                                        alert.setHeaderText("Cancel Booking");
                                        alert.setContentText("Cancel Booking Successfully");
                                        alert.showAndWait();
                                }

                                _repaint();
                        }
                });

                finishBtn.setOnAction(e -> {
                        List<PCBook> selectedPCBooks = pcBookTable.getSelectionModel().getSelectedItems();
                        if (!selectedPCBooks.isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                TransactionController.addNewTransaction(TransactionHeader.generateID(), selectedPCBooks, UserSessionHelper.getInstance().getLoggedInUserId());
                                for (PCBook pcBook : selectedPCBooks) {
                                        PCBookController.deleteBookData(pcBook.getBookID());
                                }
                                alert.setTitle("Finish Booking");
                                alert.setHeaderText("Finish Booking");
                                alert.setContentText("Finish Booking Successfully");
                                alert.showAndWait();
                                _repaint();
                        }
                });

                reassignBtn.setOnAction(e -> {
                        PCBookController.assignUserToNewPC(userIDInput.getText(), pcIDInput.getText());
                        _repaint();
                });

                pcBookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                PCBook pcBook = (PCBook) newSelection;
                                pcBookIDInput.setText(pcBook.getBookID());
                                pcIDInput.setText(pcBook.getPCID());
                                userIDInput.setText(pcBook.getUserID());
                                bookedDateInput.setText(pcBook.getBookedDate().toString());
                        } else {
                                pcBookIDInput.clear();
                                pcIDInput.clear();
                                userIDInput.clear();
                                bookedDateInput.clear();
                        }
                });
        }
        private void initialize() {
                report = new Button("Report");
                title = new Label("PCBook Management");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                pcBookIDInput = new TextField();
                pcBookIDInput.setPromptText("PCBook ID");
                pcBookIDInput.editableProperty().setValue(false);
                pcBookIDInput.setMaxWidth(115);

                pcIDInput = new TextField();
                pcIDInput.setPromptText("PC ID");
                pcIDInput.editableProperty().setValue(false);
                pcIDInput.setMaxWidth(115);

                userIDInput = new TextField();
                userIDInput.setPromptText("User ID");
                userIDInput.editableProperty().setValue(false);
                userIDInput.setMaxWidth(115);

                bookedDateInput = new TextField();
                bookedDateInput.setPromptText("Booked Date");
                bookedDateInput.editableProperty().setValue(false);
                bookedDateInput.setMaxWidth(117);

                cancelBtn = new Button("Cancel");
                finishBtn = new Button("Finish");
                reassignBtn = new Button("Reassign User to New PC");
                hb = new HBox();
                hb.setSpacing(10);
                hb.getChildren().addAll(pcBookIDInput, pcIDInput, userIDInput, bookedDateInput, cancelBtn, finishBtn, reassignBtn);

                vb = new VBox();
                pcBookTable = new TableView();
                vb.setSpacing(10);
                TableColumn<PCBook, String> bookIDCol = new TableColumn("Book ID");
                bookIDCol.setMinWidth(194);
                TableColumn<PCBook, String> pcIDCol = new TableColumn("PC ID");
                pcIDCol.setMinWidth(194);
                TableColumn<PCBook, String> userIDCol = new TableColumn("User ID");
                userIDCol.setMinWidth(194);
                TableColumn<PCBook, String> bookedDateCol = new TableColumn("Booked Date");
                bookedDateCol.setMinWidth(192);

                bookIDCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));
                pcIDCol.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
                bookedDateCol.setCellValueFactory(new PropertyValueFactory<>("BookedDate"));
                logout = new Button("Logout");
                bp = new BorderPane();
                bp.setLeft(title);
                bp.setRight(logout);

                pcBookTable.getColumns().addAll(bookIDCol, pcIDCol, userIDCol, bookedDateCol);
                pcBookTable.getItems().addAll(PCBookController.getAllPCBookedData());
                pcBookTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                vb.getChildren().addAll(bp, pcBookTable, hb, report);
                vb.setPadding(new Insets(12, 15, 12, 15));
                scene = new Scene(vb, 800, 600);
        }
        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
                _repaint();
        }
}