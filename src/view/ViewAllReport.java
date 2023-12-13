package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import main.MainStage;

public class ViewAllReport {
        private static ViewAllReport viewAllReport;
        private Scene scene;
        private Label title;
        public static ViewAllReport getInstance() {
                return viewAllReport = viewAllReport == null ? new ViewAllReport() : viewAllReport;
        }
        public ViewAllReport() {
                initialize();
                addEventListener();
        }
        private void addEventListener() {
        }
        private void initialize() {
                title = new Label("View All Report Page");
        }
        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }
}
