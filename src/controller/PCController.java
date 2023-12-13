package controller;

import javafx.collections.ObservableList;
import model.PC;

public class PCController {
        public static ObservableList getAllPCData() {
                return PC.getAllPCData();
        }

        public  static PC getPCDetail(String PCID) {
                if (PCID == null) {
                        return null;
                }
                return PC.getPCDetail(PCID);
        }
}
