package lpetlinski.bargain.server.services;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    boolean canSeeSearchItem(Authentication authentication, String id);
}
