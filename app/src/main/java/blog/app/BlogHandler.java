package blog.app;

import blog.app.request.CreateUserRequest;
import blog.repo.pg.BlogPgRepoFactory;
import io.vertx.ext.web.RoutingContext;

public class BlogHandler {

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
        this.repoFactory.getUserRepo()
                .createUser(createUserRequest.getUsername(), createUserRequest.getPassword())
                .thenApply(any -> rc.response().setStatusCode(200).end(any.toString()));
    }

    public void doPing(RoutingContext rc) {
        rc.response().setStatusCode(200).end("pong");
    }
}
