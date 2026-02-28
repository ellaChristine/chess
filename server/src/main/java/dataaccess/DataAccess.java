package dataaccess;

import exception.DataAccessException;
import model.AuthData;
import model.UserData;

public interface DataAccess {
    void createUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    void createAuth(AuthData auth) throws DataAccessException;
}
