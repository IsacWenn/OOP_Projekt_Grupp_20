import model.AppModel;
import view.AppView;

public class Main {


    public static void main(String[] args) {
        AppModel model = AppModel.getInstance();
        AppView view = new AppView();
        model.addObserver(view);
        view.startup();
        
        model.notifyObservers();
        UsersCollection.saveUsers();
        System.out.println("Färdig");
    }

}
