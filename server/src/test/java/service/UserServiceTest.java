package service;

import org.junit.jupiter.api.Test;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import service.Request.RegisterRequest;
import service.Result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static UserService service = null;
// make a new userService and new MemoryDataAccess so that each test will start with a clean slate
    @BeforeEach
    void newStuff(){
        service = new UserService(new MemoryDataAccess());
    }

//Write the positive test — successful registration. This test should create a RegisterRequest with a valid username
//password, and email, call register on your service, and then assert that the result is what you expect.
// Things worth asserting are that the result is not null, that the username in the result matches what you passed in,
// and that the authToken in the result is not null or empty.
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
    void registerBadInput() throws DataAccessException{

    }
}