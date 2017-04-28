package lpetlinski.bargain.server.allegro.builders;

import webservice.allegro.wsdl.ArrayOfFilteroptionstype;
import webservice.allegro.wsdl.DoGetItemsListRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetItemsListBuilder {
    private List<FilterBuilder> _filters = new ArrayList<>();

    public GetItemsListBuilder withFilter(FilterBuilder filter) {
        _filters.add(filter);
        return this;
    }

    public DoGetItemsListRequest build() {
        DoGetItemsListRequest request = new DoGetItemsListRequest();

        ArrayOfFilteroptionstype filters = new ArrayOfFilteroptionstype();
        filters.getItem().addAll(_filters.stream().map((filter) -> filter.build()).collect(Collectors.toList()));

        request.setFilterOptions(filters);

        return request;
    }
}
