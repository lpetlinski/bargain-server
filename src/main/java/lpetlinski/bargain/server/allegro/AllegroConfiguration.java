package lpetlinski.bargain.server.allegro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class AllegroConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("webservice.allegro.wsdl");
        return marshaller;
    }

    @Bean
    public AllegroClient allegroClient(Jaxb2Marshaller marshaller) {
        AllegroClient client = new AllegroClient();
        client.setDefaultUri("https://webapi.allegro.pl/service.php");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
