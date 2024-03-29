package ru.kduskov.vkapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.model.external.api.User;
import ru.kduskov.vkapi.repository.AuditRecordRepository;
import ru.kduskov.vkapi.utils.TestData;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private RestTemplate restTemplate;
    @Mock
    protected AuditRecordRepository auditRep;

    private UserService userService;

    {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(ExternalApiConstants.URL));
    }

    @BeforeEach
    public void setUp(){
        userService = new UserService(restTemplate, auditRep);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 1})
    public void shouldReturnUserById(int id) throws JsonProcessingException {
        Optional<User> u = userService.get(id);
        Assertions.assertTrue(u.isPresent());

        User tU = TestData.getApiUser(id);
        Assertions.assertEquals(tU, u.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {-3, 11})
    public void shouldReturnEmptyFieldForWrongUserId(int id) throws JsonProcessingException {
        Optional<User> u = userService.get(id);
        Assertions.assertFalse(u.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void shouldReturnAllUsersAlbums(int id) throws JsonProcessingException {
        Optional<List<Album>> albums = userService.getAlbums(id);
        Assertions.assertTrue(albums.isPresent());

        List<Album> expAlbums = TestData.getApiUserAlbums(id);
        Assertions.assertEquals(expAlbums, albums.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {11})
    public void shouldReturnCorrectIdOfNewUser(int id) throws JsonProcessingException {
        int actualId = userService.create(TestData.getApiUser(id));
        Assertions.assertEquals(id, actualId);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void shouldReturnAllUsersPosts(int id) throws JsonProcessingException {
        Optional posts = userService.getPosts(id);
        Assertions.assertTrue(posts.isPresent());

        List<Post> expAlbums = TestData.getApiUserPosts(id);
        Assertions.assertEquals(expAlbums, posts.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void userShouldBeDeleted(int postId) throws JsonProcessingException {
        boolean res = userService.delete(postId);
        Assertions.assertEquals(true, res);

        // To check was user actually deleted, there should be get request
    }
}
