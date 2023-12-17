package model;

import database.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PCBook {
        private String BookID;
        private String PCID;
        private String UserID;
        private Date BookedDate;

        public static String generateID() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM PCBook ORDER BY BookID DESC LIMIT 1";
                ResultSet rs = connect.executeQuery(query);

                String nextID = "BK001";

                try {
                        if (rs.next()) {
                                String lastID = rs.getString("BookID");
                                String lastDigitID = lastID.substring(2);
                                Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
                                nextID = "BK" + String.format("%03d", (lastDigitIDInt + 1));
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return nextID;
        }

        // addNewBook(String bookID, String pcID, String userID, Date date)
        public static void addNewBook(String bookID, String pcID, String userID, Date date) {
                Connect connect = Connect.getConnection();
                String query = String.format("INSERT INTO PCBook VALUES('%s', '%s', '%s', '%s')", bookID, pcID, userID, date);
                connect.executeUpdate(query);
        }

        // deleteBookData(String bookID)
        public static void deleteBookData(String bookID) {
                Connect connect = Connect.getConnection();
                String query = String.format("DELETE FROM PCBook WHERE BookID = '%s'", bookID);
                connect.executeUpdate(query);
        }

        // getPCBookedData(PCID, date)
        public static ResultSet getPCBookedData(String pcID, Date date) {
                return null;
        }

        // gettPCBookedDetail(PCID)
        public static PC getPCBookedDetail(String pcID) {
                Connect connect = Connect.getConnection();
                String query = String.format("SELECT PC.PCID, PC.PCCondition FROM PCBook JOIN PC ON PCBook.PCID = PC.PCID WHERE PCBook.PCID = '%s'", pcID);
                ResultSet rs = connect.executeQuery(query);
                try {
                        if (rs.next()) {
                                String PCIDResult = rs.getString("PCID");
                                String PCCondition = rs.getString("PCCondition");
                                PC bookedPC = new PC(PCIDResult, PCCondition);
                                return bookedPC;
                        } else {
                                return null;
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }



        // finishBook(List PCBook)
        public static void finishBook(List<PCBook> bookList) {

        }

        // getAllPCBookedData()
        public static List<PCBook> getAllPCBookedData() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM PCBook";
                ResultSet rs = connect.executeQuery(query);
                List<PCBook> pcBookList = new ArrayList<>(); // Initialize as an empty ArrayList
                try {
                        while (rs.next()) {
                                String bookID = rs.getString("BookID");
                                String pcID = rs.getString("PCID");
                                String userID = rs.getString("UserID");
                                Date bookedDate = rs.getDate("BookedDate");
                                pcBookList.add(new PCBook(bookID, pcID, userID, bookedDate));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return pcBookList;
        }

        // getPCBookedByDate(StartDate)
        public static ResultSet getPCBookedByDate(Date date) {
                return null;
        }

        // assignUserToNewPC(String userID, String pcID,)
        public static void assignUserToNewPC(String bookID, String pcID) {
                Connect connect = Connect.getConnection();

                String query = String.format("UPDATE PCBook SET PCID = '%s' WHERE BookID = '%s'", pcID, bookID);
                connect.executeUpdate(query);
//                try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
//                        preparedStatement.setString(1, pcID);
//                        preparedStatement.setString(2, userID);
//                        preparedStatement.executeUpdate();
//                } catch (SQLException e) {
//                        e.printStackTrace();
//                }
        }


        // getter, setter, and constructor

        public String getBookID() {
                return BookID;
        }

        public void setBookID(String bookID) {
                BookID = bookID;
        }

        public String getPCID() {
                return PCID;
        }

        public void setPCID(String PCID) {
                this.PCID = PCID;
        }

        public String getUserID() {
                return UserID;
        }

        public void setUserID(String userID) {
                UserID = userID;
        }

        public Date getBookedDate() {
                return BookedDate;
        }

        public void setBookedDate(Date bookedDate) {
                BookedDate = bookedDate;
        }

        public PCBook(String bookID, String PCID, String userID, Date bookedDate) {
                BookID = bookID;
                this.PCID = PCID;
                UserID = userID;
                BookedDate = bookedDate;
        }
}
