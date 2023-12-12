package model;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PC {
        private String PCID;
        private String PCCondition;

        public static String generateID() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM PC ORDER BY PCID DESC LIMIT 1";
                ResultSet rs = connect.executeQuery(query);

                String nextID = "PC001";

                try {
                        if (rs.next()) {
                                String lastID = rs.getString("PCID");
                                String lastDigitID = lastID.substring(2);
                                Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
                                nextID = "PC" + String.format("%03d", (lastDigitIDInt + 1));
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return nextID;
        }

        //getAllPCData()
        public static ObservableList<PC> getAllPCData() {
                Connect connect = Connect.getConnection();
                String query = "SELECT * FROM PC";
                ResultSet rs = connect.executeQuery(query);

                try {
                        ObservableList<PC> pcList = FXCollections.observableArrayList();
                        while (rs.next()) {
                                String PCID = rs.getString("PCID");
                                String PCCondition = rs.getString("PCCondition");
                                PC pc = new PC(PCID, PCCondition);
                                pcList.add(pc);
                        }
                        for (PC pc : pcList) {
                                System.out.println(pc.getPCID() + " | " + pc.getPCCondition());
                        }
                        return pcList;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        // UpdatePCCondition(PCID, Condition)
        public static void updatePCCondition(String PCID, String PCCondition) {
                Connect connect = Connect.getConnection();
                String query = String.format("UPDATE PC SET PCCondition = '%s' WHERE PCID = '%s'", PCCondition, PCID);
                connect.executeUpdate(query);
        }
        // DeletePC(PCID)
        public static void deletePC(String PCID) {
                Connect connect = Connect.getConnection();
                String query = String.format("DELETE FROM PC WHERE PCID = '%s'", PCID);
                connect.executeUpdate(query);
        }

        // AddNewPC(PCID)
        public static void addNewPC(String PCID) {
                Connect connect = Connect.getConnection();
                String query = String.format("INSERT INTO PC VALUES('%s', 'Available')", PCID);
                connect.executeUpdate(query);
        }

        // findPC(PCID)
        public static PC findPC(String PCID) {
                Connect connect = Connect.getConnection();
                String query = String.format("SELECT * FROM PC WHERE PCID = '%s'", PCID);
                ResultSet rs = connect.executeQuery(query);
                try {
                        if (rs.next()) {
                                String pcID = rs.getString("PCID");
                                String pcCondition = rs.getString("PCCondition");
                                PC pc = new PC(pcID, pcCondition);
                                return pc;
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        // GetPCDetail(PCID)
        public static PC getPCDetail(String PCID) {
                Connect connect = Connect.getConnection();
                String query = String.format("SELECT * FROM PC WHERE PCID = '%s'", PCID);
                ResultSet rs = connect.executeQuery(query);
                try {
                        if (rs.next()) {
                                String pcID = rs.getString("PCID");
                                String pcCondition = rs.getString("PCCondition");
                                PC pc = new PC(pcID, pcCondition);
                                return pc;
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public PC(String PCID, String PCCondition) {
                this.PCID = PCID;
                this.PCCondition = PCCondition;
        }

        public String getPCID() {
                return PCID;
        }

        public void setPCID(String PCID) {
                this.PCID = PCID;
        }

        public String getPCCondition() {
                return PCCondition;
        }

        public void setPCCondition(String PCCondition) {
                this.PCCondition = PCCondition;
        }
}