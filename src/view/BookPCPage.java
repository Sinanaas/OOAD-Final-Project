package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PCBook;
import helper.UserSessionHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class BookPCPage {
        private static BookPCPage bookPCPage;
        private Scene scene;
        private String  pcID;
        private Label title, label, pcIDLabel, bookId, hourLabel, minuteLabel;
        private VBox vb;
        private TextField pcIDInput, bookIdInput;
        private DatePicker datePicker;
        private Date date;
        private Spinner<Integer> hourSpinner;
        private Spinner<Integer> minuteSpinner;
        private HBox dateHb;
        private Button bookButton;

        public static BookPCPage getInstance(String pcID) {
                return bookPCPage = bookPCPage == null ? new BookPCPage(pcID) : bookPCPage;
        }
        public BookPCPage(String pcid) {
                this.pcID = pcid;
                initialize();
                addEventListener();
        }
        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }
        private void initialize() {
                title = new Label("Book PC Page");
                title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                // book id
                bookId = new Label("Book ID:");
                bookId.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
                bookIdInput = new TextField(PCBook.generateID());
                bookIdInput.setDisable(true);

                // pc id
                pcIDLabel = new Label("PC ID:");
                pcIDLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
                pcIDInput = new TextField(pcID);
                pcIDInput.setDisable(true);

                // date
                label = new Label("Start Date: ");
                label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
                datePicker = new DatePicker();
                datePicker.setPromptText("Select Date");

                // book button
                bookButton = new Button("Book PC");
                bookButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                dateHb = new HBox(10);
                dateHb.getChildren().addAll(label, datePicker);
                vb = new VBox();
                vb.setSpacing(10);
                vb.getChildren().addAll(title, bookId, bookIdInput, pcIDLabel, pcIDInput, dateHb, bookButton);
                vb.setAlignment(Pos.CENTER_LEFT);
                vb.setPadding(new Insets(50));
                scene = new Scene(vb, 800, 600);
        }

        private void addEventListener() {
                bookButton.setOnMouseClicked(e -> {
                        String bookID = bookIdInput.getText();
                        String pcID = pcIDInput.getText();
                        Date date = java.sql.Date.valueOf(datePicker.getValue());
                        PCBook.addNewBook(bookID, pcID, UserSessionHelper.getInstance().getLoggedInUserId(), date);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Book PC");
                        alert.setHeaderText("Book PC Success");
                        alert.setContentText("Book PC Success");
                        alert.showAndWait();
                        UserHomePage userHomePage = UserHomePage.getInstance();
                        userHomePage.show();
                });
        }
}