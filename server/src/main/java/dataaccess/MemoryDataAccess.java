package dataaccess;

import exception.DataAccessException;
import model.*;
import service.result.ListGamesData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess{
    final private HashMap<String, UserData> newUser = new HashMap<>();
    final private HashMap<String, AuthData> authToken = new HashMap<>();
    private int nextId = 1;
    final private HashMap<Integer, GameData> game = new HashMap<>();
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

    public GameData createGame(GameData gameData){
        gameData = new GameData(nextId++, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        game.put(gameData.gameID(), gameData);

        return gameData;
    }

    public GameData getGame(Integer gameID) {
        return game.get(gameID);
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        game.put(gameData.gameID(), gameData);
    }


    public Collection<ListGamesData> listGames(){
        Collection<GameData> data = game.values();
        Collection<ListGamesData> newData = new ArrayList<>();
        for(GameData i: data){
            newData.add(new ListGamesData(i.gameID(),i.whiteUsername(),i.blackUsername(),i.gameName()));
        }
        return newData;
    }

    public void clearGames() {
        game.clear();
    }
    public void clearUsers(){newUser.clear();}
    public void clearAuths() {
        authToken.clear();
    }
}
