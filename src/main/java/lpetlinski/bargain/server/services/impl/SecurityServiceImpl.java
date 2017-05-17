package lpetlinski.bargain.server.services.impl;

import lpetlinski.bargain.server.domain.searchitem.SearchItem;
import lpetlinski.bargain.server.repository.SearchItemRepository;
import lpetlinski.bargain.server.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SearchItemRepository searchItemRepository;

    @Override
    public boolean canSeeSearchItem(Authentication authentication, String id) {
        if(authentication.getAuthorities().stream().anyMatch(auth -> "ADMIN".equals(auth.getAuthority()))) {
            return true;
        }
        SearchItem item = searchItemRepository.findByIdWithUsername(id);
        Principal principal = (Principal)authentication.getPrincipal();
        return item.getUsername().equals(principal.getName());
    }
}
