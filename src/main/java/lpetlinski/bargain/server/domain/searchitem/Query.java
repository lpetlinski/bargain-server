package lpetlinski.bargain.server.domain.searchitem;

import javax.validation.constraints.NotNull;

public class Query {

    @NotNull
    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
