package server;

import com.google.gson.Gson;
import exception.ResponseException;
import io.javalin.*;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        UserHandler handler = new UserHandler();

        javalin.post("/user", handler::register);
        javalin.post("/session",handler::login);
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
