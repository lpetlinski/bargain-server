package lpetlinski.bargain.server.repository;

import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface SearchItemRepository extends MongoRepository<SearchItem, String> {
    List<SearchItem> findByAuctionsEndTimeLessThan(Date date);
    List<SearchItem> findByNotInterestingAuctionsEndTimeLessThan(Date date);
    List<SearchItem> findByUsername(String username);
    @Query(value = "{ 'id' : ?0}", fields = "{ 'username' : 1 }")
    SearchItem findByIdWithUsername(String id);
}
