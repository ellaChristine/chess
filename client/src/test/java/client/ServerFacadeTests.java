package client;

import exception.ResponseException;
import org.junit.jupiter.api.*;
import server.Server;

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
    void clear() throws ResponseException {facade.clear();}

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




}
