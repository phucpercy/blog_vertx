package blog.repo.pg;

import blog.repo.BlogRepoFactory;
import blog.repo.pg.user.PgUserRepo;
import blog.repo.user.UserRepo;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlogPgRepoFactory implements BlogRepoFactory {
    private final @NonNull Vertx vertx;

    @Getter
    private UserRepo userRepo;

    public void start() throws Exception {
        try {
            var poolOpts = new PoolOptions().setMaxSize(5);
            var connectOpts = new PgConnectOptions()
                    .setHost("localhost")
                    .setPort(5432)
                    .setUser("blog")
                    .setDatabase("blog")
                    .setPassword("blog@123");
            var pool = PgPool.pool(vertx, connectOpts, poolOpts);
            this.userRepo = new PgUserRepo(pool);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
