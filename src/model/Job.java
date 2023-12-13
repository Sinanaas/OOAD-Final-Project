package model;

public class Job {
        private String JobID;
        private String UserID;
        private String PCID;
        private String JobStatus;



        // getter, setter, constructor
        public String getJobID() {
                return JobID;
        }

        public void setJobID(String jobID) {
                JobID = jobID;
        }

        public String getUserID() {
                return UserID;
        }

        public void setUserID(String userID) {
                UserID = userID;
        }

        public String getPCID() {
                return PCID;
        }

        public void setPCID(String PCID) {
                this.PCID = PCID;
        }

        public String getJobStatus() {
                return JobStatus;
        }

        public void setJobStatus(String jobStatus) {
                JobStatus = jobStatus;
        }

        public Job(String jobID, String userID, String PCID, String jobStatus) {
                JobID = jobID;
                UserID = userID;
                this.PCID = PCID;
                JobStatus = jobStatus;
        }
}
