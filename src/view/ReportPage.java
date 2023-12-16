package view;

import controller.PCController;
import controller.ReportController;
import controller.UserController;
import helper.Helper;
import helper.UserSessionHelper;
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
import model.User;

public class ReportPage {
        private static ReportPage reportPage;
        private Scene scene;
        private Label title, pcIDLabel, reportNoteLabel;
        private ComboBox<String> pcComboBox;
        private TextArea reportNoteTextArea;
        private VBox vb;
        private Button reportButton, back;

        public static ReportPage getInstance() {
//                return reportPage = reportPage == null ? new ReportPage() : reportPage;
                return new ReportPage();
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
                pcIDLabel = new Label("PC ID:");
                pcComboBox = new ComboBox<>();
                reportNoteLabel = new Label("Report Note");
                reportButton = new Button("Report");
                reportNoteTextArea = new TextArea();
                reportNoteTextArea.setPromptText("Write your report here...");


                ObservableList<PC> availablePCs = PCController.getAllPCData();
                for (PC pc : availablePCs) {
                        pcComboBox.getItems().add(pc.getPCID());
                }
                if (availablePCs.size() > 0) {
                        pcComboBox.setValue(availablePCs.get(0).getPCID());
                }
                back = new Button("Back");
                back.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                vb = new VBox();
                vb.setSpacing(10);
                vb.setPadding(new Insets(15, 12, 15, 12));
                vb.setAlignment(Pos.CENTER_LEFT);
                vb.getChildren().addAll(back, title, pcIDLabel, pcComboBox, reportNoteLabel, reportNoteTextArea, reportButton);
                scene = new Scene(vb, 800, 600);
        }

        private void addEventListener() {
                reportButton.setOnAction(event -> {
                        PC selectedPC = PCController.getPCDetail(pcComboBox.getValue());
                        String pcID = selectedPC != null ? selectedPC.getPCID() : "";
                        String reportNote = reportNoteTextArea.getText();
                        User user = UserController.getAllUserData()
                                .stream()
                                .filter(u -> {
                                        String loggedInUserId = UserSessionHelper.getInstance().getLoggedInUserId();
                                        return loggedInUserId != null && loggedInUserId.equals(u.getUserID());
                                }).findFirst().orElse(null);

                        ReportController.addNewReport(user.getUserRole(), pcID, reportNote);

                        Helper.showAlert(Alert.AlertType.INFORMATION, "Report PC", "Report PC", "Report PC Successfully");

                        if (user.getUserRole() == 2) {
                                OperatorHomePage operatorHomePage = OperatorHomePage.getInstance();
                                operatorHomePage.show();
                        } else if (user.getUserRole() == 1) {
                                UserHomePage userHomePage = UserHomePage.getInstance();
                                userHomePage.show();
                        }
                });

                back.setOnAction(event -> {
                        String uid = UserSessionHelper.getInstance().getLoggedInUserId();
                        User user = UserController.getAllUserData()
                                .stream()
                                .filter(u -> uid != null && uid.equals(u.getUserID()))
                                .findFirst().orElse(null);
                        if (user.getUserRole() == 2) {
                                OperatorHomePage operatorHomePage = OperatorHomePage.getInstance();
                                operatorHomePage.show();
                        } else if (user.getUserRole() == 0) {
                                UserHomePage userHomePage = UserHomePage.getInstance();
                                userHomePage.show();
                        }
                });
        }
}
