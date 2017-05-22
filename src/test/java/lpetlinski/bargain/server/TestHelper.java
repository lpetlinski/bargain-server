package lpetlinski.bargain.server;

import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import lpetlinski.bargain.server.rest.user.dto.AddRoleRequestDto;
import lpetlinski.bargain.server.rest.user.dto.AddUserRequestDto;
import lpetlinski.bargain.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

public class TestHelper {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";
    private static final String ADMIN_USERNAME = "testAdmin";

    private static int sequence = 100;

    private JacksonJsonProvider json = new JacksonJsonProvider();

    private UserService userService;

    public String createSampleUser() {
        AddUserRequestDto dto = new AddUserRequestDto();
        dto.setPassword("password");
        dto.setUsername(createRandomUsername());
        userService.addUser(dto);
        return dto.getUsername();
    }

    public String createRandomUsername() {
        return "testUser-" + String.valueOf(sequence++);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setupUsers() {
        try {
            AddUserRequestDto userDto = new AddUserRequestDto();
            userDto.setUsername(USERNAME);
            userDto.setPassword(PASSWORD);
            userService.addUser(userDto);

            userDto = new AddUserRequestDto();
            userDto.setUsername(ADMIN_USERNAME);
            userDto.setPassword(PASSWORD);
            userService.addUser(userDto);

            AddRoleRequestDto roleDto = new AddRoleRequestDto();
            roleDto.setUsername(ADMIN_USERNAME);
            roleDto.setRole("ADMIN");
            userService.addRoleToUser(roleDto);
        } catch (DuplicateKeyException exc) {
            // do nothing
        }
    }

    public ResultActions performDelete(MockMvc mockMvc, String url) throws Exception {
        return performDelete(mockMvc, url, null);
    }

    public ResultActions performDelete(MockMvc mockMvc, String url, String credentials) throws Exception {
        return performDelete(mockMvc, url, null, credentials);
    }

    public ResultActions performDelete(MockMvc mockMvc, String url, Object dtoToSend, String credentials) throws Exception {
        if (credentials == null) {
            credentials = createUserAuthorization();
        }
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete(url).header("Authorization", credentials);
        if (dtoToSend != null) {
            builder.content(json.toJson(dtoToSend));
            builder.contentType(MediaType.APPLICATION_JSON);
        }
        return mockMvc.perform(builder);
    }

    public ResultActions performGet(MockMvc mockMvc, String url) throws Exception {
        return performGet(mockMvc, url, null);
    }

    public ResultActions performGet(MockMvc mockMvc, String url, String credentials) throws Exception {
        if (credentials == null) {
            credentials = createUserAuthorization();
        }
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url).header("Authorization", credentials);
        return mockMvc.perform(builder);
    }

    public ResultActions performPost(MockMvc mockMvc, String url, Object dtoToSend) throws Exception {
        return performPost(mockMvc, url, dtoToSend, null);
    }

    public ResultActions performPost(MockMvc mockMvc, String url, Object dtoToSend, String credentials) throws
                                                                                                           Exception {
        if (credentials == null) {
            credentials = createUserAuthorization();
        }
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url).header("Authorization", credentials);
        if (dtoToSend != null) {
            builder.content(json.toJson(dtoToSend));
            builder.contentType(MediaType.APPLICATION_JSON);
        }
        return mockMvc.perform(builder);
    }

    public ResultActions performPut(MockMvc mockMvc, String url, Object dtoToSend) throws Exception {
        return performPut(mockMvc, url, dtoToSend, null);
    }

    public ResultActions performPut(MockMvc mockMvc, String url, Object dtoToSend, String credentials) throws
                                                                                                          Exception {
        if (credentials == null) {
            credentials = createUserAuthorization();
        }
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(url).header("Authorization", credentials);
        if (dtoToSend != null) {
            builder.content(json.toJson(dtoToSend));
            builder.contentType(MediaType.APPLICATION_JSON);
        }
        return mockMvc.perform(builder);
    }

    public String createUserAuthorization() {
        return createBasicAuthorization(USERNAME, PASSWORD);
    }

    public String createAdminAuthorization() {
        return createBasicAuthorization(ADMIN_USERNAME, PASSWORD);
    }

    public String createBasicAuthorization(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public String createDefaultUserAuthorization(String username) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + PASSWORD).getBytes());
    }
}
