package view;

import controller.JobController;
import controller.PCController;
import helper.Helper;
import helper.UserSessionHelper;
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
import model.Job;
import model.PC;

import java.util.List;

public class TechnicianHomePage {
        private static TechnicianHomePage technicianHomePage;
        private Scene scene;
        private BorderPane bp;
        private HBox tableHb;
        private Label title;
        private TableView jobTable, pcTable;
        private VBox vb;
        private ComboBox<String> jobStatusCombo;
        private TextField jobIDField,  userIDField, pcIDField;
        private Button completeJobBtn, logout;
        public static TechnicianHomePage getInstance() {
//                return technicianHomePage = technicianHomePage == null ? new TechnicianHomePage() : technicianHomePage;
                return new TechnicianHomePage();
        }
        private TechnicianHomePage() {
                initialize();
                addEventListener();
        }
        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }
        private void addEventListener() {
                logout.setOnAction(e -> {
                        LoginPage loginPage = LoginPage.getInstance();
                        UserSessionHelper.getInstance().clear();
                        loginPage.show();
                });

                completeJobBtn.setOnAction(e -> {
                        String jobID = jobIDField.getText();
                        String userID = userIDField.getText();
                        String pcID = pcIDField.getText();
                        String jobStatus = jobStatusCombo.getValue();
                        if (jobID.isEmpty() || userID.isEmpty() || pcID.isEmpty() || jobStatus.isEmpty()) {
                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please fill in all fields");
                                return;
                        } else if (jobStatus.equals("UnComplete")) {
                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a valid job status");
                                return;
                        }else {
                                JobController.updateJobStatus(jobID,  jobStatus);
                                PCController.updatePCCondition(pcID, "Usable");
                                jobTable.getItems().clear();
                                List<Job> jobList = JobController.getAllJobData();
                                for (Job job : jobList) {
                                        if (job.getUserID().equals(UserSessionHelper.getInstance().getLoggedInUserId())) {
                                                jobTable.getItems().add(job);
                                        }
                                }
                        }
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

        private void initialize() {
                title = new Label("My Jobs");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                completeJobBtn = new Button("Update Job Status");
                jobIDField = new TextField();
                jobIDField.setPromptText("Job ID");
                jobIDField.editableProperty().set(false);
                userIDField = new TextField();
                userIDField.setPromptText("User ID");
                userIDField.editableProperty().set(false);
                pcIDField = new TextField();
                pcIDField.setPromptText("PC ID");
                pcIDField.editableProperty().set(false);
                jobStatusCombo = new ComboBox<>();
                jobStatusCombo.getItems().addAll("Complete", "UnComplete");
                jobStatusCombo.setPromptText("Job Status");
                tableHb = new HBox();
                tableHb.setSpacing(10);
                tableHb.getChildren().addAll(jobIDField, userIDField, pcIDField, jobStatusCombo, completeJobBtn);

                // table
                pcTable = new TableView();

                TableColumn<PC, String> pcIDCols = new TableColumn<>("PC ID");
                TableColumn<PC, String> pcStatusCol = new TableColumn<>("PC Condition");

                pcIDCols.setMinWidth(388);
                pcStatusCol.setMinWidth(384);

                pcIDCols.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                pcStatusCol.setCellValueFactory(new PropertyValueFactory<>("PCCondition"));

                pcTable.getColumns().addAll(pcIDCols, pcStatusCol);
                pcTable.getItems().addAll(PC.getAllPCData());

                // table
                jobTable = new TableView();
                TableColumn<Job, String> jobIDCol = new TableColumn<>("Job ID");
                TableColumn<Job, String> userIDCol = new TableColumn<>("User ID");
                TableColumn<Job, String> pcIDCol = new TableColumn<>("PC ID");
                TableColumn<Job, String> jobStatusCol = new TableColumn<>("Job Status");

                jobIDCol.setMinWidth(194);
                userIDCol.setMinWidth(194);
                pcIDCol.setMinWidth(194);
                jobStatusCol.setMinWidth(192);

                jobIDCol.setCellValueFactory(new PropertyValueFactory<>("JobID"));
                userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
                pcIDCol.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                jobStatusCol.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));

                jobTable.getColumns().addAll(jobIDCol, userIDCol, pcIDCol, jobStatusCol);
                List<Job> jobList = JobController.getAllJobData();
                for (Job job : jobList) {
                        if (job.getUserID().equals(UserSessionHelper.getInstance().getLoggedInUserId())) {
                                jobTable.getItems().add(job);
                        }
                }
                vb = new VBox();
                bp = new BorderPane();
                logout = new Button("Logout");
                bp.setLeft(title);
                bp.setRight(logout);
                vb.setSpacing(10);
                vb.setPadding(new Insets(15, 12, 15, 12));
                vb.getChildren().addAll(bp, pcTable, jobTable, tableHb);
                vb.setAlignment(Pos.CENTER);
                scene = new Scene(vb, 800, 600);
        }
}
