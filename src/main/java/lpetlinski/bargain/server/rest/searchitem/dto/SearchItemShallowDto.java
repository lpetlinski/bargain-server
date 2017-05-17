package lpetlinski.bargain.server.rest.searchitem.dto;

import lpetlinski.bargain.server.domain.searchitem.SearchItem;

public class SearchItemShallowDto {
    private String id;
    private String name;
    private Integer count;

    public SearchItemShallowDto() {
        // do nothing
    }

    public SearchItemShallowDto(SearchItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.count = item.getAuctions().size();
    }

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
