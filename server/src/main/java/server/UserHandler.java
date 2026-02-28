package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import exception.BadRequestException;
import exception.DataAccessException;
import dataaccess.MemoryDataAccess;
import exception.ResponseException;
import io.javalin.http.Context;
import service.Request.LoginRequest;
import service.Request.RegisterRequest;
import service.Result.LoginResult;
import service.Result.RegisterResult;
import service.UserService;

import java.util.Map;

public class UserHandler {
    private final UserService service;

    public UserHandler() {
        this.service = new UserService(new MemoryDataAccess());
    }

    public void register(Context ctx) throws ResponseException {
        try{
            RegisterRequest request= new Gson().fromJson(ctx.body(), RegisterRequest.class);
            RegisterResult result = service.register(request);
            ctx.result(new Gson().toJson(result));
        } catch (BadRequestException | JsonSyntaxException e) {
            throw new ResponseException(400, "Error: bad request");
        }
        catch (DataAccessException e) {
            throw new ResponseException(403, e.getMessage());
        }
    }
    public void login(Context ctx) throws ResponseException{
        try{
            LoginRequest request = new Gson().fromJson(ctx.body(), LoginRequest.class);
            LoginResult result = service.login(request);
            ctx.result(new Gson().toJson(result));
        }
        catch (BadRequestException | JsonSyntaxException e){
            throw new ResponseException(400, "Error: bad request");
        }
        catch (DataAccessException e){
            throw new ResponseException(401, e.getMessage());
        }
    }
}
