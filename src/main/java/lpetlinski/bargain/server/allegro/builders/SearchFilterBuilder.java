package lpetlinski.bargain.server.allegro.builders;

import webservice.allegro.wsdl.ArrayOfString;

public class SearchFilterBuilder extends FilterBuilder {

    private String _searchValue;

    public SearchFilterBuilder withSearchValue(String searchValue) {
        _searchValue = searchValue;
        return this;
    }

    @Override
    protected String getFilterId() {
        return "search";
    }

    @Override
    protected ArrayOfString getFilterValues() {
        ArrayOfString result = new ArrayOfString();
        result.getItem().add(_searchValue);
        return result;
    }
}
