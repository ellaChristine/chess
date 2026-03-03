package dataaccess;

import exception.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.result.ListGamesData;

import java.util.Collection;

public interface DataAccess {
    void createUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    void createAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(String auth) throws DataAccessException;
    void deleteAuth(AuthData auth) throws DataAccessException;
    void clearUsers();
    void clearGames();
    void clearAuths();
    Collection<ListGamesData> listGames() throws DataAccessException;
    GameData createGame(GameData gameData) throws DataAccessException;
    GameData getGame(Integer gameID) throws DataAccessException;
    void updateGame(GameData gameData) throws DataAccessException;
}
