package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;

public class CarManagementPage {

	public CarManagementPage() {
		initialize();
		addEventListener();
	}

//	private Car selectedcar;
	private Scene scene;
	private BorderPane bp;
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem carManagementMenuItem, logoutMenuItem;
	private VBox vb;
	private Label tableTitleLabel, carNameLabel, carPriceLabel;
//	private TableView<Car> carTableView;
	private TextField carNameTextField, carPriceTextField;
	private Button addcarButton, removecarButton;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		bp = new BorderPane();
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		carManagementMenuItem = new MenuItem("Car Management");
		logoutMenuItem = new MenuItem("Log Out");
		menu.getItems().addAll(carManagementMenuItem, logoutMenuItem);
		menuBar.getMenus().addAll(menu);
		bp.setTop(menuBar);

		vb = new VBox(15);
		tableTitleLabel = new Label("car Management");
		carNameLabel = new Label("car Name");
		carPriceLabel = new Label("car Price");

//		carTableView = new TableView<>();
//
//		TableColumn<Car, String> carNameTableColumn = new TableColumn<Car, String>("car Name");
//		carNameTableColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carName"));
//		carNameTableColumn.setMinWidth(395);
//
//		TableColumn<Car, Integer> carPriceTableColumn = new TableColumn<Car, Integer>("car Price");
//		carPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("carPrice"));
//		carPriceTableColumn.setMinWidth(395);
//
//		carTableView.getColumns().addAll(carNameTableColumn, carPriceTableColumn);
		getCarData();

		carNameTextField = new TextField();
		carNameTextField.setPromptText("Input car name here");
		carPriceTextField = new TextField();
		carPriceTextField.setPromptText("Input car price here");

		addcarButton = new Button("Add car");
		removecarButton = new Button("Remove car");

//		vb.getChildren().addAll(tableTitleLabel, carTableView, carNameLabel, carNameTextField, carPriceLabel,
//				carPriceTextField, addcarButton, removecarButton);

		tableTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 17));

		vb.setPadding(new Insets(10));
		bp.setCenter(vb);

		scene = new Scene(bp, 800, 600);
	}

	private void addEventListener() {
		carManagementMenuItem.setOnAction(e -> {
		});

		logoutMenuItem.setOnAction(e -> {
		});

//		carTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<car>() {
//			@Override
//			public void changed(ObservableValue<? extends car> observable, car oldValue, car newValue) {
//			}
//		});

		addcarButton.setOnMouseClicked(e -> {
		});

		removecarButton.setOnMouseClicked(e -> {
		});
	}

	private void getCarData() {
		
	}

	private void reset() {
//		selectedcar = null;
		carNameTextField.setText("");
		carPriceTextField.setText("");
//		carTableView.getSelectionModel().select(null);
	}

}
