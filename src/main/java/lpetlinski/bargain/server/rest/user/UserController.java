package lpetlinski.bargain.server.rest.user;

import lpetlinski.bargain.server.rest.user.dto.AddRoleRequestDto;
import lpetlinski.bargain.server.rest.user.dto.AddUserRequestDto;
import lpetlinski.bargain.server.rest.user.dto.ChangePasswordOfUserRequestDto;
import lpetlinski.bargain.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService _userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    public void addUser(@RequestBody AddUserRequestDto addUserDto) {
        _userService.addUser(addUserDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<String> getAllUsersNames() {
        return _userService.getAllUsers().stream().map(user -> user.getUsername()).collect(Collectors.toList());
    }

    @RequestMapping(path = "/role", method = RequestMethod.GET)
    public List<String> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/role", method = RequestMethod.POST)
    public void addRoleToUser(@RequestBody AddRoleRequestDto requestDto) {
        _userService.addRoleToUser(requestDto);
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    public void changePassword(Principal principal, @RequestBody String newPassword) {
        newPassword = newPassword.replace("\"", "");
        _userService.changePassword(principal.getName(), newPassword);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/changePasswordOfUser", method = RequestMethod.POST)
    public void changePasswordOfUser(@RequestBody ChangePasswordOfUserRequestDto dto) {
        _userService.changePassword(dto.getUsername(), dto.getNewPassword());
    }
}
