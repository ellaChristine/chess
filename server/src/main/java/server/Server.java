package server;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import exception.ResponseException;
import io.javalin.*;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class Server {

    private final Javalin javalin;
    private final DataAccess dataAccess;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        this.dataAccess = new MemoryDataAccess();
        UserHandler handler = new UserHandler(dataAccess);


        javalin.post("/user", handler::register);
        javalin.post("/session",handler::login);
        javalin.delete("/session", handler::logout);
        javalin.delete("/db", handler::clear);
        javalin.exception(ResponseException.class, this::exceptionHandler);
    }
    private void exceptionHandler(ResponseException ex, Context ctx) {
        ctx.status(ex.code());
        ctx.result(ex.toJson());
    }
    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

}
