package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Job;
import model.PC;
import model.Report;

import java.util.ArrayList;
import java.util.List;

public class PCController {
        // updatePC(PCID, PCCondition)
        public static void updatePCCondition(String PCID, String PCCondition) {
                ObservableList<PC> observableList = PC.getAllPCData();
                ArrayList<PC> PCList = new ArrayList<>(observableList);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (PCID == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID cannot be empty");
                        alert.showAndWait();
                        return;
                } else if (PCCondition == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC Condition cannot be empty");
                        alert.showAndWait();
                        return;
                } else if (PCID.length() != 5) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must be 5 characters");
                        alert.showAndWait();
                        return;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must start with 'PC'");
                        alert.showAndWait();
                        return;
                } else if (PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID))) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID does not exist");
                        alert.showAndWait();
                        return;
                }  else if (Integer.parseInt(PCID.substring(3, 5)) < 1) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must be greater than 0");
                        alert.showAndWait();
                        return;
                }
                PC.updatePCCondition(PCID, PCCondition);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Success");
                info.setHeaderText("PC updated successfully");
                info.showAndWait();

        }


        // addNewPC(PCOD)
        public static void addNewPC(String PCID) {
                ObservableList<PC> observableList = PC.getAllPCData();
                ArrayList<PC> PCList = new ArrayList<>(observableList);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (PCID == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID cannot be empty");
                        alert.showAndWait();
                        return;
                } else if (PCID.length() != 5) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must be 5 characters");
                        alert.showAndWait();
                        return;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must start with 'PC'");
                        alert.showAndWait();
                        return;
                } else if (PCList.stream().anyMatch(pc -> pc.getPCID().equals(PCID))) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID already exists");
                        alert.showAndWait();
                        return;
                }

                if (PCID != null && PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID)) && PCID.length() == 5 && PCID.substring(0, 2).equals("PC")) {
                        PC.addNewPC(PCID);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Success");
                        info.setHeaderText("PC added successfully");
                        info.showAndWait();
                }
        }

        // deletePC(PCID)
        public static void deletePC(String PCID) {
                ObservableList<PC> observableList = PC.getAllPCData();
                List<Job> jobList = JobController.getAllJobData();
                List<Report> reportList = ReportController.getAllReportData();
                ArrayList<PC> PCList = new ArrayList<>(observableList);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (PCID == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID cannot be empty");
                        alert.showAndWait();
                        return;
                } else if (PCID.length() != 5) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must be 5 characters");
                        alert.showAndWait();
                        return;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must start with 'PC'");
                        alert.showAndWait();
                        return;
                } else if (PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID))) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID does not exist");
                        alert.showAndWait();
                        return;
                } else if (PCBookController.getPCBookedDetail(PCID) != null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC is booked");
                        alert.showAndWait();
                        return;
                } else if (jobList.stream().anyMatch(job -> job.getPCID().equals(PCID))) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC is being maintained");
                        alert.showAndWait();
                        return;
                }

                if (reportList.stream().anyMatch(report -> report.getPCID().equals(PCID))) {
                        ReportController.deleteReportByPCID(PCID);
                        Alert info2 = new Alert(Alert.AlertType.INFORMATION);
                        info2.setTitle("Success");
                        info2.setHeaderText("Reports that are related to " + PCID + " has been deleted successfully");
                        info2.showAndWait();
                }
                PC.deletePC(PCID);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Success");
                info.setHeaderText("PC deleted successfully");
                info.showAndWait();
        }

        // getPCDetail(PCID)
        public  static PC getPCDetail(String PCID) {
                ObservableList<PC> observableList = PC.getAllPCData();
                ArrayList<PC> PCList = new ArrayList<>(observableList);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (PCID == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID cannot be empty");
                        alert.showAndWait();
                        return null;
                } else if (PCID.length() != 5) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must be 5 characters");
                        alert.showAndWait();
                        return null;
                } else if (!PCID.substring(0, 2).equals("PC")) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID must start with 'PC'");
                        alert.showAndWait();
                        return null;
                } else if (PCList.stream().noneMatch(pc -> pc.getPCID().equals(PCID))) {
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID does not exist");
                        alert.showAndWait();
                        return null;
                }

                return PC.getPCDetail(PCID);
        }

        // getAllPCData()
        public static ObservableList getAllPCData() {
                return PC.getAllPCData();
        }
}
