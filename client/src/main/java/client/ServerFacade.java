package client;

import com.google.gson.Gson;
import model.*;
import exception.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collection;
import java.util.Map;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(int port) {
        serverUrl = "http://localhost:"+ port;
    }

    public AuthData register(String username, String password, String email) throws ResponseException{
        var body = new UserData(username, password, email);
        var request = buildRequest("POST", "/user", body, null);
        var response = sendRequest(request);
        return handleResponse(response, AuthData.class);
    }

    public AuthData login(String username, String password) throws ResponseException {
        var body = new LoginRecord(username, password);
        var request = buildRequest("POST", "/session", body, null);
        var response = sendRequest(request);
        return handleResponse(response, AuthData.class);
    }

    public void logout(String authToken) throws ResponseException {
        var request = buildRequest("DELETE", "/session", null, authToken);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    public int createGame(String gameName, String authToken) throws ResponseException {
        var body = Map.of("gameName", gameName);
        var request = buildRequest("POST", "/game",body, authToken);
        var response = sendRequest(request);
        var result = handleResponse(response,CreateGameResponse.class);
        return result.gameID;
    }

    public void joinGame(String playerColor, Integer gameID, String authToken) throws ResponseException {
        var body = new Join(playerColor,gameID);
        var request = buildRequest("PUT", "/game", body, authToken);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    public void clear() throws ResponseException {
        var request = buildRequest("DELETE", "/db", null,null);
        var response = sendRequest(request);
        handleResponse(response,null);
    }

    public Collection<GameData> listGames(String authToken) throws ResponseException {
        var request = buildRequest("GET","/game",null, authToken);
        var response = sendRequest(request);
        var result =handleResponse(response,ListGamesResponse.class);
        return result.games;
    }

    private record CreateGameResponse(int gameID){}

    private record Join(String playerColor, Integer gameID){}

    private record ListGamesResponse(Collection<GameData> games){}

    private HttpRequest buildRequest(String method, String path, Object body, String authToken){
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if(body != null){
            request.setHeader("Content-Type","application/json");

        }
        if(authToken != null){
            request.setHeader("authorization", authToken);
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request){
        if (request != null){
            return BodyPublishers.ofString(new Gson().toJson(request));
        }else{
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try{
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex){
            throw new ResponseException(500,"Error: server Error");
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                var map = new Gson().fromJson(body, Map.class);
                var message = (String) map.get("message");
                throw new ResponseException(status, message);
            }
            throw new ResponseException(status, "unkown erro: " + status);
        }
        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
