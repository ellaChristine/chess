package service;

import exception.BadRequestException;
import org.junit.jupiter.api.Test;
import exception.DataAccessException;
import dataaccess.MemoryDataAccess;
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
    }
}