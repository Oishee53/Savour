import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users= new ArrayList<>();

    public ArrayList<User> getUserList(){
        return users;
    }

    public void addUsers(User user){
        users.add(user);
    }
    public void removeUsers(User user){
        users.remove(user);
    }


}
