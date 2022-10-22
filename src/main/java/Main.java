import model.AppModel;
import model.user.User;
import view.AppView;

public class Main {


    public static void main(String[] args) {
        AppModel model = AppModel.getInstance();
        AppView view = new AppView();
        model.addObserver(view);
        User.loadUsers();
        view.startup();
        model.notifyObservers();
    }

}
