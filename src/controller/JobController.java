package controller;

import database.Connect;
import javafx.scene.control.Alert;
import model.Job;
import model.PC;
import model.PCBook;
import model.User;

import java.util.Collections;
import java.util.List;

public class JobController {
        // addNewJob(UserID, PCID)
        public static void addNewJob(String userID, String pcID) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                if (userID == null || pcID == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please select user and PC");
                        alert.showAndWait();
                } else {
                        // check if there's already a technician that is already working on fixing the pc
                        List<Job> jobList = Job.getAllJobData();
                        if (jobList != null) {
                                for (Job job : jobList) {
                                        if (job.getPCID().equals(pcID) && job.getJobStatus().equals("UnComplete")) {
                                                alert.setTitle("Error");
                                                alert.setHeaderText("Error");
                                                alert.setContentText("PC is already being fixed");
                                                alert.showAndWait();
                                                return;
                                        }
                                }
                        }
                        // Check if the selected PC is booked
                        if (PCBookController.getPCBookedDetail(pcID) != null) {
                                String tempUserID= PCBookController.getAllPCBookedData().stream().filter(pcBook -> pcBook.getPCID().equals(pcID)).findFirst().get().getUserID();
                                boolean flag = PCBookController.assignUserToNewPC(tempUserID, pcID);
                                if (flag == true) {
                                        PCController.updatePCCondition(pcID, "Maintenance");
                                        Job.addNewJob(userID, pcID);
                                        successAlert.setTitle("Success");
                                        successAlert.setHeaderText("Success");
                                        successAlert.setContentText("Successfully added a new job");
                                        successAlert.showAndWait();
                                        return;
                                } else {
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Error");
                                        alert.setContentText("Failed to assign user to new PC");
                                        alert.showAndWait();
                                        return;
                                }
                        } else {
                                Job.addNewJob(userID, pcID);
                                PCController.updatePCCondition(pcID, "Maintenance");
                                successAlert.setTitle("Success");
                                successAlert.setHeaderText("Success");
                                successAlert.setContentText("Successfully added a new job");
                                successAlert.showAndWait();
                                return;
                        }
                }
        }



        // updateJobStatus(JobID, JobStatus)
        public static void updateJobStatus(String jobID, String jobStatus) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                boolean isJobConflictWithPCBook = false;
                List<PCBook> pcBookList = PCBookController.getAllPCBookedData();

                for (PCBook pcBook : pcBookList) {
                        if (pcBook.getPCID().equals(jobID)) {
                                isJobConflictWithPCBook = true;
                                break;
                        }
                }

                if (!jobStatus.equals("Complete")) {
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Job status must be complete");
                        alert.showAndWait();
                        return;
                }

                if ((jobID == null || jobStatus == null)) {
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please select job and status");
                        alert.showAndWait();
                        return;
                } else if (isJobConflictWithPCBook)  {
                        alert.setTitle("Error");
                        alert.setHeaderText("Cannot update job status");
                        alert.setContentText("Job is conflict with PCBook");
                        alert.showAndWait();
                        return;
                } else {
                        Job.updateJobStatus(jobID, jobStatus);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Success");
                        alert1.setHeaderText("Success");
                        alert1.setContentText("Update job status successfully");
                        alert1.showAndWait();
                        return;
                }
        }

        // getPCOnWorkingList(PCID)
        public static List<PC> getPCOnWorkingList(String pcID) {
                return null;
        }

        // getTechnicianJob(UserID)
        public static List<Job> getTechnicianJob(String userID) {
                return null;
        }

        // getAllJobData()
        public static List<Job> getAllJobData() {
                List<Job> jobList = Job.getAllJobData();
                return jobList != null ? jobList : Collections.emptyList();
        }
}
