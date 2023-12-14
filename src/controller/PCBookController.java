package controller;

import javafx.scene.control.Alert;
import model.Job;
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
                        // Check if the selected PC is booked

                        if (PCBookController.getPCBookedDetail(pcID) != null) {
                                List<PC> pcList = PCController.getAllPCData();
                                List<PCBook> pcBookList = PCBookController.getAllPCBookedData();
                                List<PC> usablePCList = new ArrayList<>();

                                for (PC pc : pcList) {
                                        if (pc.getPCCondition().equals("Usable")) {
                                                usablePCList.add(pc);
                                        }
                                }

                                for (PC pc : usablePCList) {
                                        for (PCBook pcBook : pcBookList) {
                                                System.out.println("KEREN JUGA YA:" + pc.getPCID() + "  | " + pcID);
                                                if (!pc.getPCID().equals(pcBook.getPCID())) {
                                                        System.out.println("JALAN BOS:" + pc.getPCID() + "  | " + pcID);
                                                        PCBook.assignUserToNewPC(userID, pc.getPCID());
                                                        alert.setTitle("Jalan Bos");
                                                        alert.setHeaderText("Jalan Bos");
                                                        alert.setContentText("Jalan Bos");
                                                        alert.showAndWait();
                                                        return;
                                                }
                                        }

                                }
                        }
                        alert.setTitle("HEHE");
                        alert.setHeaderText("Failed");
                        alert.setContentText("There's no usable PC");
                        alert.showAndWait();
                }
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
