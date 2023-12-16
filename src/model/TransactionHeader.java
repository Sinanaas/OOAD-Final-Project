package model;

import database.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHeader {
        private String TransactionID;
        private String StaffID;
        private String StaffName;
        private Date TransactionTime;

        public static String generateID() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM TransactionHeader ORDER BY TransactionID DESC LIMIT 1";
                ResultSet rs = connect.executeQuery(query);

                String nextID = "TR001";
                try {
                        if (rs.next()) {
                                String lastID = rs.getString("TransactionID");
                                String lastDigitID = lastID.substring(2);
                                Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
                                nextID = "TR" + String.format("%03d", (lastDigitIDInt + 1));
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return nextID;
        }

        // getAllTransactionHeaderData()
        public static List<TransactionHeader> getAllTransactionHeaderData() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM TransactionHeader";
                ResultSet rs = connect.executeQuery(query);
                List<TransactionHeader> transactionHeaderList = new ArrayList<>();
                try {
                        while (rs.next()) {
                                String transactionID = rs.getString("TransactionID");
                                String staffID = rs.getString("StaffID");
                                String staffName = rs.getString("StaffName");
                                Date transactionTime = rs.getDate("TransactionTime");

                                TransactionHeader transactionHeader = new TransactionHeader(transactionID, staffID, staffName, transactionTime);
                                transactionHeaderList.add(transactionHeader);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return transactionHeaderList;
        }

        // addNewTransactionHeader(StaffID, StaffName)
        public static void addNewTransactionHeader(String staffID, String staffName) {
                String transactionID = generateID();
                Timestamp transactionTime = Timestamp.from(Instant.now());

                Connect connect = Connect.getConnection();
                String query = String.format("INSERT INTO TransactionHeader VALUES('%s', '%s', '%s', '%s')", transactionID, staffID, staffName, transactionTime);
                connect.executeUpdate(query);
        }

        // getter, setter, constructor
        public String getTransactionID() {
                return TransactionID;
        }

        public void setTransactionID(String transactionID) {
                TransactionID = transactionID;
        }

        public String getStaffID() {
                return StaffID;
        }

        public void setStaffID(String staffID) {
                StaffID = staffID;
        }

        public String getStaffName() {
                return StaffName;
        }

        public void setStaffName(String staffName) {
                StaffName = staffName;
        }

        public Date getTransactionTime() {
                return TransactionTime;
        }

        public void setTransactionTime(Date transactionTime) {
                TransactionTime = transactionTime;
        }

        public TransactionHeader(String transactionID, String staffID, String staffName, Date transactionTime) {
                TransactionID = transactionID;
                StaffID = staffID;
                StaffName = staffName;
                TransactionTime = transactionTime;
        }
}
