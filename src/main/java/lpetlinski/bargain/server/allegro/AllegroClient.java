package lpetlinski.bargain.server.allegro;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import webservice.allegro.wsdl.*;

public class AllegroClient extends WebServiceGatewaySupport {

    private static final int COUNTRY = 1; // TODO move to properties

    public DoGetItemsListResponse getItemsList(DoGetItemsListRequest request) {

        request.setWebapiKey("aaa"); // TODO api key
        if (request.getCountryId() == 0) {
            request.setCountryId(COUNTRY);
        }

        return (DoGetItemsListResponse) getWebServiceTemplate().marshalSendAndReceive(request);

    }

    public DoGetCountriesResponse getCountries(DoGetCountriesRequest request) {

        request.setWebapiKey("aaa"); // TODO api key
        if (request.getCountryCode() == 0) {
            request.setCountryCode(COUNTRY);
        }

        return (DoGetCountriesResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
