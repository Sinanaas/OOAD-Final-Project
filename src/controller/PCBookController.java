package controller;

import javafx.scene.control.Alert;
import model.PC;
import model.PCBook;

import java.util.Date;

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
}
