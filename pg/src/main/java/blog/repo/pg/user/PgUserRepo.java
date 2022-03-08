package blog.repo.pg.user;

import blog.repo.user.UserBean;
import blog.repo.user.UserRepo;
import io.vertx.pgclient.PgPool;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class PgUserRepo implements UserRepo {
    private final PgPool client;

    public PgUserRepo(PgPool _client) {
        this.client = _client;
    }

    @Override
    public CompletionStage<UUID> createUser(String username, String password) {
        var sql = String.format("insert into public.\"user\" (id,  \"username\" , \"password\") " +
                "values (uuid_generate_v1(), '%s', '%s') returning id", username, password);
        return client
                .getConnection()
                .toCompletionStage()
                .thenCompose(connection -> connection
                        .query(sql)
                        .execute()
                        .toCompletionStage()
                        .thenApply(rows -> rows.iterator().next().getUUID("id"))
                        .whenComplete((any, exception) -> connection.close()))
                .whenComplete((any, exception) -> {
                    if (exception == null) {
                        System.out.println("Done");
                    } else {
                        System.out.println("Something went wrong " + exception.getMessage());
                    }
                });
    }

    @Override
    public CompletionStage<UserBean> fetchUserByUsername(String username) {
        return null;
    }
}
