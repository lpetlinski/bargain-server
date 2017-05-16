package lpetlinski.bargain.server.rest;

import lpetlinski.bargain.server.cron.LoadAuctionsWorker;
import lpetlinski.bargain.server.cron.RemoveOldAuctionsWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty("lpetlinski.bargain.server.runWorkers")
@PreAuthorize("hasAuthority('ADMIN')")
@RestController("/workers")
public class WokersController {

    @Autowired
    private LoadAuctionsWorker _loadAuctionsWorker;

    @Autowired
    private RemoveOldAuctionsWorker _removeOldAuctionsWorker;

    @RequestMapping("/runLoadWorker")
    public void runLoadWorker() {
        _loadAuctionsWorker.loadData();
    }

    @RequestMapping("/runRemoveOldAuctions")
    public void runRemoveOldAuctions() {
        _removeOldAuctionsWorker.removeOldAuctions();
    }
}
