package controller;

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

        public static PC getPCBookedDetail(String pcID) {
                if (pcID == null) {
                        throw new IllegalArgumentException("PC ID must not be null");
                }
                return PCBook.getPCBookedDetail(pcID);
        }
}
