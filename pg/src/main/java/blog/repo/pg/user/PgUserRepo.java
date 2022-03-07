package blog.repo.pg.user;

import blog.repo.pg.PgAbstractRepo;
import blog.repo.user.UserBean;
import blog.repo.user.UserRepo;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Tuple;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PgUserRepo implements UserRepo {
    private final PgPool client;

    public PgUserRepo(PgPool _client) {
        this.client = _client;
    }

    @Override
    public CompletionStage<UUID> createUser(String username, String password) {
        var sql = String.format("insert into public.\"user\" (id, username, \"password\") "
        + " values (uuid_generate_v1(), %s, %s) returning id", username, password);
        return client
                .getConnection()
                .toCompletionStage()
                .thenApply(connetion -> {
                    return connetion
                            .query(sql)
                            .execute()
                            .map(rs -> {
                                return rs.iterator().next().getUUID("id");
                            })
                            .onSuccess(id -> {
                                connetion.close();
                                return id;
                            });
                });
//                .toCompletionStage();
    }

    @Override
    public CompletionStage<UserBean> fetchUserByUsername(String username) {
        return null;
    }
}
