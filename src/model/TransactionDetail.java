package model;

import database.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDetail {
        private String TransactionID;
        private String PCID;
        private String CustomerName;
        private Date BookedTime;

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

        public static List<TransactionDetail> getUserTransactionDetail(String userID) {
                Connect connect = Connect.getConnection();
                String query = String.format("SELECT * FROM TransactionDetail td JOIN Users u ON td.CustomerName = u.username WHERE u.UserID = '%s'", userID);

                ResultSet rs = connect.executeQuery(query);
                List<TransactionDetail> transactionDetailList = new ArrayList<>();

                try {
                        while (rs.next()) {
                                String transactionID = rs.getString("TransactionID");
                                String pcID = rs.getString("PCID");
                                String customerName = rs.getString("CustomerName");
                                Date bookedTime = rs.getDate("BookedTime");

                                TransactionDetail transactionDetail = new TransactionDetail(transactionID, pcID, customerName, bookedTime);
                                transactionDetailList.add(transactionDetail);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

                return transactionDetailList;
        }


        // getAllTransactionDetail(TransactionID)
        public static List<TransactionDetail> getAllTransactionDetail(String transactionID) {
                Connect connect = Connect.getConnection();
                String query = String.format("SELECT * FROM TransactionDetail WHERE TransactionID = '%s'", transactionID);
                ResultSet rs = connect.executeQuery(query);
                List<TransactionDetail> transactionDetailList = new ArrayList<>();
                try {
                        while (rs.next()) {
                                String pcID = rs.getString("PCID");
                                String customerName = rs.getString("CustomerName");
                                Date bookedTime = rs.getDate("BookedTime");

                                TransactionDetail transactionDetail = new TransactionDetail(transactionID, pcID, customerName, bookedTime);
                                transactionDetailList.add(transactionDetail);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return transactionDetailList;
        }

        // addNewTransactionDetail(TransactionID, List PCBook)
        public static void addNewTransactionDetail(String transactionID, List<PCBook> pcBookList) {
                Connect connect = Connect.getConnection();
                for (PCBook pcBook : pcBookList) {
                        List<User> userList = User.getAllUserData();
                        String customerName = "";
                        for (User user : userList) {
                                if (user.getUserID().equals(pcBook.getUserID())) {
                                        customerName = user.getUsername();
                                        break;
                                }
                        }

                        String query = String.format("INSERT INTO TransactionDetail VALUES('%s', '%s', '%s', '%s')", transactionID, pcBook.getPCID(), customerName, pcBook.getBookedDate());
                        connect.executeUpdate(query);
                }
        }

        // getter, setter, constructor
        public String getTransactionID() {
                return TransactionID;
        }

        public void setTransactionID(String transactionID) {
                TransactionID = transactionID;
        }

        public String getPCID() {
                return PCID;
        }

        public void setPCID(String PCID) {
                this.PCID = PCID;
        }

        public String getCustomerName() {
                return CustomerName;
        }

        public void setCustomerName(String customerName) {
                CustomerName = customerName;
        }

        public Date getBookedTime() {
                return BookedTime;
        }

        public void setBookedTime(Date bookedTime) {
                BookedTime = bookedTime;
        }

        public TransactionDetail(String transactionID, String PCID, String customerName, Date bookedTime) {
                TransactionID = transactionID;
                this.PCID = PCID;
                CustomerName = customerName;
                BookedTime = bookedTime;
        }
}
