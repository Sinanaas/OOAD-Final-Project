package model;

import java.util.Date;

public class TransactionHeader {
        private String TransactionID;
        private String StaffID;
        private String StaffName;
        private Date TransactionTime;

        // getter, setter, constructor
        public String getTransactionID() {
                return TransactionID;
        }

        public void setTransactionID(String transactionID) {
                TransactionID = transactionID;
        }

        public String getStaffID() {
                return StaffID;
        }

        public void setStaffID(String staffID) {
                StaffID = staffID;
        }

        public String getStaffName() {
                return StaffName;
        }

        public void setStaffName(String staffName) {
                StaffName = staffName;
        }

        public Date getTransactionTime() {
                return TransactionTime;
        }

        public void setTransactionTime(Date transactionTime) {
                TransactionTime = transactionTime;
        }

        public TransactionHeader(String transactionID, String staffID, String staffName, Date transactionTime) {
                TransactionID = transactionID;
                StaffID = staffID;
                StaffName = staffName;
                TransactionTime = transactionTime;
        }
}
