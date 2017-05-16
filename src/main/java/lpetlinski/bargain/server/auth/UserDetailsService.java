package lpetlinski.bargain.server.auth;

import lpetlinski.bargain.server.domain.User;
import lpetlinski.bargain.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository _repository;

    @Autowired
    private Converter<User, UserDetails> _userUserDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return _userUserDetailsConverter.convert(_repository.findByName(username));
    }
}
