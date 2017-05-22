package lpetlinski.bargain.server.cron;

import lpetlinski.bargain.server.BargainServerApplication;
import lpetlinski.bargain.server.TestConfiguration;
import lpetlinski.bargain.server.TestHelper;
import lpetlinski.bargain.server.allegro.AllegroClient;
import lpetlinski.bargain.server.domain.searchitem.Auction;
import lpetlinski.bargain.server.domain.searchitem.NotInterestingAuction;
import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BargainServerApplication.class, TestConfiguration.class})
@TestPropertySource(locations = "classpath:/application-test.properties")
public class RemoveOldAuctionsWorkerTest {
    private static final Long OLD_AUCTION_ID = (long) 1;
    private static final Long OLD_NOT_INTERESTING_AUCTION_ID = (long) 2;
    private static final Long AUCTION_ID = (long) 3;
    private static final Long NOT_INTERESTING_AUCTION_ID = (long) 4;

    @Autowired
    private SearchItemRepository repository;

    @Autowired
    private RemoveOldAuctionsWorker removeOldAuctionsWorker;

    @Test
    public void testRemoveOldAuctions() {
        String itemId = createSearchItem();

        removeOldAuctionsWorker.removeOldAuctions();

        SearchItem item = repository.findOne(itemId);

        Assert.assertEquals("Invalid number of auctions", 1, item.getAuctions().size());
        Assert.assertEquals("Invalid id of remaining auction", AUCTION_ID, item.getAuctions().get(0).getOfferId());
        Assert.assertEquals("Invalid number of not interesting auctions", 1, item.getNotInterestingAuctions().size());
        Assert.assertEquals("Invalid id of remaining not interesting auction", NOT_INTERESTING_AUCTION_ID,
                            item.getNotInterestingAuctions().get(0).getOfferId());
    }

    private String createSearchItem() {
        SearchItem item = new SearchItem();
        item.setName("Some name");
        item.setUsername("Some username");

        Auction auction = new Auction();
        auction.setEndTime(new GregorianCalendar(2016, 10, 1).getTime());
        auction.setOfferId(OLD_AUCTION_ID);
        item.getAuctions().add(auction);
        auction = new Auction();
        Calendar today = new GregorianCalendar();
        today.set(Calendar.MONTH, today.get(Calendar.MONTH)+1);
        auction.setEndTime(today.getTime());
        auction.setOfferId(AUCTION_ID);
        item.getAuctions().add(auction);

        NotInterestingAuction nAuction = new NotInterestingAuction();
        nAuction.setOfferId(OLD_NOT_INTERESTING_AUCTION_ID);
        nAuction.setEndTime(new GregorianCalendar(2016, 10, 1).getTime());
        item.getNotInterestingAuctions().add(nAuction);
        nAuction = new NotInterestingAuction();
        nAuction.setOfferId(NOT_INTERESTING_AUCTION_ID);
        nAuction.setEndTime(today.getTime());
        item.getNotInterestingAuctions().add(nAuction);

        return repository.save(item).getId();
    }
}
