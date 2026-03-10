package dataaccess;

import com.google.gson.Gson;
import exception.*;
import model.*;
import service.result.ListGamesData;

import java.sql.*;
import java.util.Collection;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlDataAccess implements DataAccess {

    public MySqlDataAccess() throws DataAccessException{
        configureDatabase();
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String auth) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(AuthData auth) throws DataAccessException {

    }

    @Override
    public void clearUsers() {

    }

    @Override
    public void clearGames() {

    }

    @Override
    public void clearAuths() {

    }

    @Override
    public Collection<ListGamesData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public GameData createGame(GameData gameData) throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {

    }
    private  final String[] createTables = new String[]{
                """
                CREATE TABLE IF NOT EXISTS user (
                username VARCHAR(265) NOT NULL,
                password VARCHAR(265) NOT NULL,
                email VARCHAR(265) NOT NULL,
                PRIMARY KEY (username)
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS authToken (
                authToken VARCHAR(265) NOT NULL,
                username VARCHAR(265) NOT NULL,
                PRIMARY KEY (authToken)
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS game (
                gameID INT NOT NULL AUTO_INCREMENT,
                whiteUsername VARCHAR(265),
                blackUsername VARCHAR(265),
                gameName VARCHAR(265) NOT NULL,
                game TEXT NOT NULL,
                PRIMARY KEY (gameID)
                )
                """
        };
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try(Connection conn = DatabaseManager.getConnection()){
            for(String statement : createTables){
                try(var preparedStatement = conn.prepareStatement(statement)){
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e){
            throw new DataAccessException("failed to create database", e);
        }
    }
}
