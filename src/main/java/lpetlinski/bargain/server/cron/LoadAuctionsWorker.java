package lpetlinski.bargain.server.cron;

import lpetlinski.bargain.server.allegro.AllegroClient;
import lpetlinski.bargain.server.allegro.builders.GetItemsListBuilder;
import lpetlinski.bargain.server.allegro.builders.SearchFilterBuilder;
import lpetlinski.bargain.server.domain.searchitem.Auction;
import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webservice.allegro.wsdl.DoGetItemsListResponse;
import webservice.allegro.wsdl.ItemsListType;

import java.util.stream.Collectors;

@ConditionalOnProperty("lpetlinski.bargain.server.runWorkers")
@Component
public class LoadAuctionsWorker {

    @Autowired
    private SearchItemRepository searchItemRepository;

    @Autowired
    private AllegroClient allegroClient;

    @Scheduled(cron = "0 0 * * * *")
    public void loadData() {
        searchItemRepository.findAll(new PageRequest(0, 5)).forEach((searchItem -> {
            findAuctionsForSearchItem(searchItem);
            searchItemRepository.save(searchItem);
        }));
    }

    private void findAuctionsForSearchItem(SearchItem searchItem) {
        searchItem.getQueries().forEach((query) -> {
            try {
                DoGetItemsListResponse response = findAuctions(query.getSearchQuery());
                mapResponseToSearchItem(response, searchItem);
            } catch (Exception exc) {
                // TODO do something
            }
        });
    }

    private DoGetItemsListResponse findAuctions(String query) {
        return allegroClient.getItemsList(
                new GetItemsListBuilder().withFilter(new SearchFilterBuilder().withSearchValue(query)).build());
    }

    private void mapResponseToSearchItem(DoGetItemsListResponse response, SearchItem searchItem) {
        response.getItemsList().getItem().stream().filter((item) -> !searchItem.getNotInterestingAuctions().stream()
                                                                               .anyMatch((a) -> a.getOfferId()
                                                                                                 .equals(item.getItemId())) && !searchItem
                .getAuctions().stream().anyMatch((a) -> a.getOfferId().equals(item.getItemId()))).forEach((item) -> {
            Auction newAuction = new Auction();
            newAuction.setCount(item.getLeftCount());
            newAuction.setEndTime(item.getEndingTime().toGregorianCalendar().getTime());
            newAuction.setOfferId(item.getItemId());
            setPrices(item, newAuction);
            newAuction.setTitle(item.getItemTitle());
            newAuction.setPhotos(item.getPhotosInfo().getItem().stream().map((photo) -> photo.getPhotoUrl())
                                     .collect(Collectors.toList()));
            searchItem.getAuctions().add(newAuction);
        });
    }

    private void setPrices(ItemsListType item, Auction newAuction) {
        item.getPriceInfo().getItem().forEach(p -> {
            switch(p.getPriceType()) {
                case "buyNow":
                    newAuction.setBuyNowPrice(p.getPriceValue());
                    break;
                case "bidding":
                    newAuction.setBiddingPrice(p.getPriceValue());
                    break;
                case "withDelivery":
                    newAuction.setWithDeliveryPrice(p.getPriceValue());
                    break;
            }
        });
    }
}
