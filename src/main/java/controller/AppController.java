package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.AppModel;
import model.user.User;

/**
 * Class which handles the and creates intractable charts.
 *
 * @author Johan
 * @author Dennis
 */
public class AppController {

    private final AppModel appModel = AppModel.getInstance();

    @FXML private TabPane tabsPane;
    @FXML private AnchorPane mainView;
    @FXML private AnchorPane signupView;
    @FXML private AnchorPane loginView;
    @FXML private TextField loginUsernameField;
    @FXML private TextField loginPasswordField;
    @FXML private TextField signupUsernameField;
    @FXML private TextField signupPasswordField;
    @FXML private Group loginButtons;
    @FXML private Group logoutButtons;
    @FXML private Label usernameLabel;
    @FXML private Label loginError;
    @FXML private Label signupError;


    private void updateUserButtonGroups() {
        loginButtons.setVisible(!User.isLoggedIn());
        logoutButtons.setVisible(User.isLoggedIn());
    }

    /**
     * Generates a new {@link ComparisonChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newComparisonChart() {
        ChartController newChart = new ComparisonChartController(this);
        newTab(newChart, "Comparison Chart");
    }

    /**
     * Generates a new {@link DetailedChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newDetailedChart() {
        ChartController newChart = new DetailedChartController(this);
        newTab(newChart, "Detailed Chart");
    }

    /**
     * Generates a new {@link CorrelationChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newCorrelationChart() {
        ChartController newChart = new CorrelationChartController(this);
        newTab(newChart, "Correlation Chart");
    }

    /**
     * Creates a new tab in the AppController GUI.
     * @param content the node which is added as a tab.
     * @param name the name of the tab.
     */
    private void newTab(Node content, String name) {
        Tab newTab = new Tab(name, content);
        tabsPane.getTabs().add(newTab);
        tabsPane.getSelectionModel().select(newTab);
    }

    @FXML
    private void mainView() {
        mainView.toFront();
    }

    @FXML
    private void loginView() {
        mainView.toFront();
        loginView.toFront();
    }

    @FXML
    private void signupView() {
        mainView.toFront();
        signupView.toFront();
    }

    @FXML
    private void mouseTrap(Event event) {
        event.consume();
    }

    @FXML
    private void login() {
        User.login(loginUsernameField.getText(), loginPasswordField.getText());
        if (User.isLoggedIn()) {
            mainView();
            updateUserButtonGroups();
            usernameLabel.setText(User.getCurrentUser().getUsername());
        } else {
            loginError.setText("No account found");
        }
    }

    @FXML
    private void signup() {
        User.signup(signupUsernameField.getText(), signupPasswordField.getText());
        if (User.isLoggedIn()) {
            mainView();
            updateUserButtonGroups();
            usernameLabel.setText(User.getCurrentUser().getUsername());
        } else {
            signupError.setText("Username taken");
        }
    }

    @FXML
    private void logout() {
        User.logout();
        updateUserButtonGroups();
        usernameLabel.setText("");
    }
}









