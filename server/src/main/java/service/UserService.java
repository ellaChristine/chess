package service;

import dataaccess.DataAccess;
import model.*;
import dataaccess.DataAccessException;
import service.Request.RegisterRequest;
import service.Result.RegisterResult;


import java.util.Collection;
import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException{
        if(registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            throw new DataAccessException("{ message: bad request}");

        }
        boolean b = dataAccess.getUser(registerRequest.username()) != null;
        if(b){
            throw new DataAccessException("{message : Error: username already taken}");

        }
        UserData n = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
        dataAccess.createUser(n);
        String token = createAuthToken();
        AuthData a = new AuthData(token, n.username());
        dataAccess.createAuth(a);

        return new RegisterResult(a.username(),a.authToken());


    }
    private String createAuthToken(){
        return UUID.randomUUID().toString();
    }
}
