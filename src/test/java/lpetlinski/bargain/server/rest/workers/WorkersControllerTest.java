package lpetlinski.bargain.server.rest.workers;

import lpetlinski.bargain.server.BargainServerApplication;
import lpetlinski.bargain.server.TestConfiguration;
import lpetlinski.bargain.server.TestHelper;
import lpetlinski.bargain.server.cron.LoadAuctionsWorker;
import lpetlinski.bargain.server.cron.RemoveOldAuctionsWorker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BargainServerApplication.class, TestConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/application-test.properties")
@MockBean({LoadAuctionsWorker.class, RemoveOldAuctionsWorker.class})
public class WorkersControllerTest {

    @Autowired
    private LoadAuctionsWorker loadAuctionsWorker;

    @Autowired
    private RemoveOldAuctionsWorker removeOldAuctionsWorker;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestHelper testHelper;

    @Before
    public void setup() {
        testHelper.setupUsers();
    }

    @Test
    public void testRunLoadWorker() throws Exception {
        Mockito.doNothing().when(loadAuctionsWorker).loadData();
        testHelper.performPost(mockMvc, "/workers/runLoadWorker", null, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRunLoadWorkerAsUser() throws Exception {
        Mockito.doNothing().when(loadAuctionsWorker).loadData();
        testHelper.performPost(mockMvc, "/workers/runLoadWorker", null, testHelper.createUserAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testRunRemoveOldAuctions() throws Exception {
        Mockito.doNothing().when(removeOldAuctionsWorker).removeOldAuctions();
        testHelper.performPost(mockMvc, "/workers/runRemoveOldAuctions", null, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRunRemoveOldAuctionsAsUser() throws Exception {
        Mockito.doNothing().when(removeOldAuctionsWorker).removeOldAuctions();
        testHelper.performPost(mockMvc, "/workers/runRemoveOldAuctions", null, testHelper.createUserAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
