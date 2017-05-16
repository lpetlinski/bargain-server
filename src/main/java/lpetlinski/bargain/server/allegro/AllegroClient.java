package lpetlinski.bargain.server.allegro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import webservice.allegro.wsdl.*;

@Component
public class AllegroClient extends WebServiceGatewaySupport {

    @Value("${lpetlinski.bargain.server.allegro.webApiKey}")
    private String webApiKey;

    @Value("${lpetlinski.bargain.server.allegro.defaultCountry}")
    private Integer defaultCountry;

    public DoGetItemsListResponse getItemsList(DoGetItemsListRequest request) {

        request.setWebapiKey(webApiKey);
        if (request.getCountryId() == 0) {
            request.setCountryId(defaultCountry);
        }

        return (DoGetItemsListResponse) getWebServiceTemplate().marshalSendAndReceive(request);

    }

    public DoGetCountriesResponse getCountries(DoGetCountriesRequest request) {

        request.setWebapiKey(webApiKey);
        if (request.getCountryCode() == 0) {
            request.setCountryCode(defaultCountry);
        }

        return (DoGetCountriesResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
