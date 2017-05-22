package lpetlinski.bargain.server.rest.searchitem;

import lpetlinski.bargain.server.BargainServerApplication;
import lpetlinski.bargain.server.TestConfiguration;
import lpetlinski.bargain.server.TestHelper;
import lpetlinski.bargain.server.services.SearchItemService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BargainServerApplication.class, TestConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/application-test.properties")
public class SearchItemControllerTest {
    @Autowired
    private TestHelper testHelper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SearchItemService searchItemService;

    @Before
    public void setup() {
        testHelper.setupUsers();
    }

    @Test
    public void testAddSearchItem() throws Exception {
        String username = testHelper.createSampleUser();
        String searchName = "SearchItemName";

        testHelper.performPut(mockMvc, "/searchItem", searchName, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
    }

    @Test
    public void testGetAllSearchItems() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem1 = "SearchItem1";
        String searchItem2 = "SearchItem2";

        String id1 = createSearchItem(searchItem1, username);
        String id2 = createSearchItem(searchItem2, username);

        testHelper.performGet(mockMvc, "/searchItem", testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                  .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", Matchers.containsInAnyOrder(id1, id2)))
                  .andExpect(MockMvcResultMatchers
                                     .jsonPath("$[*].name", Matchers.containsInAnyOrder(searchItem1, searchItem2)));
    }

    @Test
    public void testGetSearchItem() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.comparesEqualTo(id)))
                  .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.comparesEqualTo(searchItem)))
                  .andExpect(MockMvcResultMatchers.jsonPath("count", Matchers.comparesEqualTo(0)));
    }

    @Test
    public void testGetSearchItemAsUnauthorizedUser() throws Exception {
        String username = testHelper.createSampleUser();
        String username2 = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username2))
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testGetSearchItemAsAdmin() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.comparesEqualTo(id)))
                  .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.comparesEqualTo(searchItem)))
                  .andExpect(MockMvcResultMatchers.jsonPath("count", Matchers.comparesEqualTo(0)));
    }

    @Test
    public void testRemoveSearchItem() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);

        testHelper.performGet(mockMvc, "/searchItem", testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

        testHelper.performDelete(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/searchItem", testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testRemoveSearchItemAsUnauthorizedUser() throws Exception {
        String username = testHelper.createSampleUser();
        String username2 = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);

        testHelper.performDelete(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username2))
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testRemoveSearchItemAsAdmin() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);

        testHelper.performGet(mockMvc, "/searchItem", testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

        testHelper.performDelete(mockMvc, "/searchItem/" + id, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/searchItem", testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testAddQuery() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);
        String query = "some random query";

        testHelper.performPut(mockMvc, "/searchItem/" + id + "/query", query, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("queries", Matchers.hasSize(1)))
                  .andExpect(MockMvcResultMatchers.jsonPath("queries[0]", Matchers.comparesEqualTo(query)));
    }

    @Test
    public void testAddQueryAsUnauthorizedUser() throws Exception {
        String username = testHelper.createSampleUser();
        String username2 = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);
        String query = "some random query";

        testHelper.performPut(mockMvc, "/searchItem/" + id + "/query", query, testHelper.createDefaultUserAuthorization(username2))
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testAddQueryAsAdmin() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);
        String query = "some random query";

        testHelper.performPut(mockMvc, "/searchItem/" + id + "/query", query, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("queries", Matchers.hasSize(1)))
                  .andExpect(MockMvcResultMatchers.jsonPath("queries[0]", Matchers.comparesEqualTo(query)));
    }

    @Test
    public void testDeleteQuery() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);
        String query = "some random query";

        addQueryToSearchItem(id, query);

        testHelper.performDelete(mockMvc, "/searchItem/" + id + "/query", query, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("queries", Matchers.hasSize(0)));
    }

    @Test
    public void testDeleteQueryAsUnauthorizedUser() throws Exception {
        String username = testHelper.createSampleUser();
        String username2 = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);
        String query = "some random query";

        addQueryToSearchItem(id, query);

        testHelper.performDelete(mockMvc, "/searchItem/" + id + "/query", query, testHelper.createDefaultUserAuthorization(username2))
                  .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testDeleteQueryAsAdmin() throws Exception {
        String username = testHelper.createSampleUser();
        String searchItem = "SearchItem";

        String id = createSearchItem(searchItem, username);
        String query = "some random query";

        testHelper.performDelete(mockMvc, "/searchItem/" + id + "/query", query, testHelper.createAdminAuthorization())
                  .andExpect(MockMvcResultMatchers.status().isOk());

        testHelper.performGet(mockMvc, "/searchItem/" + id, testHelper.createDefaultUserAuthorization(username))
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("queries", Matchers.hasSize(0)));
    }

    private String createSearchItem(String name, String username) {
        return searchItemService.addSearchItem(name, username);
    }

    private void addQueryToSearchItem(String id, String query) {
        searchItemService.addQueryToSearchItem(id, query);
    }
}
