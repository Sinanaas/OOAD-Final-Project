package controller;

import javafx.scene.control.Alert;
import model.PC;
import model.PCBook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PCBookController {
        public static void addNewBook(String bookID, String pcID, String userID, Date date) {
                if (bookID == null || pcID == null || userID == null || date == null) {
                        throw new IllegalArgumentException("All arguments must not be null");
                }
                PCBook.addNewBook(bookID, pcID, userID, date);
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
        public static void assignUserToNewPC(String userID, String pcID) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Alert error = new Alert(Alert.AlertType.ERROR);
                if (userID == null || pcID == null) {
                        error.setTitle("Error");
                        error.setHeaderText("Error");
                        error.setContentText("Please select user and PC");
                        error.showAndWait();
                } else {
                        PCBook.assignUserToNewPC(userID, pcID);
                        alert.setTitle("Success");
                        alert.setHeaderText("Success");
                        alert.setContentText("Assign user to new PC successfully");
                        alert.showAndWait();
                }
        }

        // getAllPCBookData()
        public static List<PCBook> getAllPCBookedData() {
                return PCBook.getAllPCBookedData();
        }
}
