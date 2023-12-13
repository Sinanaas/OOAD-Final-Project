package controller;

import javafx.scene.control.Alert;
import model.Report;

import java.util.List;

public class ReportController {
        public static void addNewReport(Integer userRole, String userID, String reportNote) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (userRole == null) {
                        alert.setContentText("User role must not be null");
                        alert.show();
                        throw new IllegalArgumentException("User role must not be null");
                } else if (userID == null) {
                        alert.setContentText("User ID must not be null");
                        alert.show();
                        throw new IllegalArgumentException("User ID must not be null");
                } else if (reportNote == null) {
                        alert.setContentText("Report note must not be null");
                        alert.show();
                        throw new IllegalArgumentException("Report note must not be null");
                } else if (reportNote.length() > 100) {
                        alert.setContentText("Report note must not be more than 100 characters");
                        alert.show();
                        throw new IllegalArgumentException("Report note must not be more than 100 characters");
                } else if (reportNote.length() < 10) {
                        alert.setContentText("Report note must not be less than 10 characters");
                        alert.show();
                        throw new IllegalArgumentException("Report note must not be less than 10 characters");
                }
                Report.addNewReport(userRole, userID, reportNote);
        }

        public static List<Report> getAllReportData() {
                return model.Report.getAllReportData();
        }
}
