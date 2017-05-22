package lpetlinski.bargain.server.cron;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import lpetlinski.bargain.server.BargainServerApplication;
import lpetlinski.bargain.server.TestConfiguration;
import lpetlinski.bargain.server.TestHelper;
import lpetlinski.bargain.server.allegro.AllegroClient;
import lpetlinski.bargain.server.domain.searchitem.Auction;
import lpetlinski.bargain.server.domain.searchitem.NotInterestingAuction;
import lpetlinski.bargain.server.domain.searchitem.Query;
import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import webservice.allegro.wsdl.*;

import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BargainServerApplication.class, TestConfiguration.class})
@TestPropertySource(locations = "classpath:/application-test.properties")
@MockBean({AllegroClient.class})
public class LoadAuctionsWorkerTest {
    private static final Long EXISTING_AUCTION_ID = (long)1;
    private static final Long NOT_INTERESTING_AUCTION_ID = (long)2;
    private static final Long NOT_EXISTING_AUCTION_ID = (long)3;

    @Autowired
    private AllegroClient allegroClient;

    @Autowired
    private SearchItemRepository repository;

    @Autowired
    private LoadAuctionsWorker loadAuctionsWorker;

    @Test
    public void testLoadAuctionsWorker() {
        mockAllegroClient();
        String itemId = createSampleSearchItem();

        loadAuctionsWorker.loadData();

        SearchItem item = repository.findOne(itemId);
        Assert.assertEquals("Invalid number of auctions", 2, item.getAuctions().size());
        Assert.assertEquals("Invalid number of not interesting auctions", 1, item.getNotInterestingAuctions().size());

        Assert.assertTrue("There should be not changed auction as it was", item.getAuctions().stream().anyMatch(a -> a.getOfferId() == EXISTING_AUCTION_ID));
        Assert.assertTrue("There should be not changed not interesting auction", item.getNotInterestingAuctions().stream().anyMatch(a -> a.getOfferId() == NOT_INTERESTING_AUCTION_ID));
        Assert.assertTrue("There should be new auction", item.getAuctions().stream().anyMatch(a -> a.getOfferId() == NOT_EXISTING_AUCTION_ID));

        Auction newAuction = item.getAuctions().stream().filter(a -> a.getOfferId() == NOT_EXISTING_AUCTION_ID).findFirst().get();
        Assert.assertEquals("Invalid end time", new GregorianCalendar(2017, 10, 1).getTime(), newAuction.getEndTime());
        Assert.assertEquals("Invalid left count", 10, newAuction.getCount());
        Assert.assertTrue("There should be \"some url1\" in photos", newAuction.getPhotos().contains("some url1"));
        Assert.assertTrue("There should be \"some url2\" in photos", newAuction.getPhotos().contains("some url2"));
        Assert.assertEquals("Invalid buyout price", 11.7f, newAuction.getBuyNowPrice(), 0.1f);
        Assert.assertEquals("Invalid bidding price", 10.7f, newAuction.getBiddingPrice(), 0.1f);
        Assert.assertEquals("Invalid with delivery price", 100.7f, newAuction.getWithDeliveryPrice(), 0.1f);
    }

    private String createSampleSearchItem() {
        SearchItem item = new SearchItem();
        item.setName("Some name");
        item.setUsername("Some username");

        Auction auction = new Auction();
        auction.setOfferId(EXISTING_AUCTION_ID);
        item.getAuctions().add(auction);

        NotInterestingAuction nAuction = new NotInterestingAuction();
        nAuction.setOfferId(NOT_INTERESTING_AUCTION_ID);
        item.getNotInterestingAuctions().add(nAuction);

        Query query = new Query();
        query.setSearchQuery("Some search query");
        item.getQueries().add(query);

        return repository.save(item).getId();
    }

    private void mockAllegroClient() {
        DoGetItemsListResponse response = new DoGetItemsListResponse();
        ArrayOfItemslisttype itemsList = new ArrayOfItemslisttype();

        ItemsListType elem1 = new ItemsListType();
        elem1.setItemId(EXISTING_AUCTION_ID);
        itemsList.getItem().add(elem1);

        ItemsListType elem2 = new ItemsListType();
        elem2.setItemId(NOT_INTERESTING_AUCTION_ID);
        itemsList.getItem().add(elem2);

        ItemsListType elem3 = new ItemsListType();
        elem3.setItemId(NOT_EXISTING_AUCTION_ID);
        elem3.setEndingTime(new XMLGregorianCalendarImpl(new GregorianCalendar(2017, 10, 1)));
        elem3.setLeftCount(10);

        ArrayOfPhotoinfotype photos = new ArrayOfPhotoinfotype();
        PhotoInfoType photo1 = new PhotoInfoType();
        photo1.setPhotoUrl("some url1");
        photos.getItem().add(photo1);
        PhotoInfoType photo2 = new PhotoInfoType();
        photo2.setPhotoUrl("some url2");
        photos.getItem().add(photo2);

        elem3.setPhotosInfo(photos);

        ArrayOfPriceinfotype prices = new ArrayOfPriceinfotype();
        PriceInfoType buyout = new PriceInfoType();
        buyout.setPriceType("buyNow");
        buyout.setPriceValue(11.7f);
        prices.getItem().add(buyout);
        PriceInfoType bidding = new PriceInfoType();
        bidding.setPriceType("bidding");
        bidding.setPriceValue(10.7f);
        prices.getItem().add(bidding);
        PriceInfoType withDelivery = new PriceInfoType();
        withDelivery.setPriceType("withDelivery");
        withDelivery.setPriceValue(100.7f);
        prices.getItem().add(withDelivery);

        elem3.setPriceInfo(prices);
        itemsList.getItem().add(elem3);

        response.setItemsList(itemsList);

        Mockito.when(allegroClient.getItemsList(Mockito.any())).thenReturn(response);
    }
}
