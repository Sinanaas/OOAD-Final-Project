package model;

import database.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {
        private String ReportID;
        private Integer UserRole;
        private String PCID;
        private String ReportNote;
        private Date ReportDate;

        public static String generateID() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM Report ORDER BY ReportID DESC LIMIT 1";
                ResultSet rs = connect.executeQuery(query);

                String nextID = "RP001";

                try {
                        if (rs.next()) {
                                String lastID = rs.getString("ReportID");
                                String lastDigitID = lastID.substring(2);
                                Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
                                nextID = "RP" + String.format("%03d", (lastDigitIDInt + 1));
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return nextID;
        }

        // addNewReport(UserRole, PCID, ReportNote)
        public static void addNewReport(Integer userRole, String pcID, String reportNote) {
                String reportID = generateID();
                LocalDateTime now = LocalDateTime.now();
                String reportDate = now.toString();
                System.out.println(reportDate + " | " + reportID + " | " + userRole + " | " + pcID + " | " + reportNote);


                Connect connect = Connect.getConnection();
                String query = String.format("INSERT INTO Report VALUES('%s', '%d', '%s', '%s', '%s')", reportID, userRole, pcID, reportNote, reportDate);
                connect.executeUpdate(query);
        }

        // getAllReportData()
        public static List<Report> getAllReportData() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM Report";
                ResultSet rs = connect.executeQuery(query);
                List<Report> reportList = new ArrayList<>();
                try {
                        while (rs.next()) {
                                String reportID = rs.getString("ReportID");
                                Integer userRole = rs.getInt("UserRole");
                                String pcID = rs.getString("PCID");
                                String reportNote = rs.getString("ReportNote");
                                Date reportDate = rs.getDate("ReportDate");

                                Report report = new Report(reportID, userRole, pcID, reportNote, reportDate);
                                reportList.add(report);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return reportList;
        }

        // deleteReport(PCID)
        public static void deleteReportByPCID(String PCID) {
                Connect connect = Connect.getConnection();
                String query = String.format("DELETE FROM Report WHERE PCID = '%s'", PCID);
                connect.executeUpdate(query);
        }

        // getter, setter, constructor
        public String getReportID() {
                return ReportID;
        }

        public void setReportID(String reportID) {
                ReportID = reportID;
        }

        public Integer getUserRole() {
                return UserRole;
        }

        public void setUserRole(Integer userRole) {
                UserRole = userRole;
        }

        public String getPCID() {
                return PCID;
        }

        public void setPCID(String PCID) {
                this.PCID = PCID;
        }

        public String getReportNote() {
                return ReportNote;
        }

        public void setReportNote(String reportNote) {
                ReportNote = reportNote;
        }

        public Date getReportDate() {
                return ReportDate;
        }

        public void setReportDate(Date reportDate) {
                ReportDate = reportDate;
        }

        public Report(String reportID, Integer userRole, String PCID, String reportNote, Date reportDate) {
                this.ReportID = reportID;
                this.UserRole = userRole;
                this.PCID = PCID;
                this.ReportNote = reportNote;
                this.ReportDate = reportDate;
        }
}
