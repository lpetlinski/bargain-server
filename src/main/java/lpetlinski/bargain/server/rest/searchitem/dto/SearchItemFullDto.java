package lpetlinski.bargain.server.rest.searchitem.dto;

import lpetlinski.bargain.server.domain.searchitem.SearchItem;

import java.util.List;
import java.util.stream.Collectors;

public class SearchItemFullDto extends SearchItemShallowDto {
    private List<AuctionDto> auctions;
    private List<String> queries;
    private List<Long> notInterestingAuctions;

    public SearchItemFullDto(SearchItem item) {
        super(item);
        auctions = item.getAuctions().stream().map(auction -> new AuctionDto(auction)).collect(Collectors.toList());
        queries = item.getQueries().stream().map(query -> query.getSearchQuery()).collect(Collectors.toList());
        notInterestingAuctions = item.getNotInterestingAuctions().stream().map(nia -> nia.getOfferId()).collect(Collectors.toList());
    }

    public List<AuctionDto> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<AuctionDto> auctions) {
        this.auctions = auctions;
    }

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }

    public List<Long> getNotInterestingAuctions() {
        return notInterestingAuctions;
    }

    public void setNotInterestingAuctions(List<Long> notInterestingAuctions) {
        this.notInterestingAuctions = notInterestingAuctions;
    }
}
