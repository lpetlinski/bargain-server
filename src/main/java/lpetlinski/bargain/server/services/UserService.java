package lpetlinski.bargain.server.services;

import lpetlinski.bargain.server.domain.user.User;
import lpetlinski.bargain.server.rest.user.dto.AddRoleRequestDto;
import lpetlinski.bargain.server.rest.user.dto.AddUserRequestDto;

import java.util.List;

public interface UserService {
    void addUser(AddUserRequestDto dto);
    void addRoleToUser(AddRoleRequestDto dto);
    List<User> getAllUsers();
    void changePassword(String username, String newPassword);
}
