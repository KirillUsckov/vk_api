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
import ru.kduskov.vkapi.model.external.api.Comment;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.repository.AuditRecordRepository;
import ru.kduskov.vkapi.utils.TestData;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    private RestTemplate restTemplate;
    @Mock
    protected AuditRecordRepository auditRep;

    private PostService postService;
    private UserService userService;


    {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(ExternalApiConstants.URL));
    }

    @BeforeEach
    public void setUp(){
        postService = new PostService(restTemplate, auditRep);
        userService = new UserService(restTemplate, auditRep);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void shouldReturnPostById(int id) throws JsonProcessingException {
        Optional post = postService.get(id);
        Assertions.assertTrue(post.isPresent());

        Post tPost = TestData.getPost(id);
        Assertions.assertEquals(tPost, post.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void shouldReturnAllUsersAlbums(int id) throws JsonProcessingException {
        Optional comments = postService.getComments(id);
        Assertions.assertTrue(comments.isPresent());

        List<Comment> expAlbums = TestData.getPostsComments(id);
        Assertions.assertEquals(expAlbums, comments.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void postsByUserFilterShouldBeAsListOfUsersPosts(int userId) throws JsonProcessingException {
        Optional postsOfUser = userService.getPosts(userId);
        Optional usersPosts = postService.getPostsByUser(userId);
        Assertions.assertTrue(postsOfUser.isPresent());
        Assertions.assertTrue(usersPosts.isPresent());
        Assertions.assertEquals(postsOfUser.get(), usersPosts.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {101})
    public void postShouldBeCreatedWithNewId(int postId) throws JsonProcessingException {
        int res = postService.create(TestData.getPost(postId));
        Assertions.assertEquals(postId, res);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void postShouldBeDeleted(int postId) throws JsonProcessingException {
        boolean res = postService.delete(postId);
        Assertions.assertEquals(true, res);

        // To check was post actually deleted, there should be get request
    }
}
