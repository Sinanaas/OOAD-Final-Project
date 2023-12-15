package view;

import controller.PCBookController;
import controller.PCController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.PC;

import java.util.List;

public class PCManagementPage {
        private static PCManagementPage pcManagementPage;
        private Scene scene;
        private Label title;
        private TableView pcTable;
        private TableColumn<PC, String> pcID, pcCondition;
        private TextField pcIDField;
        private ComboBox<String> pcConditionField;
        private VBox vb;
        private HBox hb;
        private Button addPC, updatePC, deletePC, back;

        public static PCManagementPage getInstance() {
                return pcManagementPage = pcManagementPage == null ? new PCManagementPage() : pcManagementPage;
        }

        public PCManagementPage() {
                initialize();
                addEventListener();
        }

        public void _repaint() {
                pcTable.getItems().clear();
                pcTable.getItems().addAll(
                        PCController.getAllPCData()
                );
        }

        private void addEventListener() {
                back.setOnAction(e -> {
                        AdminHomePage adminPage = AdminHomePage.getInstance();
                        adminPage.show();
                        _repaint();
                });

                // pada add PC,condition akan otomatis Usable, karena PC baru...
                addPC.setOnAction(e -> {
                        String pcID = pcIDField.getText();
                        PCController.addNewPC(pcID);
                        _repaint();
                        pcIDField.clear();
                        pcConditionField.setValue(null);
                });

                updatePC.setOnAction(e -> {
                        String pcID = pcIDField.getText();
                        String pcCondition = pcConditionField.getValue();
                        String pcSelectedID = ((PC) pcTable.getSelectionModel().getSelectedItem()).getPCID();
                        String selectedPCCondition = ((PC) pcTable.getSelectionModel().getSelectedItem()).getPCCondition();

                        if (PCBookController.getPCBookedDetail(pcID) != null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Error");
                                alert.setContentText("PC is booked");
                                alert.showAndWait();
                                return;
                        }

                        PCController.updatePCCondition(pcID, pcCondition);
                        _repaint();
                        pcIDField.clear();
                        pcConditionField.setValue(null);
                });

                deletePC.setOnAction(e -> {
                        String pcID = pcIDField.getText();
                        PCController.deletePC(pcID);
                        _repaint();
                        pcIDField.clear();
                        pcConditionField.setValue(null);
                });

                pcTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                PC pc = (PC) newSelection;
                                pcIDField.setText(pc.getPCID());
                                pcConditionField.setValue(pc.getPCCondition());
                        } else {
                                // If no row is selected, you may want to clear the text fields
                                pcIDField.clear();
                                pcConditionField.setValue(null);
                        }
                });

        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
                _repaint();
        }

        private void initialize() {
                title = new Label("PC Management");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                pcIDField = new TextField();
                pcIDField.setPromptText("PC ID");

                pcConditionField = new ComboBox<>();
                pcConditionField.getItems().addAll("Usable", "Maintenance", "Broken");
                pcConditionField.setPromptText("PC Condition");

                addPC = new Button("Add PC");
                updatePC = new Button("Update PC");
                deletePC = new Button("Delete PC");

                hb = new HBox();
                hb.getChildren().addAll(pcIDField, pcConditionField, addPC, updatePC, deletePC);
                hb.setSpacing(10);

                // table
                pcTable = new TableView();
                pcID = new TableColumn("PC ID");
                pcID.setMinWidth(388);
                pcCondition = new TableColumn("PC Condition");
                pcCondition.setMinWidth(388);
                pcID.setCellValueFactory(new PropertyValueFactory<>("PCID"));
                pcCondition.setCellValueFactory(new PropertyValueFactory<>("PCCondition"));

                pcTable.getColumns().addAll(pcID, pcCondition);
                pcTable.getItems().addAll(
                        PCController.getAllPCData()
                );

                back = new Button("Back");
                back.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                vb = new VBox();
                vb.getChildren().addAll(back, title, pcTable, hb);
                vb.setSpacing(10);
                vb.setPadding(new Insets(15, 12, 15, 12));

                scene = new Scene(vb, 800, 600);
        }

}
