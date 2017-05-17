package lpetlinski.bargain.server.rest.searchitem;

import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import lpetlinski.bargain.server.rest.searchitem.dto.SearchItemFullDto;
import lpetlinski.bargain.server.rest.searchitem.dto.SearchItemShallowDto;
import lpetlinski.bargain.server.services.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/searchItem")
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public String addSearchItem(Principal principal, @RequestBody String name) {
        return searchItemService.addSearchItem(name, principal.getName());
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<SearchItemShallowDto> getAllSearchItems(Principal principal) {
        List<SearchItem> items = searchItemService.getUserSearchItems(principal.getName());
        return items.stream().map(item -> new SearchItemShallowDto(item)).collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.canSeeSearchItem(authentication, #id)")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public SearchItemFullDto getSearchItem(Principal principal, @PathVariable("id") String id) {
        SearchItem item = searchItemService.getSearchItem(id);
        return new SearchItemFullDto(item);
    }

    @PreAuthorize("@securityService.canSeeSearchItem(authentication, #id)")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void removeSearchItem(Principal principal, @PathVariable("id") String id) {
        searchItemService.removeSearchItem(id);
    }

    @PreAuthorize("@securityService.canSeeSearchItem(authentication, #id)")
    @RequestMapping(path = "/{id}/query", method = RequestMethod.PUT)
    public void addQuery(@PathVariable("id") String id, @RequestBody String query) {
        searchItemService.addQueryToSearchItem(id, query);
    }

    @PreAuthorize("@securityService.canSeeSearchItem(authentication, #id)")
    @RequestMapping(path = "/{id}/query", method = RequestMethod.DELETE)
    public void deleteQuery(@PathVariable("id") String id, @RequestBody String query) {
        searchItemService.removeQueryFromSearchItem(id, query);
    }

    @PreAuthorize("@securityService.canSeeSearchItem(authentication, #id)")
    @RequestMapping(path = "/{id}/auction/{auctionId}/move", method = RequestMethod.POST)
    public void moveAuctionToNotInteresting(@PathVariable("id") String id, @PathVariable("auctionId") Long auctionId) {
        searchItemService.moveAuctionToNotInteresting(id, auctionId);
    }
}
