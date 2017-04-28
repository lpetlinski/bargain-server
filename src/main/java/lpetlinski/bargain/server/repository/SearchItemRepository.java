package lpetlinski.bargain.server.repository;

import lpetlinski.bargain.server.domain.SearchItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SearchItemRepository extends MongoRepository<SearchItem, String> {
    List<SearchItem> findByAuctionsEndTimeLessThan(Date date);
    List<SearchItem> findByNotInterestingAuctionsEndTimeLessThan(Date date);
}
