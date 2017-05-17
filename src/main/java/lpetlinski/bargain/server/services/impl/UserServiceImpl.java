package lpetlinski.bargain.server.services.impl;

import lpetlinski.bargain.server.domain.user.User;
import lpetlinski.bargain.server.repository.UserRepository;
import lpetlinski.bargain.server.rest.user.dto.AddRoleRequestDto;
import lpetlinski.bargain.server.rest.user.dto.AddUserRequestDto;
import lpetlinski.bargain.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(AddUserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.getRoles().add("USER");
        user.setEncryptedPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void addRoleToUser(AddRoleRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        user.getRoles().add(dto.getRole());
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        user.setEncryptedPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
