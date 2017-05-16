package lpetlinski.bargain.server.rest;

import lpetlinski.bargain.server.domain.User;
import lpetlinski.bargain.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    @RequestMapping(method = RequestMethod.PUT)
    public void addUser(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password) {
        User user = new User();
        user.setName(name);
        user.getRoles().add("USER");
        user.setEncryptedPassword(_passwordEncoder.encode(password));
        _userRepository.save(user);
    }
}
