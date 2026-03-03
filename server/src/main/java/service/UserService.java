package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import exception.BadRequestException;
import model.*;
import exception.DataAccessException;
import service.Request.*;
import service.Result.*;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException{
        if(registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            throw new BadRequestException();
        }
        boolean b = dataAccess.getUser(registerRequest.username()) != null;
        if(b){
            throw new DataAccessException("Error: already taken");
        }
        UserData n = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
        dataAccess.createUser(n);
        String token = createAuthToken();
        AuthData a = new AuthData(token, n.username());
        dataAccess.createAuth(a);

        return new RegisterResult(a.username(),a.authToken());
    }

    public LoginResult login(LoginRequest loginrequest) throws DataAccessException {
        if(loginrequest.username() == null || loginrequest.password() == null){
            throw new BadRequestException();
        }
        UserData user = dataAccess.getUser(loginrequest.username());
        if(user == null){
            throw new DataAccessException("Error: Unauthorized");
        }
        if(!Objects.equals(user.password(), loginrequest.password())){
            throw new DataAccessException("Error: unauthorized");
        }
        String token = createAuthToken();
        AuthData result = new AuthData(token, user.username());
        dataAccess.createAuth(result);

        return new LoginResult(result.username(), result.authToken());
    }

    public void logout(LogoutRequest logoutrequest) throws DataAccessException{
        if (logoutrequest.authToken() == null){
            throw new BadRequestException();
        }
        AuthData a = dataAccess.getAuth(logoutrequest.authToken());
        if(a == null){
            throw new DataAccessException("Error: unauthorized");
        }
        dataAccess.deleteAuth(a);
    }

    public void clear(){
        dataAccess.clearUsers();
        dataAccess.clearAuths();
        dataAccess.clearGames();
    }

    public CreateGameResult createGame(String authToken, CreateGameRequest createGameRequest) throws DataAccessException {
        AuthData a = dataAccess.getAuth(authToken);
        if(a == null){
            throw new DataAccessException("Error: unauthorized");
        }
        if(createGameRequest.gameName() == null){
            throw new BadRequestException();
        }
        GameData g = new GameData(0, null, null,createGameRequest.gameName(),new ChessGame());
        Integer ID = dataAccess.createGame(g).gameID();
        return new CreateGameResult(ID);
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws DataAccessException{
        if(listGamesRequest.authToken() == null){
            throw new BadRequestException();
        }
        AuthData a = dataAccess.getAuth(listGamesRequest.authToken());
        if(a == null){
            throw new DataAccessException("Error: unauthorized");
        }
        Collection<ListGamesData> games = dataAccess.listGames();
        return new ListGamesResult(games);

    }

    public void joinGame(String authToken, JoinGameRequest joinGameRequest)throws DataAccessException{
        AuthData a = dataAccess.getAuth(authToken);
        if(a == null){
            throw new DataAccessException("Error: unauthorized");
        }

        if(joinGameRequest.playerColor() == null || joinGameRequest.gameID() == null) {
            throw new BadRequestException();

        }
        GameData game = dataAccess.getGame(joinGameRequest.gameID());
        if(Objects.equals(joinGameRequest.playerColor(), "WHITE")){
            if(game.whiteUsername() != null){
                throw new DataAccessException("Error: already taken");
            }
            GameData gameData = new GameData(game.gameID(), a.username(),game.blackUsername(), game.gameName(), game.game());
            dataAccess.updateGame(gameData);
        }
        if(joinGameRequest.playerColor().equals("BLACK")){
            if(game.blackUsername() != null){
                throw new DataAccessException("Error: already taken");
            }
            GameData gameData = new GameData(game.gameID(), game.whiteUsername(), a.username(), game.gameName(), game.game());
            dataAccess.updateGame(gameData);
        }
        if(!joinGameRequest.playerColor().equals("WHITE") && !joinGameRequest.playerColor().equals("BLACK")){
            throw new BadRequestException();
        }

    }
    private String createAuthToken(){
        return UUID.randomUUID().toString();
    }

    public AuthData getAuth (String auth) throws DataAccessException {
        return dataAccess.getAuth(auth);
    }
}
