package blog.app;

import blog.repo.pg.BlogPgRepoFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class BlogApp extends AbstractVerticle {
    private HttpServer httpServer;

    private CompletionStage<Vertx> initVertx() {
        return CompletableFuture.completedStage(Vertx.vertx());
    }

    public void start() throws Exception {
        initVertx() //
                .thenAccept(this::onVertxReady) //
                .thenAccept(any -> {
                    try {
                        this.httpServer.listen().toCompletionStage().toCompletableFuture().get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("abc");
                    }
                });
    }

    private void onVertxReady(Vertx vertx) {
        var repoFactory = initRepoFactory(vertx);
        initHttpServer(vertx, repoFactory);
    }

    private void initHttpServer(Vertx vertx, BlogPgRepoFactory repoFactory) {
        var blogHandlers = BlogHandler.create(repoFactory);
        var router = routes(blogHandlers);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(9000)
                .onSuccess(server -> {
                    this.httpServer = server;
                });
    }

    private BlogPgRepoFactory initRepoFactory(Vertx vertx) {
        var repoFactory = new BlogPgRepoFactory(vertx);
        try {
            repoFactory.start();
        } catch (Exception e) {
            throw new RuntimeException("error when start pg repo factory");
        }
        return repoFactory;
    }

    private Router routes(BlogHandler handlers) {

        Router router = Router.router(vertx);
        router.post("/users").consumes("application/json").handler(BodyHandler.create()).handler(handlers::createUser);
        router.get("/ping").handler(handlers::doPing);

        return router;
    }
}
