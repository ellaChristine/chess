package client;

import exception.ResponseException;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    void clearFacade() throws ResponseException {facade.clear();}

    @Test
    void register() throws Exception {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void registerFail() throws Exception{
        facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.register("player1", "password", "p1@email.com"));
    }

    @Test
    void login() throws Exception{
        facade.register("player1", "password", "p1@email.com");
        var authData = facade.login("player1", "password");
        assertNotNull(authData);
    }

    @Test
    void loginFail() throws Exception{
        facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.login("player1", "123"));
    }

    @Test
    void logout() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        assertDoesNotThrow(() -> facade.logout(authData.authToken()));
    }

    @Test
    void logoutFail() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.logout("123gnjoi"));
    }

    @Test
    void createGame() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        Integer gameID = facade.createGame("name", authData.authToken());
        assertNotNull(gameID);
    }

    @Test
    void createGameFail() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.createGame("name", "123454gfd"));
    }

    @Test
    void join() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        facade.createGame("name", authData.authToken());
        assertDoesNotThrow(() -> facade.joinGame("WHITE", 1, authData.authToken()));
    }

    @Test
    void joinFail() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.joinGame("WHITE", 1, authData.authToken()));
    }

    @Test
    void listGames() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        facade.createGame("game", authData.authToken());
        Collection<GameData> list = facade.listGames(authData.authToken());
        assertNotNull(list);
    }

    @Test
    void listGamesFail() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.listGames("123djiop"));
    }

    @Test
    void clear() throws Exception{
        var authData = facade.register("player1", "password", "p1@email.com");
        facade.createGame("name", authData.authToken());
        facade.clear();
        assertThrows(ResponseException.class, () -> facade.listGames(authData.authToken()));
    }
}
