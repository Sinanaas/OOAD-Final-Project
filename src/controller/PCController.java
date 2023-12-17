package controller;

import helper.Helper;
import javafx.scene.control.Alert;
import model.Job;
import model.PC;
import model.Report;
import java.util.ArrayList;
import java.util.List;

public class PCController {
        // updatePC(PCID, PCCondition)
        public static void updatePCCondition(String PCID, String PCCondition) {
                ArrayList<PC> PCList = new ArrayList<>(PC.getAllPCData());
                if (PCID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID cannot be empty", null);
                        return;
                } else if (PCCondition == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC condition cannot be empty", null);
                        return;
                } else if (PCID.length() != 5) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be 5 characters", null);
                        return;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must start with 'PC'", null);
                        return;
                } else if (PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID))) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID does not exist", null);
                        return;
                }  else if (Integer.parseInt(PCID.substring(3, 5)) < 1) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be greater than 0", null);
                        return;
                }
                PC.updatePCCondition(PCID, PCCondition);
                Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "PC condition updated successfully", null);
        }


        // addNewPC(PCOD)
        public static void addNewPC(String PCID) {
                ArrayList<PC> PCList = new ArrayList<>(PC.getAllPCData());
                if (PCID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID cannot be empty", null);
                        return;
                } else if (PCID.length() != 5) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be 5 characters", null);
                        return;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must start with 'PC'", null);
                        return;
                } else if (PCList.stream().anyMatch(pc -> pc.getPCID().equals(PCID))) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID already exists", null);
                        return;
                }

                if (PCID != null && PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID)) && PCID.length() == 5 && PCID.substring(0, 2).equals("PC")) {
                        PC.addNewPC(PCID);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "PC added successfully", null);
                }
        }

        // deletePC(PCID)
        public static void deletePC(String PCID) {
                List<Job> jobList = JobController.getAllJobData();
                List<Report> reportList = ReportController.getAllReportData();
                ArrayList<PC> PCList = new ArrayList<>(PC.getAllPCData());
                if (PCID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID cannot be empty", null);
                        return;
                } else if (PCID.length() != 5) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be 5 characters", null);
                        return;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must start with 'PC'", null);
                        return;
                } else if (PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID))) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID does not exist", null);
                        return;
                } else if (PCBookController.getPCBookedDetail(PCID) != null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC is being booked", null);
                        return;
                } else if (jobList.stream().anyMatch(job -> job.getPCID().equals(PCID))) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC is being used", null);
                        return;
                }

                if (reportList.stream().anyMatch(report -> report.getPCID().equals(PCID))) {
                        ReportController.deleteReportByPCID(PCID);
                        Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Report deleted successfully", null);
                }
                PC.deletePC(PCID);
                Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "PC deleted successfully", null);
        }

        // getPCDetail(PCID)
        public  static PC getPCDetail(String PCID) {
                ArrayList<PC> PCList = new ArrayList<>(PC.getAllPCData());
                if (PCID == null) {
                       Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID cannot be empty", null);
                        return null;
                } else if (PCID.length() != 5) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be 5 characters", null);
                        return null;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID must start with 'PC'", null);
                        return null;
                } else if (PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID))) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "PC ID does not exist", null);
                        return null;
                }
                return PC.getPCDetail(PCID);
        }

        // getAllPCData()
        public static List<PC> getAllPCData() {
                return PC.getAllPCData();
        }
}
