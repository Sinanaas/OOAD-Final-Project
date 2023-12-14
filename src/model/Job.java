package model;

import database.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Job {
        private String JobID;
        private String UserID;
        private String PCID;
        private String JobStatus;

        public static String generateID() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM JOB ORDER BY JOBID DESC LIMIT 1";
                ResultSet rs = connect.executeQuery(query);

                String nextID = "JB001";

                try {
                        if (rs.next()) {
                                String lastID = rs.getString("JobID");
                                String lastDigitID = lastID.substring(2);
                                Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
                                nextID = "JB" + String.format("%03d", (lastDigitIDInt + 1));
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return nextID;
        }

        // addNewJob(UserID, PCID)
        public static void addNewJob(String userID, String pcID) {
                String jobID = generateID();
                String unComplete = "UnComplete";
                Connect connect = Connect.getConnection();
                String query = String.format("INSERT INTO JOB VALUES ('%s', '%s', '%s', '%s')", jobID, userID, pcID, unComplete);
                connect.executeUpdate(query);
                System.out.println("TEST2:" + userID + " " + pcID);
        }

        // updateJobStatus(JobID, JobStatus)
        public static void updateJobStatus(String jobID, String jobStatus) {
                Connect connect = Connect.getConnection();
                String query = String.format("UPDATE JOB SET JobStatus = '%s' WHERE JobID = '%s'", jobStatus, jobID);
                connect.executeUpdate(query);
        }

        // getPCOnWorkingList(PCID)
        public static List<PC> getPCOnWorkingList(String pcID) {
                return null;
        }

        // getTechnicianJob(UserID)
        public static List<Job> getTechnicianJob(String userID) {
                Connect connect = Connect.getConnection();
                String query = String.format("SELECT * FROM JOB WHERE UserID = '%s'", userID);
                ResultSet rs = connect.executeQuery(query);
                List<Job> jobList = null;
                try {
                        while (rs.next()) {
                                String jobID = rs.getString("JobID");
                                String pcID = rs.getString("PCID");
                                String jobStatus = rs.getString("JobStatus");

                                Job job = new Job(jobID, userID, pcID, jobStatus);
                                jobList.add(job);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return jobList;
        }

        // getAllJobData()
        public static List<Job> getAllJobData() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM JOB";
                ResultSet rs = connect.executeQuery(query);
                List<Job> jobList = new ArrayList<>(); // Initialize the list

                try {
                        while (rs.next()) {
                                String jobID = rs.getString("JobID");
                                String userID = rs.getString("UserID");
                                String pcID = rs.getString("PCID");
                                String jobStatus = rs.getString("JobStatus");

                                Job job = new Job(jobID, userID, pcID, jobStatus);
                                jobList.add(job);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return jobList;
        }



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
