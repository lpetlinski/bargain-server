package lpetlinski.bargain.server.rest.user;

import lpetlinski.bargain.server.BargainServerApplication;
import lpetlinski.bargain.server.TestConfiguration;
import lpetlinski.bargain.server.TestHelper;
import lpetlinski.bargain.server.domain.user.User;
import lpetlinski.bargain.server.rest.user.dto.AddRoleRequestDto;
import lpetlinski.bargain.server.rest.user.dto.AddUserRequestDto;
import lpetlinski.bargain.server.rest.user.dto.ChangePasswordOfUserRequestDto;
import lpetlinski.bargain.server.services.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BargainServerApplication.class, TestConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/application-test.properties")
public class UserControllerTest {

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        testHelper.setupUsers();
    }

    @Test
    public void testAddUser() throws Exception {
        AddUserRequestDto dto = new AddUserRequestDto();
        dto.setUsername(testHelper.createRandomUsername());
        dto.setPassword("testPassword");

        testHelper.performPut(mockMvc, "/user", dto, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/user/role").andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddUserAsNotAdmin() throws Exception {
        AddUserRequestDto dto = new AddUserRequestDto();
        dto.setUsername(testHelper.createRandomUsername());
        dto.setPassword("testPassword");

        testHelper.performPut(mockMvc, "/user", dto).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testGetAllUsersNames() throws Exception {
        List<String> userNames = userService.getAllUsers().stream().map(User::getUsername).collect(Collectors.toList());

        testHelper.performGet(mockMvc, "/user", testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(userNames.size())))
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder(userNames.toArray())));
    }

    @Test
    public void testGetAllUsersNamesAsNotAdmin() throws Exception {
        testHelper.performGet(mockMvc, "/user").andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testGetRolesAsUser() throws Exception {
        testHelper.performGet(mockMvc, "/user/role").andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                  .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("USER")));
    }

    @Test
    public void testGetRolesAsAdmin() throws Exception {
        testHelper.performGet(mockMvc, "/user/role", testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder("USER", "ADMIN")));
    }

    @Test
    public void testAddRoleToUser() throws Exception {
        String username = testHelper.createSampleUser();

        AddRoleRequestDto addRole = new AddRoleRequestDto();
        addRole.setRole("ADMIN");
        addRole.setUsername(username);
        testHelper.performPost(mockMvc, "/user/role", addRole, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/user/role", testHelper.createBasicAuthorization(username, "password"))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder("USER", "ADMIN")));
    }

    @Test
    public void testAddRoleToUserAsNotAdmin() throws Exception {
        String username = testHelper.createSampleUser();

        AddRoleRequestDto addRole = new AddRoleRequestDto();
        addRole.setRole("ADMIN");
        addRole.setUsername(username);
        testHelper.performPost(mockMvc, "/user/role", addRole).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testChangePassword() throws Exception {
        String username = testHelper.createSampleUser();

        String newPassword = "someNewPassword";
        testHelper.performPost(mockMvc, "/user/changePassword", newPassword,
                               testHelper.createBasicAuthorization(username, "password"))
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/user/role", testHelper.createBasicAuthorization(username, "password"))
                  .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        testHelper.performGet(mockMvc, "/user/role", testHelper.createBasicAuthorization(username, newPassword))
                  .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testChangePasswordOfUser() throws Exception {
        String username = testHelper.createSampleUser();

        ChangePasswordOfUserRequestDto changeDto = new ChangePasswordOfUserRequestDto();
        changeDto.setUsername(username);
        changeDto.setNewPassword("someNewPassword");
        testHelper.performPost(mockMvc, "/user/changePasswordOfUser", changeDto, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/user/role", testHelper.createBasicAuthorization(username, "password"))
                  .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        testHelper.performGet(mockMvc, "/user/role",
                              testHelper.createBasicAuthorization(username, changeDto.getNewPassword()))
                  .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testChangePasswordOfUserAsNotAdmin() throws Exception {
        String username = testHelper.createSampleUser();

        ChangePasswordOfUserRequestDto changeDto = new ChangePasswordOfUserRequestDto();
        changeDto.setUsername(username);
        changeDto.setNewPassword("someNewPassword");
        testHelper.performPost(mockMvc, "/user/changePasswordOfUser", changeDto)
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
