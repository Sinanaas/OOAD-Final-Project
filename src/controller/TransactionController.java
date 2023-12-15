package controller;

import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;

import java.util.List;

public class TransactionController {
        // getAllTransactionData()


        // addNewTransaction(TransactionID, List PCBook, StaffID)
        public static void addNewTransaction(String transactionID, List<PCBook> pcBookList, String staffID) {
                if (pcBookList.size() == 0) {
                        return;
                }
                TransactionHeader.addNewTransactionHeader(transactionID, staffID);
                TransactionDetail.addNewTransactionDetail(transactionID, pcBookList);
        }

        // getAllTransactionDetail(TransactionID)


        // getUserTransactionDetail(String UserID)


}
