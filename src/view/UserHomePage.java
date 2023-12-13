package view;

import controller.PCBookController;
import controller.PCController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import main.MainStage;
import model.PC;
import model.PCBook;

public class UserHomePage {
        private static UserHomePage userHomePage;
        private Scene scene;
        private Button reportButton;
        public static UserHomePage getInstance() {
                return userHomePage = userHomePage == null ? new UserHomePage() : userHomePage;
        }

        private UserHomePage() {
                initialize();
                addEventListener();
        }

        private void addEventListener() {
                reportButton.setOnAction((ActionEvent event) -> {
                        ReportPage reportPage = ReportPage.getInstance();
                        reportPage.show();
                });
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void initialize() {
                Label label = new Label("User Home Page");
                label.setFont(Font.font("Arial", FontWeight.BOLD, 36));
                reportButton = new Button("Report PC");
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER_LEFT); // Set alignment to center
                vb.setPadding(new Insets(15, 12, 15, 12));
                vb.setSpacing(10);
                vb.getChildren().add(label);
                TableColumn<PC, String> pcIDColumn = new TableColumn<>("ID");
                TableColumn<PC, String> pcConditionColumn = new TableColumn<>("Condition");
                TableColumn<PC, Void> bookPCColumn = new TableColumn<>("Book PC");
                pcIDColumn.setMinWidth(300);
                pcConditionColumn.setMinWidth(300);
                bookPCColumn.setMinWidth(200);
                pcIDColumn.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                pcConditionColumn.setCellValueFactory(new PropertyValueFactory<>("PCCondition"));

                Callback<TableColumn<PC, Void>, TableCell<PC, Void>> cellFactory = new Callback<>() {
                        @Override
                        public TableCell<PC, Void> call(final TableColumn<PC, Void> param) {
                                final TableCell<PC, Void> cell = new TableCell<>() {
                                        private final Button btn = new Button("Book PC");

                                        {
                                                btn.setOnAction((ActionEvent event) -> {
                                                        PC pc = getTableView().getItems().get(getIndex());
                                                        if (pc.getPCCondition().equals("Broken")) {
                                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                                alert.setTitle("Error");
                                                                alert.setHeaderText("PC is broken");
                                                                alert.setContentText("Please select another PC");
                                                                alert.showAndWait();
                                                                return;
                                                        }

                                                        if (pc.getPCCondition().equals("Maintenance")) {
                                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                                alert.setTitle("Error");
                                                                alert.setHeaderText("PC is being maintained");
                                                                alert.setContentText("Please select another PC");
                                                                alert.showAndWait();
                                                                return;
                                                        }

                                                        if (PCBookController.getPCBookedDetail(pc.getPCID()) == null ) {
                                                                BookPCPage bookPCPage = BookPCPage.getInstance(pc.getPCID());
                                                                bookPCPage.show();
                                                        } else {
                                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                                alert.setTitle("Error");
                                                                alert.setHeaderText("PC is already booked");
                                                                alert.setContentText("Please select another PC");
                                                                alert.showAndWait();
                                                                return;
                                                        }
                                                });
                                        }

                                        @Override
                                        public void updateItem(Void item, boolean empty) {
                                                super.updateItem(item, empty);
                                                if (empty) {
                                                        setGraphic(null);
                                                } else {
                                                        setGraphic(btn);
                                                }
                                        }
                                };
                                return cell;
                        }
                };

                bookPCColumn.setCellFactory(cellFactory);

                TableView<PC> pcTable = new TableView<>();
                pcTable.getColumns().addAll(pcIDColumn, pcConditionColumn, bookPCColumn);
                pcTable.getItems().addAll(PCController.getAllPCData());
                pcTable.setPrefHeight(400);
                pcTable.setPrefWidth(800);
                vb.getChildren().addAll(pcTable, reportButton);
                scene = new Scene(vb, 800, 600);
        }
}