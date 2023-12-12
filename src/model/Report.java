package model;

import database.Connect;
import helper.UserSessionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

public class Report {
        private String ReportID;
        private Integer UserRole;
        private String PCID;
        private String ReportNote;
        private Date ReportDate;

        // addNewReport(UserRole, PCID, ReportNote)
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

        public static void addNewReport(Integer userRole, String pcID, String reportNote) {
                String reportID = generateID();
                LocalDateTime now = LocalDateTime.now();
                String reportDate = now.toString();

                Connect connect = Connect.getConnection();
                String query = String.format("INSERT INTO Report VALUES('%s', '%d', '%s', '%s', '%s')", reportID, userRole, pcID, reportNote, reportDate);
                connect.executeUpdate(query);
        }
}
