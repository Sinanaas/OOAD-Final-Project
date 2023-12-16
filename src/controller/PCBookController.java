package controller;

import helper.Helper;
import javafx.scene.control.Alert;
import model.PC;
import model.PCBook;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PCBookController {
        public static boolean addNewBook(String bookID, String pcID, String userID, Date date) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                LocalDate localDate = LocalDate.now();

                if (bookID == null || pcID == null || userID == null || date == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please fill in all the fields");
                        return false;
                }

                Date utilDate = new Date(date.getTime());
                LocalDate bookLocalDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (bookLocalDate.isBefore(localDate)) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a valid date");
                        return false;
                }
                PCBook.addNewBook(bookID, pcID, userID, date);
                Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Success", "Successfully booked a PC");
                return true;
        }

        // gettPCBookedDetail(PCID)
        public static PC getPCBookedDetail(String pcID) {
                if (pcID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a PC");
                        return null;
                }
                return PCBook.getPCBookedDetail(pcID);
        }

        // assignUserToNewPC(String userID, String pcID)
        public static boolean assignUserToNewPC(String userID, String pcID) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Alert error = new Alert(Alert.AlertType.ERROR);

                if (userID == null || pcID == null) {
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a PC");
                        return false;
                } else {
                                List<PC> pcList = PCController.getAllPCData();
                                PC usablePC = null;

                                for (PC pc : pcList) {
                                        PC temp = PCBookController.getPCBookedDetail(pc.getPCID());
                                        if (temp == null && pc.getPCCondition().equals("Usable") && !pc.getPCID().equals(pcID)) {
                                                PCBook.assignUserToNewPC(userID,pc.getPCID());
                                                Helper.showAlert(Alert.AlertType.INFORMATION, "Success", "Success", "Successfully assigned a PC");
                                                return true;
                                        }
                                }

                                if (usablePC == null) {
                                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "There's no usable PC");
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
                        Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select a PC");
                        return;
                }
                PCBook.deleteBookData(bookID);
        }
}
