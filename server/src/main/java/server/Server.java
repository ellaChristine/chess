package server;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.MySqlDataAccess;
import exception.DataAccessException;
import exception.ResponseException;
import io.javalin.*;
import io.javalin.http.Context;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final DataAccess dataAccess;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        try {
            this.dataAccess = new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserHandler handler = new UserHandler(dataAccess);


        javalin.post("/user", handler::register);
        javalin.post("/session",handler::login);
        javalin.delete("/session", handler::logout);
        javalin.delete("/db", handler::clear);
        javalin.post("/game", handler::createGame);
        javalin.get("/game", handler::listGames);
        javalin.put("/game", handler::joinGame);
        javalin.exception(ResponseException.class, this::responseExceptionHandler);
        javalin.exception(Exception.class, this::exceptionHandler);
    }
    private void responseExceptionHandler(ResponseException ex, Context ctx) {
        ctx.status(ex.code());
        ctx.result(ex.toJson());
    }
    private void exceptionHandler(Exception ex, Context ctx) {
        ctx.status(500);
        ctx.result(new Gson().toJson(Map.of("message", "Error: internal server error")));
    }
    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

}
