package model.user;

import java.io.Serializable;
import java.util.List;

public class UserFavorites implements Serializable {

    
    UserFavorites() {
        foo = "bar";
    }

    @Override
    public String toString() {
        return "UserFavorites{" +
                "foo='" + foo + '\'' +
                '}';
    }
}
