package blog.repo.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleUserBean {

	private final @NonNull UUID id;
	private final @NonNull String password;
}
