package blog.repo;

import blog.repo.user.UserRepo;

public interface BlogRepoFactory {
    UserRepo getUserRepo();
}
