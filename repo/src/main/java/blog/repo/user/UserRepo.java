package blog.repo.user;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface UserRepo {

    CompletionStage<UUID> createUser(String username, String password);

    CompletionStage<UserBean> fetchUserByUsername(String username);
}
