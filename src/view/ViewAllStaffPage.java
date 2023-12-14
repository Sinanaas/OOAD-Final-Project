package view;

import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import main.MainStage;
import model.User;

import java.util.stream.Collectors;

public class ViewAllStaffPage {
        private static ViewAllStaffPage viewAllStaffPage;
        private Scene scene;
        private Label title;
        private TableView<User> userTable;
        private Button backButton;
        VBox vb;
        HBox hb;

        public static ViewAllStaffPage getInstance() {
                return viewAllStaffPage = viewAllStaffPage == null ? new ViewAllStaffPage() : viewAllStaffPage;
        }

//        private void _setScene(Stage primaryStage) {
//                this.primaryStage = primaryStage;
//                primaryStage.setScene(scene);
//                _repaint();
//        }


        public void _repaint() {
                userTable.getItems().clear();
                userTable.getItems().addAll(
                        UserController.getAllUserData().stream()
                                .filter(user -> user.getUserRole() == 1 || user.getUserRole() == 2 || user.getUserRole() == 3)
                                .collect(Collectors.toList())
                );
        }

        public ViewAllStaffPage() {
                initialize();
                addEventListener();
                _repaint();
        }

        public void show() {
                _repaint();
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }

        private void addEventListener() {
                backButton.setOnAction(event -> {
                        AdminHomePage adminPage = AdminHomePage.getInstance();
                        adminPage.show();
                });
        }

        private void initialize() {
                title = new Label("View All Staff Page");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                backButton = new Button("Back");
                backButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                vb = new VBox();
                vb.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                vb.setPadding(new Insets(15, 12, 15, 12));
                vb.setSpacing(10);
                // table
                TableColumn<User, String> userIDColumn = new TableColumn<>("ID");
                TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
                TableColumn<User, String> userRoleColumn = new TableColumn<>("User Role");
                TableColumn<User, Void> changeRoleColumn = new TableColumn<>("Change Role");

                userIDColumn.setMinWidth(175);
                userIDColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));

                usernameColumn.setMinWidth(175);
                usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));

                userRoleColumn.setMinWidth(175);
                userRoleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoleName()));

                changeRoleColumn.setMinWidth(149);


                Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
                        @Override
                        public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                                final TableCell<User, Void> cell = new TableCell<>() {
                                        private final Button btn = new Button("Change Role");

                                        {
                                                btn.setOnAction((ActionEvent event) -> {
                                                        User user = getTableView().getItems().get(getIndex());
                                                        ChangeRolePage changeRolePage = ChangeRolePage.getInstance(user);
                                                        changeRolePage.show();
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

                userTable = new TableView<>();
                userTable.getColumns().addAll(userIDColumn, usernameColumn, userRoleColumn, changeRoleColumn);
                userTable.setEditable(false);
                changeRoleColumn.setCellFactory(cellFactory);
                userTable.getItems().addAll(
                        UserController.getAllUserData().stream()
                                .filter(user -> user.getUserRole() == 1 || user.getUserRole() == 2 || user.getUserRole() == 3)
                                .collect(Collectors.toList())
                );
                userTable.setPrefHeight(400);
                userTable.setPrefWidth(800);
                vb.getChildren().addAll(backButton, title, userTable);
                scene = new Scene(vb, 800, 600);
        }
}
