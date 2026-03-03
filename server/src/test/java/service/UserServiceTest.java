package service;

import dataaccess.DataAccess;
import exception.BadRequestException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Test;
import exception.DataAccessException;
import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import service.Request.*;
import service.Result.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private DataAccess dataAccess;
    private UserService service;

    @BeforeEach
    void setUp() {
        dataAccess = new MemoryDataAccess(); // or whatever your implementation is
        service = new UserService(dataAccess);
    }

    @Test
    void registerSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("Ella716", "1234567", "ekinney0@byu.edu");
        RegisterResult result = service.register(request);
        assertNotNull(result);
        assertEquals("Ella716", result.username());
        assertNotNull(result.authToken());

    }

    @Test
    void registerDuplicateUsername() throws DataAccessException {
        RegisterRequest request1 = new RegisterRequest("RJSM715", "123456",
                                "rjmiercort@gmail.com");
        service.register(request1);
        RegisterRequest request2 = new RegisterRequest("RJSM715", "23456",
                                "ellakinney@gmail.com");
        assertThrows(DataAccessException.class, () -> service.register(request2));
    }

    @Test
    void registerBadInput() throws BadRequestException {
        RegisterRequest requestNullUser = new RegisterRequest(null, "IlikeChesse!",
                                    "ekinney0@gmail.com");
        assertThrows(BadRequestException.class, () -> service.register(requestNullUser));
        RegisterRequest requestNullPassword = new RegisterRequest("ekinney", null,
                                        "ellakinney716@gmail.com");
        assertThrows(BadRequestException.class, () -> service.register(requestNullPassword));
        RegisterRequest requestNullEmail = new RegisterRequest("ekinney", "123456", null);
        assertThrows(BadRequestException.class, () -> service.register(requestNullEmail));

    }

    @Test
    void loginSuccess() throws DataAccessException {
        RegisterRequest createUser = new RegisterRequest("EllaCK", "336112#",
                                "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest request = new LoginRequest("EllaCK", "336112#");
        LoginResult result = service.login(request);
        assertNotNull(result);
        assertEquals("EllaCK", result.username());
        assertNotNull(result.authToken());
    }

    @Test
    void userDoesNotExist() {
        LoginRequest request = new LoginRequest("EllaCK", "336112#");
        assertThrows(DataAccessException.class, () -> service.login(request));
    }

    @Test
    void passwordIncorrect() throws DataAccessException {
        RegisterRequest createUser = new RegisterRequest("EllaCK", "336112#",
                                "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest request = new LoginRequest("EllaCK", "12345");
        assertThrows(DataAccessException.class, () -> service.login(request));
    }

    @Test
    void loginBadRequest() throws DataAccessException {
        RegisterRequest createUser = new RegisterRequest("EllaCK", "336112#",
                                "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest passwordNull = new LoginRequest("EllaCK", null);
        assertThrows(BadRequestException.class, () -> service.login(passwordNull));
        LoginRequest userNull = new LoginRequest(null, "336112#");
        assertThrows(BadRequestException.class, () -> service.login(userNull));
    }

    @Test
    void logoutSuccess() throws DataAccessException {
        LoginResult loginResult = loginUser();
        LogoutRequest request = new LogoutRequest(loginResult.authToken());
        service.logout(request);
        AuthData auth = new MemoryDataAccess().getAuth(loginResult.authToken());
        assertNull(auth);
    }

    @Test
    void logoutFail() {
        LogoutRequest logout = new LogoutRequest("fakeToken123");
        assertThrows(DataAccessException.class, () -> service.logout(logout));

    }

    @Test
    void createGameSuccess() throws DataAccessException {
        LoginResult loginResult = loginUser();
        CreateGameRequest request = new CreateGameRequest("gameName");
        CreateGameResult result = service.createGame(loginResult.authToken(), request);
        assertEquals(1, result.gameID());
    }

    @Test
    void badAuthToken() {
        CreateGameRequest request = new CreateGameRequest("name");
        assertThrows(DataAccessException.class, () -> service.createGame("qwertyuiop12345", request));

    }

    @Test
    void nullName() throws DataAccessException {
        LoginResult result = loginUser();
        CreateGameRequest request = new CreateGameRequest(null);

        assertThrows(BadRequestException.class, () -> service.createGame(result.authToken(), request));
    }

    @Test
    void listSuccess() throws DataAccessException {
        LoginResult result = loginUser();
        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        service.createGame(result.authToken(), createGameRequest);
        ListGamesResult result1 = service.listGames(new ListGamesRequest(result.authToken()));
        assertEquals(1, result1.games().size());

    }

    @Test
    void listFail() throws DataAccessException {
        LoginResult result = loginUser();
        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        service.createGame(result.authToken(), createGameRequest);
        ListGamesRequest listGamesRequest = new ListGamesRequest("qwerewuiob");
        assertThrows(DataAccessException.class, () -> service.listGames(listGamesRequest));
    }
    @Test
    void joinGameSuccess() throws DataAccessException{
        LoginResult loginResult = loginUser();
        CreateGameResult createGameResult = createGame(loginResult);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        service.joinGame(loginResult.authToken(),joinGameRequest);
        GameData game = dataAccess.getGame(joinGameRequest.gameID());
        assertEquals(loginResult.username(), game.whiteUsername());
    }

    @Test
    void joinGameFail() throws DataAccessException{
        LoginResult firstUser = loginUser();
        CreateGameResult game = createGame(firstUser);
        JoinGameRequest joinWhite = new JoinGameRequest("White", game.gameID());
        service.joinGame(firstUser.authToken(), joinWhite);
        assertThrows(BadRequestException.class, () -> service.joinGame(firstUser.authToken(), joinWhite));
    }

    private LoginResult loginUser() throws DataAccessException {
        RegisterRequest createUser = new RegisterRequest("molecularBiology!", "1234567", "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest loginRequest = new LoginRequest("molecularBiology!", "1234567");
        return service.login(loginRequest);
    }
    private CreateGameResult createGame(LoginResult loginResult) throws DataAccessException{
        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        return service.createGame(loginResult.authToken(), createGameRequest);
    }

}