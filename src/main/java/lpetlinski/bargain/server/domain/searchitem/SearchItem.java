package lpetlinski.bargain.server.domain.searchitem;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "search_items")
public class SearchItem {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    private List<Query> queries = new ArrayList<>();
    private List<Auction> auctions = new ArrayList<>();
    private List<NotInterestingAuction> notInterestingAuctions = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public List<NotInterestingAuction> getNotInterestingAuctions() {
        return notInterestingAuctions;
    }

    public void setNotInterestingAuctions(List<NotInterestingAuction> notInterestingAuctions) {
        this.notInterestingAuctions = notInterestingAuctions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
