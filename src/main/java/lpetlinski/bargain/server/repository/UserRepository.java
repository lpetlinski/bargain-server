package lpetlinski.bargain.server.repository;

import lpetlinski.bargain.server.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
