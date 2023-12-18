package controller;

import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TransactionController {
        // getAllTransactionData()


        // addNewTransaction(TransactionID, List PCBook, StaffID)
        public static void addNewTransaction(String transactionID, List<PCBook> pcBookList, String staffID) {
                if (pcBookList.size() == 0) {
                        return;
                }
                List<User> userList = User.getAllUserData();
                String staffName = "";
                for (User user : userList) {
                        if (user.getUserID().equals(staffID)) {
                                staffName = user.getUsername();
                                break;
                        }
                }
                Timestamp now = Timestamp.from(Instant.now());
                TransactionHeader.addNewTransactionHeader(staffID, now);
                List<TransactionHeader> transactionHeaderList = TransactionHeader.getAllTransactionHeaderData();
                String tempTransactionID  = transactionHeaderList.get(transactionHeaderList.size() - 1).getTransactionID();
                TransactionDetail.addNewTransactionDetail(tempTransactionID, pcBookList);
        }

        // getAllTransactionDetail(TransactionID)
        public static List<TransactionDetail> getAllTransactionDetail(String transactionID) {
                List<TransactionDetail> transactionDetailList = TransactionDetail.getAllTransactionDetail(transactionID);
                if (transactionDetailList != null) {
                        return transactionDetailList;
                } else {
                        return null;
                }
        }

        // getUserTransactionDetail(String UserID)
        public static List<TransactionDetail> getUserTransactionDetail(String userID) {
                return TransactionDetail.getUserTransactionDetail(userID);
        }


}
