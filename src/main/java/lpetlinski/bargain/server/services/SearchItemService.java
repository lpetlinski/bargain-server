package lpetlinski.bargain.server.services;

import lpetlinski.bargain.server.domain.searchitem.SearchItem;

import java.util.List;

public interface SearchItemService {
    String addSearchItem(String name, String username);
    SearchItem getSearchItem(String id);
    List<SearchItem> getUserSearchItems(String username);
    void removeSearchItem(String id);
    void addQueryToSearchItem(String searchItemId, String query);
    void removeQueryFromSearchItem(String searchItemId, String query);
    void moveAuctionToNotInteresting(String searchItemId, Long auctionId);
}
