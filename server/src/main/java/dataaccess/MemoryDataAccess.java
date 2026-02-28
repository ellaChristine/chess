package dataaccess;

import model.*;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess{
    final private HashMap<String, UserData> newUser = new HashMap<>();
    final private HashMap<String, AuthData> authToken = new HashMap<>();

    public void createUser(UserData user){
        user = new UserData(user.username(),user.password(),user.email());

        newUser.put(user.username(),user);
    }
    public UserData getUser(String username){
        return newUser.get(username);
    }
    public void createAuth(AuthData auth){
        auth = new AuthData(auth.authToken(), auth.username());

        authToken.put(auth.authToken(), auth);
    }
    public AuthData getAuth(String auth){return authToken.get(auth);}
    public void deleteAuth(AuthData auth){
        authToken.remove(auth.authToken());
    }
}
