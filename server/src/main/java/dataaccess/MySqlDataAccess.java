package dataaccess;

import com.google.gson.Gson;
import exception.*;
import model.*;
import org.mindrot.jbcrypt.BCrypt;
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


    public void createUser(UserData user) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        var statement = "INSERT INTO user(username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, user.username(),hashedPassword,user.email());
    }


    public UserData getUser(String username) throws DataAccessException {
        try(Connection conn = DatabaseManager.getConnection()){
            var statement = "SELECT * FROM user WHERE username=?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, username);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return readUser(rs);
                    }
                }
            }
        }
        catch (Exception e){
            throw new DataAccessException("Error", e);
        }
        return null;
    }


    public void createAuth(AuthData auth) throws DataAccessException {
        var statement = "INSERT INTO authToken(authToken, username) VALUES(?,?)";
        executeUpdate(statement, auth.authToken(), auth.username());
    }


    public AuthData getAuth(String auth) throws DataAccessException {
        try(Connection conn = DatabaseManager.getConnection()){
            var statement = "SELECT * FROM authToken WHERE authToken=?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,auth);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return readAuth(rs);
                    }
                }
            }
        }
        catch (Exception e){
            throw new DataAccessException("Error", e);
        }
        return null;
    }


    public void deleteAuth(AuthData auth) throws DataAccessException {
        var statement = "DELETE FROM authToken WHERE auth=?";
        executeUpdate(statement);
    }


    public void clearUsers() {

    }


    public void clearGames() {

    }


    public void clearAuths() {

    }


    public Collection<ListGamesData> listGames() throws DataAccessException {
        return List.of();
    }


    public GameData createGame(GameData gameData) throws DataAccessException {
        return null;
    }


    public GameData getGame(Integer gameID) throws DataAccessException {
        return null;
    }


    public void updateGame(GameData gameData) throws DataAccessException {

    }

    private UserData readUser(ResultSet rs) throws SQLException{
        var username = rs.getString("username");
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new UserData(username,password,email);
    }

    private AuthData readAuth(ResultSet rs) throws SQLException{
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        return new AuthData(authToken,username);
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to update database", e);
        }
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
