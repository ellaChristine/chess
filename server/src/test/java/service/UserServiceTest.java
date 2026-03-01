package service;

import exception.BadRequestException;
import model.AuthData;
import org.junit.jupiter.api.Test;
import exception.DataAccessException;
import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import service.Request.*;
import service.Result.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static UserService service = null;
// make a new userService and new MemoryDataAccess so that each test will start with a clean slate
    @BeforeEach
    void newStuff(){
        service = new UserService(new MemoryDataAccess());
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
        RegisterRequest request1 = new RegisterRequest("RJSM715", "123456", "rjmiercort@gmail.com");
        RegisterResult result1 = service.register(request1);
        RegisterRequest request2 = new RegisterRequest("RJSM715", "23456", "ellakinney@gmail.com");
        assertThrows(DataAccessException.class, () -> service.register(request2));
    }

    @Test
    void registerBadInput() throws BadRequestException {
        RegisterRequest requestNullUser = new RegisterRequest(null, "IlikeChesse!", "ekinney0@gmail.com");
        assertThrows(BadRequestException.class, () -> service.register(requestNullUser));
        RegisterRequest requestNullPassword = new RegisterRequest("ekinney", null, "ellakinney716@gmail.com");
        assertThrows(BadRequestException.class, () -> service.register(requestNullPassword));
        RegisterRequest requestNullEmail = new RegisterRequest("ekinney", "123456", null);
        assertThrows(BadRequestException.class, () -> service.register(requestNullEmail));

    }
    @Test
    void loginSuccess() throws DataAccessException {
        RegisterRequest createUser = new RegisterRequest("EllaCK", "336112#", "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest request = new LoginRequest("EllaCK", "336112#");
        LoginResult result = service.login(request);
        assertNotNull(result);
        assertEquals("EllaCK", result.username());
        assertNotNull(result.authToken());
    }
    @Test
    void userDoesNotExist() throws DataAccessException{
        LoginRequest request = new LoginRequest("EllaCK", "336112#");
        assertThrows(DataAccessException.class, () ->service.login(request));
    }
    @Test
    void passwordIncorrect() throws DataAccessException{
        RegisterRequest createUser = new RegisterRequest("EllaCK", "336112#", "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest request = new LoginRequest("EllaCK", "12345");
        assertThrows(DataAccessException.class, () -> service.login(request));
    }
    @Test
    void loginBadRequest() throws DataAccessException {
        RegisterRequest createUser = new RegisterRequest("EllaCK", "336112#", "rjmiercort@gmail.com");
        service.register(createUser);
        LoginRequest passwordNull = new LoginRequest("EllaCK", null);
        assertThrows(BadRequestException.class, () -> service.login(passwordNull));
        LoginRequest userNull = new LoginRequest(null, "336112#");
        assertThrows(BadRequestException.class, () -> service.login(userNull));
    }
    @Test
    void logoutSuccess() throws DataAccessException{
        RegisterRequest createUser = new RegisterRequest("molecularBiology!", "1234567", "duke.calie.io@gmail.com");
        service.register(createUser);
        LoginRequest loginRequest = new LoginRequest("molecularBiology!", "1234567");
        LoginResult loginResult = service.login(loginRequest);
        LogoutRequest request = new LogoutRequest(loginResult.authToken());
        service.logout(request);
        AuthData auth = new MemoryDataAccess().getAuth(loginResult.authToken());
        assertNull(auth);
    }
    @Test
    void logoutFail() throws DataAccessException{
        LogoutRequest logout = new LogoutRequest("fakeToken123");
        assertThrows(DataAccessException.class, () -> service.logout(logout));

    }

}