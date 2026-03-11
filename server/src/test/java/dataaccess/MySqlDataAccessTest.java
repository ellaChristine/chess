package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import exception.*;
import dataaccess.*;
import model.*;
import service.UserService;


import static org.junit.jupiter.api.Assertions.*;

class MySqlDataAccessTest {
    private MySqlDataAccess dataAccess;

    @BeforeEach
    void setUp() throws DataAccessException {
        dataAccess = new MySqlDataAccess();
        this.dataAccess.clearAuths();
        this.dataAccess.clearGames();
        this.dataAccess.clearUsers();
    }

    @Test
    void createUserSuccess() throws DataAccessException {
        UserData user = new UserData("ella", "123","aaaaa@gmail.com");
        dataAccess.createUser(user);
        UserData result = dataAccess.getUser(user.username());
        assertNotNull(result);
    }
    @Test
    void createUserFail() throws DataAccessException{
        UserData user = new UserData("ella", "123","aaaaa@gmail.com");
        dataAccess.createUser(user);
        UserData user1= new UserData("ella", "123","aaaaa@gmail.com");
        assertThrows(DataAccessException.class, () -> dataAccess.createUser(user1));
    }

    @Test
    void getUserSuccess() throws DataAccessException {
        UserData user = new UserData("a","b","c");
        dataAccess.createUser(user);
        assertNotNull(dataAccess.getUser(user.username()));
    }

    @Test
    void getUserFail() throws DataAccessException{
        assertNull(dataAccess.getUser("a"));

    }

    @Test
    void createAuth() {
    }

    @Test
    void getAuth() {
    }

    @Test
    void deleteAuth() {
    }

    @Test
    void clearUsers() {
    }

    @Test
    void clearGames() {
    }

    @Test
    void clearAuths() {
    }

    @Test
    void listGames() {
    }

    @Test
    void createGame() {
    }

    @Test
    void getGame() {
    }

    @Test
    void updateGame() {
    }
}