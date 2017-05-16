package lpetlinski.bargain.server.rest;

import lpetlinski.bargain.server.cron.LoadAuctionsWorker;
import lpetlinski.bargain.server.cron.RemoveOldAuctionsWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty("lpetlinski.bargain.server.runWorkers")
@RestController
@RequestMapping("/workers")
public class WokersController {

    @Autowired
    private LoadAuctionsWorker _loadAuctionsWorker;

    @Autowired
    private RemoveOldAuctionsWorker _removeOldAuctionsWorker;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/runLoadWorker", method = RequestMethod.POST)
    public void runLoadWorker() {
        _loadAuctionsWorker.loadData();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/runRemoveOldAuctions", method = RequestMethod.POST)
    public void runRemoveOldAuctions() {
        _removeOldAuctionsWorker.removeOldAuctions();
    }
}
