package server;

import dataaccess.DataAccess;
import exception.DataAccessException;
import exception.ResponseException;
import io.javalin.http.Context;
import model.AuthData;
import service.UserService;


public class AuthHandler {
    private final UserService service;

    public AuthHandler(DataAccess dataAccess) {
        this.service = new UserService(dataAccess);
    }
    public AuthData validateAuth(Context ctx) throws ResponseException{
       try{
           String auth = ctx.header("authorization");
           AuthData data = this.service.getAuth(auth);
           if(data == null){
               throw new ResponseException(401, "Error: unauthorized");
           }
           return data;
       } catch (DataAccessException e) {
           throw new ResponseException(401, "Error: unauthorized");
       }
    }
}
