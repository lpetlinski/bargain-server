package lpetlinski.bargain.server.repository;

import lpetlinski.bargain.server.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ '_name': ?0 }")
    User findByName(String name);
}
