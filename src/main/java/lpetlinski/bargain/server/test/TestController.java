package lpetlinski.bargain.server.test;

import lpetlinski.bargain.server.allegro.AllegroClient;
import lpetlinski.bargain.server.cron.LoadAuctionsWorker;
import lpetlinski.bargain.server.domain.Query;
import lpetlinski.bargain.server.domain.SearchItem;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Autowired
    private AllegroClient allegroClient;

    @Autowired
    private LoadAuctionsWorker _worker;

    @Autowired
    private SearchItemRepository _searchItemRepository;

    @RequestMapping("/countries")
    public List<CountryDto> getCountries() {
        return allegroClient.getCountries(null).getCountryArray().getItem().stream().map((country) -> new CountryDto(country.getCountryId(), country.getCountryName())).collect(
                Collectors.toList());
    }

    @RequestMapping("/itemsList")
    public String getItemsList() {
        return allegroClient.getItemsList(null).toString();
    }

    @RequestMapping("/loadAuctions")
    public void runLoadAuctions() {
        _worker.loadData();
    }

    @RequestMapping("/addSearchItem")
    public void addSearchItem() {
        SearchItem item = new SearchItem();
        item.setName("Sample name");
        Query query = new Query();
        query.setSearchQuery("Mroczny przypływ");
        item.getQueries().add(query);
        _searchItemRepository.save(item);
    }
}
