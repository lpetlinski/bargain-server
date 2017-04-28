package lpetlinski.bargain.server.cron;

import lpetlinski.bargain.server.domain.Auction;
import lpetlinski.bargain.server.domain.NotInterestingAuction;
import lpetlinski.bargain.server.repository.SearchItemRepository;
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

    @Autowired
    private SearchItemRepository _searchItemRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeOldAuctions() {
        removeNotInterestingAuctions();
        removeAuctions();
    }

    private void removeAuctions() {
        Date today = new Date();
        _searchItemRepository.findByAuctionsEndTimeLessThan(today).forEach(item -> {
            List<Auction> oldAuctions = item.getAuctions().stream().filter(a -> a.getEndTime().before(today)).collect(
                    Collectors.toList());
            item.getAuctions().removeAll(oldAuctions);
            _searchItemRepository.save(item);
        });
    }

    private void removeNotInterestingAuctions() {
        Date today = new Date();
        _searchItemRepository.findByNotInterestingAuctionsEndTimeLessThan(today).forEach(item -> {
            List<NotInterestingAuction> oldAuctions = item.getNotInterestingAuctions().stream().filter(a -> a.getEndTime().before(today)).collect(
                    Collectors.toList());
            item.getNotInterestingAuctions().removeAll(oldAuctions);
            _searchItemRepository.save(item);
        });
    }
}