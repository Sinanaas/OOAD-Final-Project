package view;

import controller.UserController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.User;

public class ChangeRolePage {
        private User user;
        private static ChangeRolePage changeRolePage;
        private Scene scene;
        private Label title, userIDLabel, usernameLabel, userPasswordLabel, userAgeLabel, userRoleLabel, newRole;
        private VBox labelsVBox;
        private HBox hb;
        private ComboBox<String> roleComboBox;
        private Button button, back;
        public static ChangeRolePage getInstance(User user) {
//                return changeRolePage = changeRolePage == null ? new ChangeRolePage(user) : changeRolePage;
                return new ChangeRolePage(user);
        }

        public ChangeRolePage(User user) {
                this.user = user;
                initialize();
                addEventListener();
        }

        private void addEventListener() {
                button.setOnAction(e -> {
                        if (roleComboBox.getValue() == null) {
                                Helper.showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please select new role");
                                return;
                        }

                        if (roleComboBox.getValue().equals("Customer")) {
                                UserController.changeUserRole(user.getUserID(), 0);
                        }else if (roleComboBox.getValue().equals("Computer Technician")) {
                                UserController.changeUserRole(user.getUserID(), 1);
                        } else if (roleComboBox.getValue().equals("Operator")) {
                                UserController.changeUserRole(user.getUserID(), 2);
                        } else if (roleComboBox.getValue().equals("Admin")) {
                                UserController.changeUserRole(user.getUserID(), 3);
                        }

                        ViewAllStaffPage viewAllStaffPage = ViewAllStaffPage.getInstance();
                        viewAllStaffPage.show();
                });

                back.setOnAction(e -> {
                        ViewAllStaffPage viewAllStaffPage = ViewAllStaffPage.getInstance();
                        viewAllStaffPage.show();
                });
        }

        private void initialize() {
                title = new Label("Change Role Page");
                Font font = Font.font("Arial", FontWeight.BOLD, 30);
                title.setFont(font);

                userIDLabel = new Label("User ID: " + user.getUserID());
                userIDLabel.setFont(new Font("Arial", 16));

                usernameLabel = new Label("Username: " + user.getUsername());
                usernameLabel.setFont(new Font("Arial", 16));

                userPasswordLabel = new Label("User Password: " + user.getUserPassword());
                userPasswordLabel.setFont(new Font("Arial", 16));

                userAgeLabel = new Label("User Age: " + user.getUserAge());
                userAgeLabel.setFont(new Font("Arial", 16));

//                userRoleLabel = new Label("User Role: " + user.getUserRole());
                userRoleLabel = new Label();
                if (user.getUserRole() == 1)
                        userRoleLabel.setText("User Role: Computer Technician");
                else if (user.getUserRole() == 2)
                        userRoleLabel.setText("User Role: Operator");
                else if (user.getUserRole() == 3)
                        userRoleLabel.setText("User Role: Admin");

                userRoleLabel.setFont(new Font("Arial", 16));

                newRole = new Label("New Role: ");
                newRole.setFont(new Font("Arial", 16));


                roleComboBox = new ComboBox<>();
                roleComboBox.getItems().addAll("Customer", "Computer Technician", "Operator", "Admin");
                roleComboBox.setPromptText("Select New Role");

                hb = new HBox(10);
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.getChildren().addAll(newRole, roleComboBox);

                button = new Button("Change Role");

                back = new Button("Back");
                back.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                labelsVBox = new VBox(back, title, userIDLabel, usernameLabel, userPasswordLabel, userAgeLabel, userRoleLabel, hb, button);
                labelsVBox.setAlignment(Pos.CENTER_LEFT);
                labelsVBox.setPadding(new Insets(45, 12, 15, 12));

                scene = new Scene(new VBox(20, labelsVBox), 800, 600);
        }

        public void show() {
                MainStage mainStage = MainStage.getInstance();
                mainStage.getStage().setScene(scene);
        }
}