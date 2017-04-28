package lpetlinski.bargain.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "search_items")
public class SearchItem {

    @Id
    private String _id;
    
    private String _name;
    private List<Query> _queries = new ArrayList<>();
    private List<Auction> _auctions = new ArrayList<>();
    private List<NotInterestingAuction> _notInterestingAuctions = new ArrayList<>();

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public List<Query> getQueries() {
        return _queries;
    }

    public void setQueries(List<Query> queries) {
        _queries = queries;
    }

    public List<Auction> getAuctions() {
        return _auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        _auctions = auctions;
    }

    public List<NotInterestingAuction> getNotInterestingAuctions() {
        return _notInterestingAuctions;
    }

    public void setNotInterestingAuctions(List<NotInterestingAuction> notInterestingAuctions) {
        _notInterestingAuctions = notInterestingAuctions;
    }
}
