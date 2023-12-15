package controller;

import javafx.scene.control.Alert;
import model.Job;
import model.PC;
import model.PCBook;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PCBookController {
        public static boolean addNewBook(String bookID, String pcID, String userID, Date date) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                LocalDate localDate = LocalDate.now();

                if (bookID == null || pcID == null || userID == null || date == null) {
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please select user and PC");
                        alert.showAndWait();
                        return false;
                }

                // Convert java.sql.Date to java.util.Date
                java.util.Date utilDate = new Date(date.getTime());

                LocalDate bookLocalDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (bookLocalDate.isBefore(localDate)) {
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please select a valid date (after today)");
                        alert.showAndWait();
                        return false;
                }

                PCBook.addNewBook(bookID, pcID, userID, date);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Book PC");
                alert.setHeaderText("Book PC Success");
                alert.setContentText("Book PC Success");
                alert.showAndWait();
                return true;
        }

        // gettPCBookedDetail(PCID)
        public static PC getPCBookedDetail(String pcID) {
                if (pcID == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("PC ID cannot be empty");
                        alert.showAndWait();
                        return null;
                }
                return PCBook.getPCBookedDetail(pcID);
        }

        // assignUserToNewPC(String userID, String pcID)
        public static boolean assignUserToNewPC(String userID, String pcID) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Alert error = new Alert(Alert.AlertType.ERROR);

                if (userID == null || pcID == null) {
                        error.setTitle("Error");
                        error.setHeaderText("Error");
                        error.setContentText("Please select user and PC");
                        error.showAndWait();
                        return false;
                } else {
                        // Check if the selected PC is booked
//                        if (PCBookController.getPCBookedDetail(pcID) != null) {
                                List<PC> pcList = PCController.getAllPCData();
                                PC usablePC = null;
                                // find usable pc
                                for (PC pc : pcList) {
                                        PC temp = PCBookController.getPCBookedDetail(pc.getPCID());
                                        if (temp == null && pc.getPCCondition().equals("Usable") && !pc.getPCID().equals(pcID)) {
//                                                System.out.println(temp.getPCID() + " ][" +  temp.getPCCondition());
//                                                usablePC = temp;
                                                PCBook.assignUserToNewPC(userID,pc.getPCID());
                                                alert.setTitle("Success");
                                                alert.setHeaderText("Success");
                                                alert.setContentText("Successfully assigned a user to " + pc.getPCID());
                                                alert.showAndWait();
                                                return true;
                                        }
                                }

                                // if there's no usable pc, pc reassign failed
                                if (usablePC == null) {
                                        error.setTitle("Error");
                                        error.setHeaderText("Failed");
                                        error.setContentText("[$$] There's no usable PC");
                                        error.showAndWait();
                                        return false;
                                }
                }
                return false;
        }

        // getAllPCBookData()
        public static List<PCBook> getAllPCBookedData() {
                return PCBook.getAllPCBookedData();
        }

        // deleteBookData(String bookID)
        public static void deleteBookData(String bookID) {
                if (bookID == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Book ID cannot be empty");
                        alert.showAndWait();
                }
                PCBook.deleteBookData(bookID);
        }
}
