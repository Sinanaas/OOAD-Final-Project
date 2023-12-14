package controller;

import database.Connect;
import javafx.scene.control.Alert;
import model.Job;
import model.PC;
import model.PCBook;

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
                                List<PC> pcList = PCController.getAllPCData();
                                List<PCBook> pcBookList = PCBookController.getAllPCBookedData();
                                String availablePCID = null;
                                String reassignUserID = null;

                                // Find an available and usable PC
                                for (PC pc : pcList) {
                                        boolean pcIsBooked = false;

                                        // Check if the PC is booked in PCBook
                                        for (PCBook pcBook : pcBookList) {
                                                if (pc.getPCID().equals(pcBook.getPCID())) {
                                                        pcIsBooked = true;
                                                        reassignUserID = pcBook.getUserID();
                                                        break;
                                                }
                                        }

                                        // Check if the PC is usable and not booked
                                        if (!pcIsBooked && pc.getPCCondition().equals("Usable")) {
                                                availablePCID = pc.getPCID();
                                                break;  // found an available and usable PC, exit the loop
                                        }
                                }

                                // If the selected PC is already booked, reassign the user
                                if (reassignUserID != null && availablePCID != null) {
                                        PCController.updatePCCondition(pcID, "Maintenance");
                                        PCBookController.assignUserToNewPC(reassignUserID, availablePCID);
                                }

                                // If no usable PCs are available
                                if (availablePCID == null) {
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Error");
                                        alert.setContentText("No usable PCs available");
                                        alert.showAndWait();
                                        return;  // exit the method if no usable PCs are available
                                }
                        }

                        // Add a new job with the available PC
                        Job.addNewJob(userID, pcID);

                        // Show success message
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText("Success");
                        successAlert.setContentText("Add new job successfully");
                        successAlert.showAndWait();
                }
        }



        // updateJobStatus(JobID, JobStatus)
        public static void updateJobStatus(String jobID, String jobStatus) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (jobID == null || jobStatus == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please select job and status");
                        alert.showAndWait();
                } else {
                        Job.updateJobStatus(jobID, jobStatus);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Success");
                        alert1.setHeaderText("Success");
                        alert1.setContentText("Update job status successfully");
                        alert1.showAndWait();
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
