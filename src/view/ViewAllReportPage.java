package view;

import controller.ReportController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.Report;

import java.util.ArrayList;

public class ViewAllReportPage {
        private static ViewAllReportPage viewAllReport;
        private Scene scene;
        private Label title;
        private VBox vb;
        private TableView<Report> reportTable;
        private TableColumn<Report, String> reportIdColumn, userRoleColumn, pcIdColumn, reportNoteColumn, reportDateColumn;
        public static ViewAllReportPage getInstance() {
                return viewAllReport = viewAllReport == null ? new ViewAllReportPage() : viewAllReport;
        }
        public ViewAllReportPage() {
                initialize();
                addEventListener();
        }
        private void addEventListener() {
        }
        private void initialize() {
                title = new Label("View All Report Page");
                title.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 24));

                // table
                reportTable = new TableView<>();
                reportIdColumn = new TableColumn<>("Report ID");
                reportIdColumn.setMinWidth(155);
                userRoleColumn = new TableColumn<>("User Role");
                userRoleColumn.setMinWidth(155);
                pcIdColumn = new TableColumn<>("PC ID");
                pcIdColumn.setMinWidth(155);
                reportNoteColumn = new TableColumn<>("Report Note");
                reportNoteColumn.setMinWidth(155);
                reportDateColumn = new TableColumn<>("Report Date");
                reportDateColumn.setMinWidth(155);

                reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("ReportID"));
                userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
                pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                reportNoteColumn.setCellValueFactory(new PropertyValueFactory<>("ReportNote"));
                reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("ReportDate"));

                reportTable.setEditable(false);
                reportTable.getColumns().addAll(reportIdColumn, userRoleColumn, pcIdColumn, reportNoteColumn, reportDateColumn);
                ArrayList<Report> reportList = new ArrayList<>();
                reportList.addAll(ReportController.getAllReportData());
                System.out.println(reportList.size());
                if (reportList.size() > 0) {
                        reportTable.getItems().addAll(reportList);
                }


                vb = new VBox();
                vb.getChildren().addAll(title, reportTable);
                vb.setSpacing(10);
                vb.setPadding(new javafx.geometry.Insets(15, 12, 15, 12));
                vb.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                scene = new Scene(vb, 800, 600);
        }
        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }
}