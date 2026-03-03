package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dataaccess.DataAccess;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.ResponseException;
import io.javalin.http.Context;
import model.AuthData;
import service.Request.*;
import service.Result.*;
import service.UserService;

public class UserHandler {
    private final UserService service;
    private final AuthHandler auth;

    public UserHandler(DataAccess dataAccess) {
        this.auth = new AuthHandler(dataAccess);
        this.service = new UserService(dataAccess);
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
    public void logout(Context ctx) throws ResponseException{
           try{
               AuthData auth = this.auth.validateAuth(ctx);
               service.logout(new LogoutRequest(auth.authToken()));
           }
           catch(DataAccessException e){
               throw new ResponseException(401, "Error: unauthorized");
           }

    }
    public void createGame(Context ctx) throws ResponseException{
        try{
            AuthData auth = this.auth.validateAuth(ctx);
            CreateGameRequest request = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
            CreateGameResult result = service.createGame(auth.authToken(),request);
            ctx.result(new Gson().toJson(result));
        }
        catch (BadRequestException | JsonSyntaxException e){
            throw new ResponseException(400, "Error: bad request");
        }
        catch (DataAccessException e){
            throw new ResponseException(401, "Error: unauthorized");
        }
    }
    public void listGames(Context ctx) throws ResponseException{
        try{
            AuthData auth = this.auth.validateAuth(ctx);
            ctx.result(new Gson().toJson(service.listGames(new ListGamesRequest(auth.authToken()))));

        }
        catch (DataAccessException e) {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }
    public void clear(Context ctx){
        service.clear();
        ctx.status(200);
    }
}
