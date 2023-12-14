package view;

import controller.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.Job;
import model.PC;
import model.Report;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class JobManagementPage {
        private static JobManagementPage jobManagementPage;
        private Scene scene;
        private ScrollPane scrollPane;
        private Label title, pcDetailsLabel, secondLabel;
        private VBox vb;
        private HBox hb, tableHb, secondHb;
        private ComboBox<String> mergedCombo, technicianCombo, jobCombo, jobStatusCombo;
        private TextField jobIDField,  userIDField, pcIDField;
        private TableView jobTable;
        private Button assignButton, updateJobStatus, back;
        public static JobManagementPage getInstance() {
                return jobManagementPage = (jobManagementPage == null) ? new JobManagementPage() : jobManagementPage;
        }

        public JobManagementPage() {
                initialize();
                addEventListener();
        }

        public void _repaint() {
                jobTable.getItems().clear();
                jobTable.getItems().addAll(
                        JobController.getAllJobData()
                );
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void addEventListener() {
                back.setOnAction(e -> {
                        AdminHomePage adminPage = AdminHomePage.getInstance();
                        adminPage.show();
                });

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

                updateJobStatus.setOnAction(e -> {
                        String selectedJobID = jobIDField.getText();
                        String selectedJobStatus = jobStatusCombo.getValue();
//                        System.out.println(selectedJobID + " | " + selectedJobStatus);
                        // ini aku taro luar soalnya kalo masukin ke masing-masing controller bakal ribet
                        if (selectedJobID == null || selectedJobStatus == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Error");
                                alert.setContentText("Please select a job and a job status");
                                alert.showAndWait();
                                return;
                        }
                        JobController.updateJobStatus(selectedJobID, selectedJobStatus);
                        PCController.updatePCCondition(pcIDField.getText(), "Usable");
                        _repaint();
                        jobIDField.clear();
                        userIDField.clear();
                        pcIDField.clear();
                        jobStatusCombo.setValue(null);
                });

                jobTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                Job job = (Job) newSelection;
                                jobIDField.setText(job.getJobID());
                                userIDField.setText(job.getUserID());
                                pcIDField.setText(job.getPCID());
                                jobStatusCombo.setValue(job.getJobStatus());
                        } else {
                                jobIDField.clear();
                                userIDField.clear();
                                pcIDField.clear();
                                jobStatusCombo.setValue(null);
                        }
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
                Font font2 = Font.font("Arial", FontWeight.SEMI_BOLD, 15);
                title.setFont(font);

                updateJobStatus = new Button("Update Job Status");
                jobIDField = new TextField();
                userIDField = new TextField();
                pcIDField = new TextField();
                jobStatusCombo = new ComboBox<>();
                jobStatusCombo.getItems().addAll("Complete", "UnComplete");
                jobStatusCombo.setPromptText("Job Status");
                tableHb = new HBox();
                tableHb.getChildren().addAll(jobIDField, userIDField, pcIDField, jobStatusCombo, updateJobStatus);

                // table
                jobTable = new TableView();
                TableColumn<Job, String> jobIDCol = new TableColumn<>("Job ID");
                TableColumn<Job, String> userIDCol = new TableColumn<>("User ID");
                TableColumn<Job, String> pcIDCol = new TableColumn<>("PC ID");
                TableColumn<Job, String> jobStatusCol = new TableColumn<>("Job Status");

                jobIDCol.setMinWidth(194);
                userIDCol.setMinWidth(194);
                pcIDCol.setMinWidth(194);
                jobStatusCol.setMinWidth(196);

                jobIDCol.setCellValueFactory(new PropertyValueFactory<>("JobID"));
                userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
                pcIDCol.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                jobStatusCol.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));

                jobIDField.setPromptText("Job ID");
                jobIDField.editableProperty().setValue(false);
                userIDField.setPromptText("User ID");
                userIDField.editableProperty().setValue(false);
                pcIDField.setPromptText("PC ID");
                pcIDField.editableProperty().setValue(false);


                jobTable.getColumns().addAll(jobIDCol, userIDCol, pcIDCol, jobStatusCol);
                jobTable.getItems().addAll(JobController.getAllJobData());

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
                // Initialize the label

                pcDetailsLabel = new Label();
                jobCombo.getItems().addAll("Complete", "UnComplete");

                mergedCombo.getItems().addAll(finalReportedPC);
                if (mergedCombo.getItems().size() > 0) {
                        mergedCombo.setValue(mergedCombo.getItems().get(0));
                        updateDetailsLabel(mergedCombo.getItems().get(0));
                } else {
                        mergedCombo.setPromptText("No PC available");
                        mergedCombo.setDisable(true);
                }

                if (technicianCombo.getItems().size() > 0) {
                        technicianCombo.setValue(technicianCombo.getItems().get(0));
                } else {
                        technicianCombo.setPromptText("No technician available");
                        technicianCombo.setDisable(true);
                }

                // second
                secondLabel = new Label("Assign Technician to a job");
                secondLabel.setFont(font2);
                secondHb = new HBox();
                secondHb.setSpacing(10);
                secondHb.getChildren().addAll(mergedCombo, technicianCombo, assignButton);

//                hb.getChildren().addAll(mergedCombo);
                tableHb.setSpacing(10);
//                vb.getChildren().addAll(title, hb, technicianCombo, jobCombo, assignButton);
                back = new Button("Back");
                back.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                vb.getChildren().addAll(back, title, jobTable, tableHb, secondLabel, secondHb);
                vb.getChildren().add(pcDetailsLabel);
                vb.setSpacing(10);
                vb.setPadding(new Insets(15, 12, 15, 12));
                scrollPane = new ScrollPane(vb);
                scrollPane.setFitToHeight(true);
                scrollPane.setFitToWidth(true);
                scene = new Scene(scrollPane, 800, 800);
        }
}
