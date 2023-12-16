package controller;

import database.Connect;
import helper.Helper;
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
                if (userID == null || pcID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a user and a PC");
                } else {
                        // check if there's already a technician that is already working on fixing the pc
                        List<Job> jobList = Job.getAllJobData();
                        if (jobList != null) {
                                for (Job job : jobList) {
                                        if (job.getPCID().equals(pcID) && job.getJobStatus().equals("UnComplete")) {
                                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "There's already a technician that is already working on fixing the pc");
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
                                        Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Success", "Successfully added a new job");
                                        return;
                                } else {
                                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Failed to assign user to new PC");
                                        return;
                                }
                        } else {
                                Job.addNewJob(userID, pcID);
                                PCController.updatePCCondition(pcID, "Maintenance");
                                Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Success", "Successfully added a new job");
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
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a valid job status");
                        return;
                }

                if ((jobID == null || jobStatus == null)) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a job");
                        return;
                } else if (isJobConflictWithPCBook)  {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Job is conflict with PC Book");
                        return;
                } else {
                        Job.updateJobStatus(jobID, jobStatus);
                        Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Success", "Successfully updated job status");
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
