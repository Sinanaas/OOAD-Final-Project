package model;

import java.util.Date;

public class TransactionDetail {
        private String TransactionID;
        private String PCID;
        private String CustomerName;
        private Date BookedTime;


        // getter, setter, constructor
        public String getTransactionID() {
                return TransactionID;
        }

        public void setTransactionID(String transactionID) {
                TransactionID = transactionID;
        }

        public String getPCID() {
                return PCID;
        }

        public void setPCID(String PCID) {
                this.PCID = PCID;
        }

        public String getCustomerName() {
                return CustomerName;
        }

        public void setCustomerName(String customerName) {
                CustomerName = customerName;
        }

        public Date getBookedTime() {
                return BookedTime;
        }

        public void setBookedTime(Date bookedTime) {
                BookedTime = bookedTime;
        }

        public TransactionDetail(String transactionID, String PCID, String customerName, Date bookedTime) {
                TransactionID = transactionID;
                this.PCID = PCID;
                CustomerName = customerName;
                BookedTime = bookedTime;
        }
}
