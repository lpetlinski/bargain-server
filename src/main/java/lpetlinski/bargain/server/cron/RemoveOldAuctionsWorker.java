package lpetlinski.bargain.server.cron;

import lpetlinski.bargain.server.domain.searchitem.Auction;
import lpetlinski.bargain.server.domain.searchitem.NotInterestingAuction;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty("lpetlinski.bargain.server.runWorkers")
@Component
public class RemoveOldAuctionsWorker {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SearchItemRepository searchItemRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeOldAuctions() {
        logger.info("Starting removing old auctions");
        removeNotInterestingAuctions();
        removeAuctions();
        logger.info("Old auctions removed");
    }

    private void removeAuctions() {
        Date today = new Date();
        searchItemRepository.findByAuctionsEndTimeLessThan(today).forEach(item -> {
            List<Auction> oldAuctions = item.getAuctions().stream().filter(a -> a.getEndTime().before(today)).collect(
                    Collectors.toList());
            item.getAuctions().removeAll(oldAuctions);
            searchItemRepository.save(item);
        });
    }

    private void removeNotInterestingAuctions() {
        Date today = new Date();
        searchItemRepository.findByNotInterestingAuctionsEndTimeLessThan(today).forEach(item -> {
            List<NotInterestingAuction> oldAuctions = item.getNotInterestingAuctions().stream().filter(a -> a.getEndTime().before(today)).collect(
                    Collectors.toList());
            item.getNotInterestingAuctions().removeAll(oldAuctions);
            searchItemRepository.save(item);
        });
    }
}
