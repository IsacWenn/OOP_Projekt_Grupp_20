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
import model.user.User;
import model.util.GraphRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which handles the and creates intractable charts.
 *
 * @author Johan
 * @author Dennis
 */
public class AppController {

    private List<String> favoriteCompanies = new ArrayList<>();
    private List<ChartController> charts = new ArrayList<>();

    @FXML
    private TabPane tabsPane;
    @FXML
    private AnchorPane mainView;
    @FXML
    private AnchorPane signupView;
    @FXML
    private AnchorPane loginView;
    @FXML
    private TextField loginUsernameField;
    @FXML
    private TextField loginPasswordField;
    @FXML
    private TextField signupUsernameField;
    @FXML
    private TextField signupPasswordField;
    @FXML
    private TextField signupEmailField;
    @FXML
    private TextField signupFNameField;
    @FXML
    private TextField signupLNameField;
    @FXML
    private TextField signupBioField;
    @FXML
    private Group loginButtons;
    @FXML
    private Group logoutButtons;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label loginError;
    @FXML
    private Label signupError;


    /**
     * Sets visibility of buttons depending on whether the user is logged in or not.
     */
    private void updateUserButtonGroups() {
        loginButtons.setVisible(!User.isLoggedIn());
        logoutButtons.setVisible(User.isLoggedIn());
    }

    /**
     * Generates a new {@link ComparisonChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newComparisonChart() {
        ChartController newChart = new ComparisonChartController(this, favoriteCompanies);
        charts.add(newChart);
        newTab(newChart, "Comparison Chart");
    }

    /**
     * Generates a new {@link DetailedChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newDetailedChart() {
        ChartController newChart = new DetailedChartController(this, favoriteCompanies);
        charts.add(newChart);
        newTab(newChart, "Detailed Chart");
    }

    /**
     * Generates a new {@link CorrelationChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newCorrelationChart() {
        ChartController newChart = new CorrelationChartController(this, favoriteCompanies);
        charts.add(newChart);
        newTab(newChart, "Correlation Chart");
    }

    /**
     * Creates a new tab in the AppController GUI.
     *
     * @param content the node which is added as a tab.
     * @param name    the name of the tab.
     */
    private void newTab(Node content, String name) {
        Tab newTab = new Tab(name, content);
        tabsPane.getTabs().add(newTab);
        tabsPane.getSelectionModel().select(newTab);
    }

    /**
     * Moves the main view to front.
     */
    @FXML
    private void mainView() {
        mainView.toFront();
    }

    /**
     * Moves the log in view to front.
     */
    @FXML
    private void loginView() {
        mainView.toFront();
        loginView.toFront();
    }

    /**
     * Moves the sign-up view to front.
     */
    @FXML
    private void signupView() {
        mainView.toFront();
        signupView.toFront();
    }

    @FXML
    private void mouseTrap(Event event) {
        event.consume();
    }

    /**
     * Attempt a log-in attempt.
     */
    @FXML
    private void login() {
        if (User.loginUser(loginUsernameField.getText(), loginPasswordField.getText())) {
            mainView();
            updateUserButtonGroups();
            usernameLabel.setText(User.getActiveUser().getUserName());
            favoriteCompanies = new ArrayList<>(User.getActiveUser().getUserFavoriteCompanies());
            for (ChartController chart : charts) {
                chart.updateFavoritesList(favoriteCompanies);
            }
            refreshFavorites();
        } else {
            loginError.setText("No account found");
        }
    }

    /**
     * Updates the list of {@link ControllerStockListItem}s across all active tabs.
     */
    public void refreshFavorites() {
        for (ChartController chart : charts) {
            chart.updateStockList();
        }
    }

    /**
     * Attempts a sign-up attempt.
     */
    @FXML
    private void signup() {
        new User(signupUsernameField.getText(), signupPasswordField.getText(), signupEmailField.getText(),
                signupFNameField.getText(), signupLNameField.getText(), signupBioField.getText());
        if (User.isLoggedIn()) {
            mainView();
            updateUserButtonGroups();
            usernameLabel.setText(User.getActiveUser().getUserName());
            for (String company : favoriteCompanies) {
                User.getActiveUser().addFavoriteCompany(company);
            }
            User.saveUsers();
        } else {
            signupError.setText("Error registering your account");
        }
    }

    /**
     * Logs out and saves information.
     */
    @FXML
    private void logout() {
        User.logoutActiveUser();
        updateUserButtonGroups();
        usernameLabel.setText("");
        favoriteCompanies = new ArrayList<>();
        for (ChartController chart : charts) {
            chart.updateFavoritesList(favoriteCompanies);
        }
        User.saveUsers();
    }

    @FXML
    private void loadSavedGraph() {
        List<GraphRepresentation> graphsToLoad = User.getActiveUser().getUserFavoriteGraphs();
        String chartType = User.getActiveUser().getFavoriteChartType();
        ChartController chartToAdd;
        switch (chartType) {
            case "Correlation Chart" -> {
                chartToAdd = new CorrelationChartController(this, favoriteCompanies, graphsToLoad);
                newTab(chartToAdd, chartType);
            }
            case "Detailed Chart" -> {
                chartToAdd = new DetailedChartController(this, favoriteCompanies, graphsToLoad);
                newTab(chartToAdd, chartType);
            }
            case "Comparison Chart" -> {
                chartToAdd = new ComparisonChartController(this, favoriteCompanies, graphsToLoad);
                newTab(chartToAdd, chartType);
            }
        }
    }
}









