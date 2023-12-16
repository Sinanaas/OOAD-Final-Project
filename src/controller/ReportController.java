package controller;

import helper.Helper;
import javafx.scene.control.Alert;
import model.Report;
import java.util.List;

public class ReportController {
        public static void addNewReport(Integer userRole, String userID, String reportNote) {
                if (userRole == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "User role must not be null", null);
                        return;
                } else if (userID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "User ID must not be null", null);
                        return;
                } else if (reportNote == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Report note must not be null", null);
                        return;
                } else if (reportNote.length() > 100) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Report note must not be more than 100 characters", null);
                        return;
                } else if (reportNote.length() < 10) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Report note must not be less than 10 characters", null);
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
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Report ID must not be null", null);
                        return;
                }
                Report.deleteReportByPCID(reportID);
        }
}
