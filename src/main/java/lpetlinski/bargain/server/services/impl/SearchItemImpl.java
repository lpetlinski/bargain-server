package lpetlinski.bargain.server.services.impl;

import lpetlinski.bargain.server.domain.searchitem.Auction;
import lpetlinski.bargain.server.domain.searchitem.NotInterestingAuction;
import lpetlinski.bargain.server.domain.searchitem.Query;
import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import lpetlinski.bargain.server.services.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchItemImpl implements SearchItemService {

    @Autowired
    private SearchItemRepository searchItemRepository;

    @Override
    public String addSearchItem(String name, String username) {
        SearchItem item = new SearchItem();
        item.setName(name);
        item.setUsername(username);
        item = searchItemRepository.save(item);
        return item.getId();
    }

    @Override
    public SearchItem getSearchItem(String id) {
        return searchItemRepository.findOne(id);
    }

    @Override
    public List<SearchItem> getUserSearchItems(String username) {
        return searchItemRepository.findByUsername(username);
    }

    @Override
    public void removeSearchItem(String id) {
        searchItemRepository.delete(id);
    }

    @Override
    public void addQueryToSearchItem(String searchItemId, String query) {
        SearchItem item = searchItemRepository.findOne(searchItemId);
        Query newQuery = new Query();
        newQuery.setSearchQuery(query);
        item.getQueries().add(newQuery);
        searchItemRepository.save(item);
    }

    @Override
    public void removeQueryFromSearchItem(String searchItemId, String query) {
        SearchItem item = searchItemRepository.findOne(searchItemId);
        Optional<Query> queryToDelete = item.getQueries().stream().filter(q -> q.getSearchQuery().equals(query)).findFirst();
        if(queryToDelete.isPresent()) {
            item.getQueries().remove(queryToDelete.get());
            searchItemRepository.save(item);
        }
    }

    @Override
    public void moveAuctionToNotInteresting(String searchItemId, Long auctionId) {
        SearchItem item = searchItemRepository.findOne(searchItemId);
        Optional<Auction> auctionToMove = item.getAuctions().stream().filter(a -> a.getOfferId().equals(auctionId)).findFirst();
        if(auctionToMove.isPresent()) {
            item.getAuctions().remove(auctionToMove.get());
            NotInterestingAuction nia = new NotInterestingAuction();
            nia.setEndTime(auctionToMove.get().getEndTime());
            nia.setOfferId(auctionToMove.get().getOfferId());
            item.getNotInterestingAuctions().add(nia);
            searchItemRepository.save(item);
        }
    }
}
