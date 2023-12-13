package view;

import controller.*;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PC;
import model.Report;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class JobManagementPage {
        private static JobManagementPage jobManagementPage;
        private Scene scene;
        private Label title, pcDetailsLabel;
        private VBox vb;
        private HBox hb;
        private ComboBox<String> mergedCombo, technicianCombo, jobCombo;
        private Button assignButton;
        public static JobManagementPage getInstance() {
                return jobManagementPage = (jobManagementPage == null) ? new JobManagementPage() : jobManagementPage;
        }

        public JobManagementPage() {
                initialize();
                addEventListener();
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void addEventListener() {
                mergedCombo.setOnAction(e -> {
                        String selectedPCID = mergedCombo.getValue();
                        updateDetailsLabel(selectedPCID);
                });

                assignButton.setOnAction(e -> {
                        String selectedPCID = mergedCombo.getValue();
                        String selectedTechnician = technicianCombo.getValue();
                        String selectedJob = jobCombo.getValue();
                        String selectedTechnicianID = null;
                        List<User> userList = UserController.getAllUserData();
                        for (User user : userList) {
                                if (user.getUsername().equals(selectedTechnician)) {
                                        selectedTechnicianID = user.getUserID();
                                        break;
                                }
                        }

//                        if (PCBookController.getPCBookedDetail(selectedPCID) != null) {
//                                PCBookController.assignUserToNewPC(selectedTechnicianID, selectedPCID);
//                        }
                        JobController.addNewJob(selectedTechnicianID, selectedPCID);
                });
        }

        private void updateDetailsLabel(String selectedPCID) {
                // Retrieve PC details or Report details based on the selected PCID
                PC pc = PCController.getPCDetail(selectedPCID);
                String pcStatus = pc.getPCCondition();

                ArrayList<String> reportDetails = new ArrayList<>();
                List<Report> reports = ReportController.getAllReportData();
                for (Report report : reports) {
                        if (report.getPCID().equals(selectedPCID)) {
                                reportDetails.add(report.getReportNote());
                        }
                }

                // Display details in the label
                pcDetailsLabel.setText("Details for PC ID: " + selectedPCID + "\n" + "- " + pcStatus);
                for (String reportDetail : reportDetails) {
                        appendText(pcDetailsLabel, "\n- " + reportDetail);
                }
        }

        private void appendText(Label label, String newText) {
                String existingText = label.getText();
                label.setText(existingText + newText);
        }

        private void initialize() {
                title = new Label("Job Management");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                vb = new VBox();
                hb = new HBox();
                assignButton = new Button("Assign");
                mergedCombo = new ComboBox<>();
                technicianCombo = new ComboBox<>();
                jobCombo = new ComboBox<>();

                // brokenPC
                ObservableList<PC> observableList1 = PCController.getAllPCData();
                ArrayList<String> brokenPCList = new ArrayList<>();
                for (PC pc : observableList1) {
                        if (pc.getPCCondition().equals("Broken")) {
                                brokenPCList.add(pc.getPCID());
                        }
                }

                // reportedPC
                List<Report> reportedPC = ReportController.getAllReportData();
                ArrayList<String> finalReportedPC = new ArrayList<>();

                for (Report report : reportedPC) {
                        if (!finalReportedPC.contains(report.getPCID())) {
                                finalReportedPC.add(report.getPCID());
                        }
                }

                // Merge brokenPC and reportedPC
                for (String brokenPC : brokenPCList) {
                        if (!finalReportedPC.contains(brokenPC)) {
                                finalReportedPC.add(brokenPC);
                        }
                }

                // technician combo
                List<User> technicianList = UserController.getAllUserData();

                for (User technician : technicianList) {
                        if (technician.getUserRole() == 1) {
                                technicianCombo.getItems().add(technician.getUsername());
                        }
                }

                jobCombo.getItems().addAll("Complete", "UnComplete");

                mergedCombo.getItems().addAll(finalReportedPC);

                hb.getChildren().addAll(mergedCombo);
//                vb.getChildren().addAll(title, hb, technicianCombo, jobCombo, assignButton);
                vb.getChildren().addAll(title, hb, technicianCombo, assignButton);
                vb.setSpacing(10);
                vb.setPadding(new javafx.geometry.Insets(10));
                scene = new Scene(vb, 800, 600);

                // Initialize the label
                pcDetailsLabel = new Label();
                vb.getChildren().add(pcDetailsLabel);
        }
}
