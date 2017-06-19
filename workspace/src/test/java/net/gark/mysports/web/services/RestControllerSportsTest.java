package net.gark.mysports.web.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gark.mysports.ApplicationSports;
import net.gark.mysports.domain.entity.Sport;
import net.gark.mysports.domain.entity.User;
import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositorySport;
import net.gark.mysports.domain.repository.IRepositoryUser;
import net.gark.mysports.services.dto.DtoSport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by mark on 21/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSports.class)
@WebAppConfiguration
class RestControllerSportsTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MockMvc mockMvc;

    private String userName = "TestUser";
    private User user;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private IRepositoryUser repositoryUser;

    @Autowired
    private IRepositorySport repositorySports;

    @Autowired
    private RestControllerSport restControllerSport;

    @Autowired
    void setConverters(final HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // this.repositoryUser.deleteAll();
        // this.repositorySports.deleteAll();

        this.user = this.repositoryUser.findByEmail("test@test.com");
        if (this.user == null) {
            final IUser user = new User("test@test.com", "Test", "Test");
            user.setEnabled(true);
            user.setVerified(true);
            this.user = repositoryUser.save((User) user);
        }

        this.repositorySports.findByOwnerId(user.getId()).forEach(a -> this.repositorySports.delete(a.getId()));

        restControllerSport.setLoggedInUser(this.user);
    }

    public Sport constructsport() throws Exception {
        final Sport a = new Sport();
        a.setOwnerId(user.getId());
        a.setTitle("Test sport");
        a.setDetails("Test sport Details");
        a.setRating(ISport.Rating.Simple);

        return a;
    }

    @Test
    public void createSport() throws Exception {

        final ISport a = constructsport();

        final String jsonsport = json(a);

        this.mockMvc.perform(post("/api/sport/")
                .contentType(contentType)
                .content(jsonsport))
                .andExpect(status().isOk());
    }

    @Test
    public void readSingleSport() throws Exception {

        final ISport a = this.constructsport();

        final String jsonsport = json(a);

        final MvcResult result = this.mockMvc.perform(post("/api/sport/")
                .contentType(contentType)
                .content(jsonsport))
                .andExpect(status().isOk())
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        final DtoSport createdsport = mapper.readValue(content, DtoSport.class);

        mockMvc.perform(get("/api/sport/"
                + createdsport.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(createdsport.getId().intValue())))
                .andExpect(jsonPath("$.details", is("Test sport Details")));
    }

    @Test
    public void readSports() throws Exception {

        final List<ISport> listSports = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            listSports.add(this.repositorySports.save((Sport)new Sport(user.getId())
                    .setTitle("Test sport " + i)
                    .setDetails("Test sport Details")
                    .setOwnerId(user.getId())
                    .setRating(ISport.Rating.values()[0])));
            Thread.sleep(1000); // ensure sufficient time between creations (to ensure order is predictable)
        }

        // We expect to see the sports in created (age) order
        // - we will only examine the first 2 sports (which should be the modest recent created)

        mockMvc.perform(get("/api/sport/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data", hasSize(listSports.size())))
                .andExpect(jsonPath("$.data[0].id", is(listSports.get(listSports.size() - 1).getId().intValue())))
                .andExpect(jsonPath("$.data[0].details", is("Test sport Details")))
                .andExpect(jsonPath("$.data[1].id", is(listSports.get(listSports.size() - 2).getId().intValue())))
                .andExpect(jsonPath("$.data[1].details", is("Test sport Details")));
    }

    protected String json(final Object o) throws IOException {
        final MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

