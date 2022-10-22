import model.user.User;
import view.AppView;

/**
 * A class responsible for setup and startup of the entire application.
 */
public class Main {

    /**
     * static main method that starts the application
     *
     * @param args a {@link String[]} containing optional arguments from the command-line.
     */
    public static void main(String[] args) {
        AppView view = new AppView();
        User.loadUsers();
        view.startup(args);
    }

}
