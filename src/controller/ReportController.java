package controller;

import javafx.scene.control.Alert;
import model.Report;

import java.util.List;

public class ReportController {
        public static void addNewReport(Integer userRole, String userID, String reportNote) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (userRole == null) {
                        alert.setContentText("User role must not be null");
                        alert.showAndWait();
                        return;
                } else if (userID == null) {
                        alert.setContentText("User ID must not be null");
                        alert.showAndWait();
                        return;
                } else if (reportNote == null) {
                        alert.setContentText("Report note must not be null");
                        alert.showAndWait();
                        return;
                } else if (reportNote.length() > 100) {
                        alert.setContentText("Report note must not be more than 100 characters");
                        alert.showAndWait();
                        return;
                } else if (reportNote.length() < 10) {
                        alert.setContentText("Report note must not be less than 10 characters");
                        alert.showAndWait();
                        return;
                }
                Report.addNewReport(userRole, userID, reportNote);
        }

        public static List<Report> getAllReportData() {
                return Report.getAllReportData();
        }

        // deleteReport(reportID)
        public static void deleteReportByPCID(String reportID) {
                if (reportID == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Report ID cannot be empty");
                        alert.showAndWait();
                        return;
                }
                Report.deleteReportByPCID(reportID);
        }
}
