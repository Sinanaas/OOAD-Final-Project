package view;

import helper.UserSessionHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PC;
import model.PCBook;
import model.Report;
import model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportPage {
        private static ReportPage reportPage;
        private Scene scene;
        private Label title, pcIDLabel, reportNoteLabel;
        private ComboBox<String> pcComboBox;
        private TextArea reportNoteTextArea;
        private VBox vb;
        private Button reportButton;

        public static ReportPage getInstance() {
                return reportPage = reportPage == null ? new ReportPage() : reportPage;
        }

        public ReportPage() {
                initialize();
                addEventListener();
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void initialize() {
                title = new Label("Report PC");
                title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                pcIDLabel = new Label("PC ID");
                pcComboBox = new ComboBox<>();
                reportNoteLabel = new Label("Report Note");
                reportNoteTextArea = new TextArea();
                reportButton = new Button("Report");

                ObservableList<PC> availablePCs = PC.getAllPCData();
                for (PC pc : availablePCs) {
                        pcComboBox.getItems().add(pc.getPCID());
                }

                vb = new VBox();
                vb.setSpacing(10);
                vb.setPadding(new Insets(15, 12, 15, 12));
                vb.setAlignment(Pos.CENTER_LEFT);
                vb.getChildren().addAll(title, pcIDLabel, pcComboBox, reportNoteLabel, reportNoteTextArea, reportButton);
                scene = new Scene(vb, 800, 600);
        }

        private void addEventListener() {
                reportButton.setOnAction(event -> {
                        PC selectedPC = PC.findPC(pcComboBox.getValue());

                        String pcID = selectedPC != null ? selectedPC.getPCID() : "";
                        String reportNote = reportNoteTextArea.getText();
                        User user = User.getUserById(UserSessionHelper.getInstance().getLoggedInUserId());

                        Report.addNewReport(user.getUserRole(), pcID, reportNote);
                        // alert
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Report PC");
                        alert.setHeaderText("Report PC Success");
                        alert.setContentText("Report PC Success");
                        alert.showAndWait();
                        UserHomePage userHomePage = UserHomePage.getInstance();
                        userHomePage.show();
                });
        }
}
