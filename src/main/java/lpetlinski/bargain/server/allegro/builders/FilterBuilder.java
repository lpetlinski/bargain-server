package lpetlinski.bargain.server.allegro.builders;

import webservice.allegro.wsdl.ArrayOfString;
import webservice.allegro.wsdl.FilterOptionsType;

public abstract class FilterBuilder {

    protected abstract String getFilterId();

    protected abstract ArrayOfString getFilterValues();

    public FilterOptionsType build() {
        FilterOptionsType filter = new FilterOptionsType();
        filter.setFilterId(getFilterId());
        filter.setFilterValueId(getFilterValues());
        return filter;
    }
}
