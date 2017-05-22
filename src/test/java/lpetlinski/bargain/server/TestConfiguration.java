package lpetlinski.bargain.server;

import lpetlinski.bargain.server.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public TestHelper testHelper(UserService userService) {
        TestHelper helper = new TestHelper();
        helper.setUserService(userService);
        return helper;
    }
}
