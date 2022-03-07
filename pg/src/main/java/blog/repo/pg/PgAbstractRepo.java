package blog.repo.pg;

import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.SqlResult;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PgAbstractRepo {
    private final @NonNull PgPool pool;

    protected final CompletionStage<SqlConnection> getConnection() {
        return pool.getConnection().toCompletionStage();
    }

}
