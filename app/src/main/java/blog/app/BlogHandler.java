package blog.app;

import blog.app.request.CreateUserRequest;
import blog.repo.pg.BlogPgRepoFactory;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlogHandler {
    private final static Logger LOGGER = Logger.getLogger(BlogHandler.class.getName());

    BlogPgRepoFactory repoFactory;

    private BlogHandler(BlogPgRepoFactory _repoFactory) {
        this.repoFactory = _repoFactory;
    }

    public static BlogHandler create(BlogPgRepoFactory blogs) {
        return new BlogHandler(blogs);
    }

    public void createUser(RoutingContext rc) {
        var body = rc.getBodyAsJson();
        var createUserRequest = body.mapTo(CreateUserRequest.class);
        LOGGER.log(Level.INFO, "request body: {0}", body);
        var createuser = this.repoFactory.getUserRepo()
                .createUser(createUserRequest.getUsername(), createUserRequest.getPassword())
                .thenApply(any -> rc.response().setStatusCode(200));

    }

    public void doPing(RoutingContext rc) {
        rc.response().setStatusCode(200).end("pong");
    }
}
