package blog.repo.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserBean extends SimpleUserBean {

	private final @NonNull String username;
	private final @NonNull Instant createdAt;
}
